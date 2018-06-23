package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.model.quest.EQuestStatus;
import com.ppgames.demo.model.quest.QuestList;
import com.ppgames.demo.model.quest.QuestManager;

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
