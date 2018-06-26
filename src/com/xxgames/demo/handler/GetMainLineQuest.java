package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.model.quest.QuestList;
import com.xxgames.demo.model.quest.QuestManager;

/**
 * Created by Tony on 2017/6/13.
 */
public class GetMainLineQuest extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        QuestList list = QuestManager.getInstance().getQuestList(pid);
        if (list == null) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家的任务数据");
            return;
        }

        resp.data.put("quest", list.getMainLineQuests().getDataForClient());
        resp.send(ErrCode.SUCC);
    }
}
