package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.model.quest.QuestList;
import com.xxgames.demo.model.quest.QuestManager;


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
