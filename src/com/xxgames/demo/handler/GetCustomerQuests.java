package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.model.quest.QuestCustomer;
import com.xxgames.demo.model.quest.QuestList;
import com.xxgames.demo.model.quest.QuestManager;

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
