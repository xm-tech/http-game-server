package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.model.quest.QuestList;
import com.ppgames.demo.model.quest.QuestManager;


/**
 * Created by JMX on 2017/7/25.
 */
public class GetSaleQuest extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp){
        long pid = req.data.getLongValue("pid");
        QuestList list = QuestManager.getInstance().getQuestList(pid);
        if (list == null) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家的任务数据");
            return;
        }

        resp.data.put("quest", list.GetSaleQuestForClient());
        resp.send(ErrCode.SUCC);
    }
}
