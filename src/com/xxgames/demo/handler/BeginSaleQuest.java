package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.model.quest.EQuestStatus;
import com.xxgames.demo.model.quest.QuestList;
import com.xxgames.demo.model.quest.QuestManager;

/**
 * Created by JMX on 2017/7/26.
 */
public class BeginSaleQuest extends GameAct{
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");

        QuestList list = QuestManager.getInstance().getQuestList(pid);
        if (list == null) {
            resp.send(ErrCode.UNKONW_ERR, "任务不存在");
            return;
        }

        if(list.getSaleQuest().getStatus() == EQuestStatus.REJECTED.ordinal()){
            resp.data.put("quest", list.beginSaleQuest().getDataForClient());
            resp.send(ErrCode.SUCC);
        }
        else{
            resp.send(ErrCode.UNKONW_ERR, "任务已过期");
        }
    }
}
