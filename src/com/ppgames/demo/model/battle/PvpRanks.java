package com.ppgames.demo.model.battle;

import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.RankListPvpConfig;
import com.ppgames.demo.config.config.RobotConfig;
import com.ppgames.demo.config.item.RankListPvpItem;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.mail.Mail;
import com.ppgames.demo.model.mail.MailManager;
import com.ppgames.demo.config.item.PropItem;
import org.apache.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class PvpRanks {
    public static final Logger log = Logger.getLogger(PvpRanks.class);
    public static final int max_rank = 999;

    // 游戏启动时初始化所有名次到该数组, data始终保持1000条记录
    public static ConcurrentHashMap<Integer, Long> data = new ConcurrentHashMap<>(max_rank + 1);

    public static synchronized long getPidByRank(int rank) {
        log.debug("rank:" + rank);
        return data.get(rank);
    }

    public static synchronized void addOneRank(int rank, long pid) {
        if (rank > max_rank) {
            throw new InvalidParameterException("rank exceed," + rank);
        }
        data.put(rank, pid);
        log.info("addOneRank," + rank + "," + pid);
    }

    public static synchronized void rebuild() {
        // 确保JVM内存中只有1份class对象
        clear();
        Set<Long> pids = Cache.players.keySet();
        for (long pid : pids) {
            Player p = Cache.players.get(pid);
            if (p.getPvp() != null && p.getPvp().getRank().get() <= max_rank) {
                addOneRank(p.getPvp().getRank().get(), pid);
            }
        }
    }
    private static ArrayList<PropItem> getRewardByRank(int rank){
        int length = RankListPvpConfig.getInstance().getMap().size();//DataManager.ranking_list_pvp.size();
        for (int i = 0 ; i < length ; i ++){
            //JSONObject data = JSON.parseObject(DataManager.ranking_list_pvp.getString(i));
            RankListPvpItem data = RankListPvpConfig.getInstance().getItem(i);
            int min = data.getMin();
            int max = data.getMax();
            if (rank <= max && rank >= min)
            {
                //JSONArray rewards = JSON.parseArray(data.getString("rewards"));
                return data.getRewards_list();
            }
        }
        return null;
    }
    public static synchronized void sendRankReward(){
        for (int rank = 0 ; rank < 999 ; rank ++){
            long pid = getPidByRank(rank);
            if (pid == RobotConfig.robot_id) continue;
            ArrayList<PropItem> attached = new ArrayList<PropItem>();
            ArrayList<PropItem> rewards = getRewardByRank(rank + 1);
            if (rewards == null) continue;
            for (int i = 0 ; i < rewards.size(); i ++ ){
                int type = rewards.get(i).getType();
                int id = rewards.get(i).getId();
                int n = rewards.get(i).getNum();
                PropItem reward = new PropItem(type,id,n);
                attached.add(reward);
            }
            String mialContent = "恭喜你在搭配大赛排行中获得第"+rank+"名。";
            // FIXME batch insert
            MailManager.getInstance().insertMail(pid, 0, "系统邮件", "搭配大赛排行", mialContent, attached, Mail.MTYPE_SYSTEM);
        }
    }
    private static void clear() {
        data.clear();
        for (int i = 0; i < max_rank + 1; i++) {
            // robot
            data.put(i, RobotConfig.robot_id);
        }
        log.info("PvpRanks clear");
    }
}

