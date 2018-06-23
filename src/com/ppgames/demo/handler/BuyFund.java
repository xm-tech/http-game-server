package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.activity.ActivityGroup;
import com.ppgames.demo.activity.ActivityManager;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.QuestList;
import com.ppgames.demo.model.quest.QuestManager;
import com.ppgames.util.BagType;

/**
 * Created by PhonePadPC on 2017/8/1.
 */
public class BuyFund extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player player = Cache.players.get(pid);
        if (player == null) {
            resp.send(ErrCode.UNKONW_ERR, "玩家不存在");
            return;
        }
        QuestList list = QuestManager.getInstance().getQuestList(pid);
        if (list == null) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家的任务数据");
            return;
        }
        ActivityGroup group = ActivityManager.getInstance().getFundActivityGroups();
        if (group == null) {
            resp.send(ErrCode.UNKONW_ERR, "蜜蜜基金活动已经结束");
            return ;
        }

        if (player != null) {//月卡用户才能买
            int needDiamond = 1000;
            player.decrBags("BuyFund", BagType.DIAMOND, 0, -needDiamond);
            list.addFundQuestList();
            resp.send(ErrCode.SUCC);
        }
    }
}
