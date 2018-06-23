package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.SaleQuestConfig;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.EQuestStatus;
import com.ppgames.demo.model.quest.QuestList;
import com.ppgames.demo.model.quest.QuestManager;
import com.ppgames.demo.model.quest.QuestSale;

/**
 * Created by JMX on 2017/7/26.
 */
public class TakeSaleQuestRewards extends GameAct{
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");

        QuestList list = QuestManager.getInstance().getQuestList(pid);
        if(list == null) {
            resp.send(ErrCode.UNKONW_ERR, "任务列表不存在");
            return;
        }

        QuestSale targetQuest = list.getSaleQuest();
        if(targetQuest == null){
            resp.send(ErrCode.QUEST_OUT_OF_DATE, "任务已过期");
            return;
        }

        if(targetQuest.getStatus() == EQuestStatus.SUCCESS.ordinal())
        {
            Player p = Cache.players.get(pid);

            JSONArray rewards = JSON.parseArray(SaleQuestConfig.getInstance().getItem((int)targetQuest.getId()).getRewards());
            boolean result = p.addBags("TakeSaleQuestRewards" , rewards);

            if(!result){
                resp.send(ErrCode.UNKONW_ERR, "背包空间不足");
                return;
            }

            list.saleQuestFinish();
            resp.send(ErrCode.SUCC);
            return;
        }
        else{
            resp.send(ErrCode.UNKONW_ERR, "任务未完成");
        }
    }
}
