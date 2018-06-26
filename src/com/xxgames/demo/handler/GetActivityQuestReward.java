package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.activity.*;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.item.PropItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.*;
import com.xxgames.demo.utils.Const;
import com.xxgames.util.BagType;

import java.util.ArrayList;

/**
 * Created by PhonePadPC on 2017/8/2.
 */
public class GetActivityQuestReward extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player player = Cache.players.get(pid);
        if (player == null) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家");
            return;
        }
        int groupId = req.data.getInteger("groupId");
        int aid = req.data.getInteger("aid");
        int type = req.data.getInteger("type");
        ActivityGroup group = ActivityManager.getInstance().getGroup(type,groupId);
        if (group == null) return ;
        if (type == 3){
            //兑换活动
            exchangeReward(player,group,aid,resp);
        }
        else {
            if (group.getType() == Const.ACT_TYPE_BUY){
                buyReward(player,group,aid,resp);
            }
            else {
                getTaskReward(player,group,aid,resp);
            }
        }
    }
    private void exchangeReward(Player player,ActivityGroup group,int aid,GameResp resp){
        ActivityExchange activityExchange = (ActivityExchange) (group.getActivity(aid));
        int needNum = activityExchange.getNeedNum();
        int exchangeGid = activityExchange.getExchangeGid();
        int exchangeTimes = player.getTimeTagManager().getTagCount(aid,group.getStartTime());
        if (exchangeTimes >= activityExchange.getTotalTimes()){
            resp.send(ErrCode.UNKONW_ERR, "兑换次数不足");
            return;
        }
        int haveNum = (int)(player.getBagNum(0,exchangeGid));
        if (haveNum < needNum){
            resp.send(ErrCode.UNKONW_ERR, "兑换数量不足");
            return;
        }
        boolean bagDecrResult = player.decrBags("exchangeReward",0,exchangeGid,-needNum);
        if (bagDecrResult){
            ArrayList<PropItem> rewards = activityExchange.getRewards();
            boolean bagAddResult = player.addBags("exchangeReward", rewards);
            if (bagAddResult == false){
                resp.send(ErrCode.UNKONW_ERR, "背包已满");
                player.addBags("exchangeRewardGiveBack",0,exchangeGid,needNum);
                return ;
            }
            player.getTimeTagManager().addTag(aid);
            PropItem decrItem = new PropItem(0,exchangeGid,needNum);
            resp.data.put("rewards",rewards);
            resp.data.put("decrItem",decrItem);
            resp.send(ErrCode.SUCC);
        }

    }
    private void buyReward(Player player,ActivityGroup group,int aid,GameResp resp){
        ActivityBuy activityBuy = (ActivityBuy)(group.getActivity(aid));
        int price = activityBuy.getPrice();
        boolean decrOk = false;
        // 扣钱,购买
        decrOk = player.decrBags("ShopBuy", BagType.DIAMOND, 0, -price);
        if (!decrOk) {
            resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
            return;
        }
        ArrayList<PropItem> rewards = activityBuy.getRewards();
        boolean bagAddResult = player.addBags("activityBuyReward", rewards);
        resp.data.put("rewards",rewards);
        resp.send(ErrCode.SUCC);
    }
    private void getTaskReward(Player player,ActivityGroup group,int aid,GameResp resp){
        QuestList list = QuestManager.getInstance().getQuestList(player.getId());
        if (list == null) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家的任务数据");
            return;
        }
        QuestActivity targetQuest = list.getActivityQuest(group.getGroupId(), aid);
        if (targetQuest == null) {
            resp.send(ErrCode.QUEST_OUT_OF_DATE, "任务已过期");
            return;
        }
        ActivityBase activity = group.getActivity(aid);
        if (activity == null) {
            resp.send(ErrCode.QUEST_OUT_OF_DATE, "任务已过期");
            return;
        }
        if (targetQuest.getStatus() == EQuestStatus.SUCCESS.ordinal()) {
            ArrayList<PropItem> rewards = activity.getRewards();
            boolean result = player.addBags("GetActivityQuestReward", rewards);
            if (!result) {
                resp.send(ErrCode.UNKONW_ERR, "背包空间不足");
                return;
            }
            targetQuest.setStatus(EQuestStatus.FINISH.ordinal());
            resp.data.put("rewards",rewards);
            resp.send(ErrCode.SUCC);
        } else {
            resp.send(ErrCode.UNKONW_ERR, "任务未完成");
        }

    }
}
