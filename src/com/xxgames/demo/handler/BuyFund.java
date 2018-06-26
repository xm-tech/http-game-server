package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.activity.ActivityGroup;
import com.xxgames.demo.activity.ActivityManager;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.QuestList;
import com.xxgames.demo.model.quest.QuestManager;
import com.xxgames.util.BagType;

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
