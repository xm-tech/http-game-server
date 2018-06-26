package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.activity.*;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.EQuestStatus;
import com.xxgames.demo.model.quest.QuestActivity;
import com.xxgames.demo.model.quest.QuestList;
import com.xxgames.demo.model.quest.QuestManager;
import com.xxgames.demo.utils.Const;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by PhonePadPC on 2017/8/1.
 */
public class GetActivityQuest  extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player player = Cache.players.get(pid);
        int groupId = req.data.getInteger("groupId");
        int type = req.data.getIntValue("type");
        if (type == 1){
            getNewPlayerActivityTask(player,groupId,resp);
        }
        else if (type == 2){
            getNormalActivityTask(player,groupId,resp);
        }
        else if (type == 3){
            getExchangeActivtiyTask(player,groupId,resp);
        }
    }
    private void getNormalActivityTask(Player player,int groupId,GameResp resp){
        QuestList list = QuestManager.getInstance().getQuestList(player.getId());
        if (list == null) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家的任务数据");
            return;
        }
        ActivityGroup activityGroup = ActivityManager.getInstance().getNormalGroup(groupId);
        if (activityGroup == null){
            resp.send(ErrCode.UNKONW_ERR, "该活动已结束");
            return;
        }
        if (!activityGroup.isValid()){
            resp.send(ErrCode.UNKONW_ERR, "该活动已结束");
            return;
        }
        if (activityGroup.getType() == Const.ACT_TYPE_BUY){
            //买的
            resp.data.put("questList",getBuyTasks(activityGroup));
        }
        else {
            List<QuestActivity> questActivityList = list.getActivityQuestsByGroup(groupId);
            resp.data.put("questList",getActivityTask(questActivityList));
        }

        resp.data.put("groupId",groupId);
        resp.send(ErrCode.SUCC);
    }
    private void getExchangeActivtiyTask(Player player,int groupId,GameResp resp){
        ActivityGroup activityGroup = ActivityManager.getInstance().getExchangeGroup(groupId);
        if (activityGroup == null){
            resp.send(ErrCode.UNKONW_ERR, "该活动已结束");
            return;
        }
        if (!activityGroup.isValid()){
            resp.send(ErrCode.UNKONW_ERR, "该活动已结束");
            return;
        }
        resp.data.put("questList",getExchangeTasks(player,activityGroup));
        resp.data.put("groupId",groupId);
        resp.send(ErrCode.SUCC);
    }


    private void getNewPlayerActivityTask(Player player,int groupId,GameResp resp){
        QuestList list = QuestManager.getInstance().getQuestList(player.getId());
        if (list == null) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家的任务数据");
            return;
        }
        ActivityGroup activityGroup = ActivityManager.getInstance().getNewPlayerGroup(groupId);
        if (activityGroup == null){
            resp.send(ErrCode.UNKONW_ERR, "该活动已结束");
            return;
        }
        if (!activityGroup.isValid()){
            resp.send(ErrCode.UNKONW_ERR, "该活动已结束");
            return;
        }
        List<QuestActivity> questActivityList = list.getActivityQuestsByGroup(groupId);
        if (questActivityList.size() == 0 && activityGroup.getType() == Const.ACT_TYPE_FUND_LEVEL_UP){
            //基金类,并且还没买
            resp.data.put("questList",packFundList());
        }
        else {
            resp.data.put("questList",getActivityTask(questActivityList));
        }
        resp.data.put("groupId",groupId);

        resp.send(ErrCode.SUCC);
    }
    private JSONArray getBuyTasks(ActivityGroup activityGroup){
        JSONArray list = new JSONArray();
        Set<Map.Entry<Integer, ActivityBase>> entrySet = activityGroup.getActivityMap().entrySet();
        for (Map.Entry<Integer, ActivityBase> e : entrySet){
            ActivityBuy activity = (ActivityBuy) (e.getValue()) ;
            JSONObject obj = new JSONObject();
            obj.put("aid",activity.getAid());
            obj.put("name",activity.getName());
            obj.put("intro",activity.getIntro());
            obj.put("rewards",activity.getRewards());
            obj.put("price",activity.getPrice());
            list.add(obj);
        }
        return list;
    }
    private JSONArray getExchangeTasks(Player player,ActivityGroup activityGroup){
        JSONArray list = new JSONArray();
        Set<Map.Entry<Integer, ActivityBase>> entrySet = activityGroup.getActivityMap().entrySet();
        for (Map.Entry<Integer, ActivityBase> e : entrySet) {
            ActivityExchange activity = (ActivityExchange)(e.getValue()) ;
            JSONObject obj = new JSONObject();
            obj.put("aid",activity.getAid());
            obj.put("name",activity.getName());
            obj.put("intro",activity.getIntro());
            obj.put("rewards",activity.getRewards());
            obj.put("exchangeGid",activity.getExchangeGid());
            obj.put("needNum",activity.getNeedNum());
            obj.put("exchangeTimes",player.getTimeTagManager().getTagCount(activity.getAid(),activityGroup.getStartTime(),activityGroup.getEndTime()));
            obj.put("totalTimes",activity.getTotalTimes());
            list.add(obj);
        }
        return list;
    }
    public JSONArray getActivityTask(List<QuestActivity> activityList){
        JSONArray list = new JSONArray();
        for (QuestActivity activity:activityList) {
            ActivityBase activityBase = activity.getActivity();
            if (activityBase == null) continue;
            if (activityBase.getGroupId() != Const.ACT_TYPE_FUND_LEVEL_UP
                && activity.isOutOfDate()) continue; //超过日期的不显示
            if (activityBase.getType() == 1){
                if (activity.getStatus() == EQuestStatus.FINISH.ordinal()) continue;//已完成的不显示
                int preAid = activityBase.getPreAid();
                if (preAid != 0 && !checkPreActivityComplete( activityList ,preAid)){
                    continue;//前置任务未完成的不显示
                }
            }
            list.add(activity.getDataForClient());
        }
        return list;
    }
    private JSONArray packFundList(){
        JSONArray list = new JSONArray();
        ActivityGroup fundGroup = ActivityManager.getInstance().getFundActivityGroups();
        Set<Map.Entry<Integer, ActivityBase>> entrySet = fundGroup.getActivityMap().entrySet();
        for (Map.Entry<Integer, ActivityBase> e : entrySet){
            ActivityBase activity = e.getValue();
            JSONObject object = new JSONObject();
            object.put("aid",activity.getAid());
            object.put("name",activity.getName());
            object.put("intro",activity.getIntro());
            object.put("rewards",activity.getRewards());
            object.put("status",-1);
            list.add(object);
        }
        return list;
    }
    private Boolean checkPreActivityComplete(List<QuestActivity> activityList, int preAid){
        for (QuestActivity activity:activityList) {
            if (activity.getActivity() == null) continue;
            if (activity.getActivity().getAid() == preAid && activity.getStatus() == EQuestStatus.FINISH.ordinal())
                return true;
        }
        return false;
    }
}
