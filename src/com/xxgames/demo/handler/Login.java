package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.core.db.GameLogicDataSource;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.activity.ActivityManager;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.dao.AllDao;
import com.xxgames.demo.model.Passport;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.QuestList;
import com.xxgames.demo.model.quest.QuestManager;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;
import com.xxgames.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Login extends GameAct {

    private static Logger log = LoggerFactory.getLogger(Login.class);

    @Override
    public void exec(GameReq req, GameResp resp) {
        int serverId = req.data.getInteger("serverId");
        String passPortStr = req.data.getString("passPort");
        int isBinding = req.data.getInteger("isBinding");
        long pid = 0;
        String key = serverId + "_" + passPortStr;
        Passport passport = Cache.passports.get(key);
        if (passport != null) {
            pid = passport.getPid();
        } else {
            passport = new Passport(serverId, passPortStr);
            passport.setBinding(isBinding);
            passport.insterToDB();
            Cache.passports.put(key, passport);
        }

        Player p = Cache.players.get(pid);
        if (p == null) {
            p = getPlayer(resp, pid);
            if (p == null) {
                return;
            }
            Cache.players.put(p.getId(), p);
            passport.setPid(p.getId());
        }
        QuestList q = QuestManager.getInstance().getQuestList(p.getId());
        if (q == null) {
            q = getQuestList(resp, p.getId());
            if (q == null) {
                return;
            }
            QuestManager.getInstance().AddQuestList(p.getId(), q);
        }
        p.isFirstHeartBeat = true;
        int now = TimeUtil.nowInt();
        p.setLoginTime(now);
        p.setMsgReadTime(now);
        resp.data.put("player", p);
        long token = System.currentTimeMillis();
        DataManager.tokens.put(p.getId(), token);
        resp.data.put("token", token);
        resp.data.put("isBinding", passport.getBinding());
        resp.data.put("buff", ActivityManager.getInstance().getBuffJsonData());
        resp.send(ErrCode.SUCC);

        checkLoginActivity(p);
    }
    public QuestList getQuestList(GameResp resp, long pid) {
        QuestList q;
        try {
            q = AllDao.qd.getQuestList(pid);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            resp.send(ErrCode.DB_EXEC_ERR);
            return null;
        }
        if (q == null) {
            q = new QuestList(pid);
            try {
                AllDao.qd.saveQuests(q);
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                resp.send(ErrCode.DB_EXEC_ERR);
                return null;
            }
        }
        return q;
    }


    public Player getPlayer(GameResp resp, long pid) {
        // FIXME 最坏情况 2 次db操作
        Player p;
        try {
            // FIXME 始终要加载1次db，为了提高性能可在服务器重启过程先对热点玩家数据做预加载/或者做登陆排队(短连接可以保存session?)
            // 可能造成db线程争用
            p = AllDao.pd.getPlayerById(pid);
        } catch (SQLException e) {
            // mysql problem, need to check mysql server
            log.error(e.getMessage(), e);
            resp.send(ErrCode.DB_EXEC_ERR);
            return null;
        }
        if (p == null) {
            // first login , create.FIXME 也可做到延迟写入
            //p = new Player(pid);
            p = new Player();
            try {
                long create_pid = AllDao.pd.createPlayer(p);
                p.setId(create_pid);
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                resp.send(ErrCode.DB_EXEC_ERR);
                return null;
            }
        }
        return p;
    }

    private boolean tryUpdatePid(long pid, String passPort, int serverId) {
        try {
            GameLogicDataSource.instance
                .update("update t_passport set pid = ? where passPort = ? and serverId = ?", pid, passPort, serverId);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            log.error("tryUpdatePid error passPort = " + passPort + " serverid = " + serverId);

            return false;
        }
        return true;
    }
    private void checkLoginActivity(Player player){
        int loginDay = TimeUtil.getDiffOfDay(player.getRegTime(), player.getLoginTime()) + 1;
        player.questEventListener.dispatchEvent(QuestEventId.LoginDay, loginDay);
    }
}