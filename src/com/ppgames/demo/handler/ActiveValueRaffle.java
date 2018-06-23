package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.ActiveRewardsConfig;
import com.ppgames.demo.config.item.ActiveRewardsItem;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.config.item.PropItem;
import com.ppgames.demo.model.quest.QuestList;
import com.ppgames.demo.model.quest.QuestManager;
import com.ppgames.util.RandBucketItem;
import com.ppgames.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 2017/6/15.
 */
public class ActiveValueRaffle extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player player = Cache.players.get(pid);
        if (player == null) {
            resp.send(ErrCode.UNKONW_ERR, "玩家信息出错,没有此玩家");
            return;
        }
        QuestList questList = QuestManager.getInstance().getQuestList(pid);
        if (questList == null) {
            resp.send(ErrCode.UNKONW_ERR, "没有找到数据,错误的player id");
            return;
        }
        int activeValue = questList.getActiveValueQuest().getWeekActiveValue();


        JSONArray get_rewards = new JSONArray();
        if (activeValue >= 300) {
            activeValue -= 300;
            questList.getActiveValueQuest().setWeekActiveValue(activeValue);
            int rand_count = RandomUtil.getRandom(3);
            for (int i = 0 ; i <= rand_count ;i ++) {
                ArrayList<PropItem> reward = getRandomReward();
                for(int j = 0; j < reward.size(); ++j){
                    get_rewards.add(reward.get(j).toJsonObject());
                }
                player.addBags("ActiveValueRaffle",reward);
            }
            resp.data.put("active_value", activeValue);
            resp.data.put("get_rewards",get_rewards);
            resp.send(ErrCode.SUCC);
        } else {
            resp.send(ErrCode.ACTIVE_VALUE_NOT_ENOUGH, "活跃值不够");
            return;
        }
    }

    public ArrayList<PropItem> getRandomReward(){
        int length = ActiveRewardsConfig.getInstance().getMap().size();
        List<RandBucketItem<ArrayList<PropItem>>> rand_bucket_list = new ArrayList<>();
        for (int i = 0 ; i < length ; i ++){
            ActiveRewardsItem data = ActiveRewardsConfig.getInstance().getItem(i);
            ArrayList<PropItem> rewards = data.getRewardsList();
            RandomUtil.addRandBucket(rand_bucket_list,rewards,data.getRate());
        }
        return RandomUtil.getRandBucket(rand_bucket_list);
    }
}
