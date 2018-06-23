package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.model.quest.QuestCustomer;
import com.ppgames.demo.model.quest.QuestList;
import com.ppgames.demo.model.quest.QuestManager;

import java.util.List;

/**
 * Created by Tony on 2017/6/21.
 */
public class GetCustomerQuests extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        QuestList list = QuestManager.getInstance().getQuestList(pid);
        if (list == null) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家的任务数据");
            return;
        }

        List<QuestCustomer> quests = list.getCustomerQuests();
        if (quests.size() <= 0) {
            resp.send(ErrCode.UNKONW_ERR, "目前没有顾客任务");
            return;
        }

        resp.data.put("list", list.getCustomerQuestsForClient());
        resp.send(ErrCode.SUCC);
    }
}
