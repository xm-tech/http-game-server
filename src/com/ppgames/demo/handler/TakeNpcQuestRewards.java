package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.NpcQuestConfig;
import com.ppgames.demo.config.item.QuestItems.NpcQuestItem;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.EQuestStatus;
import com.ppgames.demo.model.quest.QuestList;
import com.ppgames.demo.model.quest.QuestManager;
import com.ppgames.demo.model.quest.QuestNpc;

import java.util.List;

/**
 * Created by Tony on 2017/6/24.
 */
public class TakeNpcQuestRewards extends GameAct {
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

        QuestNpc targetQuest = list.getNpcQuest();

        if (targetQuest == null) {
            resp.send(ErrCode.QUEST_OUT_OF_DATE, "任务已过期");
            return;
        }

        List<Integer> equips = JSON.parseArray(req.data.getString("equips"), Integer.class);

        JSONObject submitResult = targetQuest.submit(equips);

        if (submitResult.getBoolean("result") && targetQuest.getStatus() == EQuestStatus.SUCCESS.ordinal()) {
            Player p = Cache.players.get(pid);
            NpcQuestItem item = NpcQuestConfig.getInstance().getItem((int) targetQuest.getId());
            JSONArray rewards = JSON.parseArray(item.getRewards());
            boolean result = p.addBags("TakeNpcQuestRewards," + targetQuest.getId(), rewards);
            if (!result) {
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
