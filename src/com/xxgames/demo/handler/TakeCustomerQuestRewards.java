package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.CustomerQuestConfig;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.EQuestStatus;
import com.xxgames.demo.model.quest.QuestCustomer;
import com.xxgames.demo.model.quest.QuestList;
import com.xxgames.demo.model.quest.QuestManager;

import java.util.List;

/**
 * Created by Tony on 2017/6/21.
 */
public class TakeCustomerQuestRewards extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int questId = req.data.getInteger("qid");

        resp.data.put("qid", questId);
        QuestList list = QuestManager.getInstance().getQuestList(pid);
        if (list == null) {
            resp.send(ErrCode.UNKONW_ERR, "任务列表不存在");
            return;
        }

        List<QuestCustomer> quests = list.getCustomerQuests();
        QuestCustomer targetQuest = null;
        for (QuestCustomer quest : quests) {
            if (quest.getId() == questId) {
                targetQuest = quest;
                break;
            }
        }

        if (targetQuest == null) {
            resp.send(ErrCode.QUEST_OUT_OF_DATE, "任务已过期");
            return;
        }

        int eid = req.data.getIntValue("eid");
        int num = req.data.getIntValue("num");
        if (targetQuest.payEquip(eid, num) && targetQuest.getStatus() == EQuestStatus.SUCCESS.ordinal()) {
            Player p = Cache.players.get(pid);
            JSONArray rewards = JSON.parseArray(CustomerQuestConfig.getInstance().getItem(p.getLevel()).getRewards());
            // FIXME 若奖励是多个可能造成部分奖励领取不成功的情况
            boolean result = p.addBags("TakeCustomerQuestRewards", rewards);
            if (result) {
                targetQuest.takeRewards();
            } else {
                resp.send(ErrCode.UNKONW_ERR, "背包空间不足");
                return;
            }
            resp.send(ErrCode.SUCC);
            return;
        }

        resp.send(ErrCode.QUEST_ITEM_INCOMPATIBLE, "物品不符合要求");
        return;
    }
}
