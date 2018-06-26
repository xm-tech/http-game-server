package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.battle.PlayerPvp;
import com.xxgames.util.TimeUtil;

public class PvpRefreshEnemys extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        // tid(获取类型：0直接获取，1-刷新）
        int tid = req.data.getIntValue("tid");
        Player p = Cache.players.get(pid);
        PlayerPvp pvp = p.getPvp();

        int diamond = 0;
        if (tid == 0) {
            // 直接获取
            if (pvp.noEnemys()) {
                pvp.refreshEnemyRanks();
            }
        } else {
            // 刷新
            int now = TimeUtil.nowInt();
            if (!TimeUtil.sameDay(pvp.getRefreshedtime(), now) && pvp.getRefreshedtime() != 0) {
                pvp.setRefreshednum(0);
            }
            diamond = pvp.getRefreshDiamond();
            boolean decrOk = p.decrDiamond("PvpRefreshEnemys", diamond);
            if (!decrOk) {
                resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
                return;
            }
            // 换组对手
            pvp.refreshEnemyRanks();
            // 记录刷新次数,刷新时间戳
            pvp.addRefreshedNum(1, now);
        }

        resp.data.put("useddiamond", diamond);
        resp.data.put("enemys", pvp.genEnemys());
        resp.data.put("rank", pvp.getRank());
        resp.data.put("crystal", pvp.getCrystal());
        resp.data.put("styleid", pvp.getStyleid());
        resp.data.put("buybattlenum", pvp.getBuybattlenum());
        resp.data.put("battlednum", pvp.getBattlednum());
        resp.data.put("refreshednum", pvp.getRefreshednum());
        resp.data.put("tid", tid);

        resp.send(ErrCode.SUCC);
    }
}
