package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.ActiveValueQuestConfig;
import com.ppgames.demo.config.item.QuestItems.ActiveValueQuestItem;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.QuestActiveValue;
import com.ppgames.demo.model.quest.QuestManager;

/**
 * Created by Tony on 2017/6/14.
 */
public class TakeActiveValueReward extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        //要领取哪级的奖励
        int level = req.data.getIntValue("level");

        QuestActiveValue questActiveValue = QuestManager.getInstance().getQuestList(pid).getActiveValueQuest();
        if (questActiveValue.isRewardTaked(level)) {
            resp.send(ErrCode.UNKONW_ERR, "已经领取过奖励");
            return;
        }

        ActiveValueQuestItem item = ActiveValueQuestConfig.getInstance().getItem(0);
        JSONArray rewards = JSON.parseArray(item.getRewards());
        JSONObject reward = rewards.getJSONObject(level);
        if (questActiveValue.getActivity() < item.getConditions().get(level)) {
            resp.send(ErrCode.UNKONW_ERR, "活跃值不够领取奖励");
            return;
        }

        Player p = Cache.players.get(pid);
        if (p == null) {
            //Fix 找不到玩家!
            resp.send(ErrCode.UNKONW_ERR, "找不到玩家!");
            return;
        }

        boolean addOk = p.addBags("TakeActiveValueReward," + level, reward);
        if (addOk) {
            if (p.getLevel() - level > 0) {
                resp.data.put("newLevel", p.getLevel());
                resp.data.put("exp", p.getExp());
            }
            questActiveValue.takeReward(level);
            resp.data.put("reward", reward);
            resp.send(ErrCode.SUCC);
        } else {
            resp.data.put("reward", reward);
            resp.send(ErrCode.UNKONW_ERR, "背包容量不够!");
        }
    }
}
