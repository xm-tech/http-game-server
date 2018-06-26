package com.xxgames.demo.rank;
import com.alibaba.fastjson.JSONArray;
import com.xxgames.demo.model.battle.PvpRanks;
import com.xxgames.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by PhonePadPC on 2017/7/10.
 */
public class RankManager implements Runnable {
    private RankManager(){
    }
    private static final RankManager instance = new RankManager();
    public static RankManager getInstance(){
        return instance;
    }
    public static volatile boolean run = true;
    private static final Logger log = LoggerFactory.getLogger(RankManager.class);
    @Override
    public void run() {
        while (true) {
            if (!run) {
                // exit
                break;
            }
            try {
                Thread.sleep(1 * 1000L);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                continue;
            }
            pulse();
        }
    }
    public void pulse(){
        int time = TimeUtil.nowInt();
        if (time % 60 == 0)
        {
            PlayerCharmRank.getInstance().updateRank();
            PlayerSellRank.getInstance().updateRank();
        }
        if (time % 86400 == 0)
        {
            trySendReward();
        }
    }

    public void trySendReward(){
//        int reward_time = DataManager.getRankRewardTime();
//        int now_time = TimeUtil.nowInt();
//        if (now_time >= reward_time){
//
//        }
        doSendRankReward();
    }
    private void doSendRankReward(){
        PlayerCharmRank.getInstance().sendRankReward();
        PlayerSellRank.getInstance().sendRankReward();
        PvpRanks.sendRankReward();

        updateRankRewardTime();
    }
    private void updateRankRewardTime(){

    }

    public JSONArray getCharmRanks(){
        return PlayerCharmRank.getInstance().getRanks();
    }

    public JSONArray getTodaySellRanks(){
        return PlayerSellRank.getInstance().getRanks();
    }
}
