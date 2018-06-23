package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.MainLineQuestConfig;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.EQuestStatus;
import com.ppgames.demo.model.quest.Quest;
import com.ppgames.demo.model.quest.QuestList;
import com.ppgames.demo.model.quest.QuestManager;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by Tony on 2017/6/19.
 */
public class TakeMainLineReward extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);
        if (p == null) {
            //Fix 找不到玩家!
            resp.send(ErrCode.UNKONW_ERR, "找不到玩家!");
            return;
        }

        QuestList list = QuestManager.getInstance().getQuestList(pid);
        Quest quest = list.getMainLineQuests();

        if (quest.getStatus() != EQuestStatus.SUCCESS.ordinal()) {
            resp.send(ErrCode.UNKONW_ERR, "任务尚未完成");
            return;
        }

        JSONArray rewards = JSONObject.parseArray(MainLineQuestConfig.getInstance().getItem((int)quest.getId()).getRewards());
        short oldLevel = p.getLevel();
        boolean result = p.addBags("TakeMainLineReward", rewards);

        if (result) {
            //进入到下一个主线任务
            list.onTakeMainLineReward();
            short newLevel = p.getLevel();
            if (newLevel - oldLevel > NumberUtils.SHORT_ZERO) {
                resp.data.put("newLevel", newLevel);
                resp.data.put("exp", p.getExp());
            }
            resp.data.put("quest", list.getMainLineQuests().getDataForClient());
            resp.send(ErrCode.SUCC);
        } else {
            resp.send(ErrCode.UNKONW_ERR, "背包容量不够!");
        }
    }
}
