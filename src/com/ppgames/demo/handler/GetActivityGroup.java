package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.ppgames.util.TimeUtil;

import java.util.Map;

/**
 * Created by PhonePadPC on 2017/8/2.
 */
public class GetActivityGroup extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player player = Cache.players.get(pid);
        int type = req.data.getIntValue("type");
        if (type == 1){
            getNewPlayerGroup(player,resp);
        }
        else if (type == 2){
            getNormalGroup(player,resp);
        }
        else if (type == 3){
            getExchageGroup(player,resp);
        }
    }
    private void getExchageGroup(Player player,GameResp resp){
        JSONArray groupList = new JSONArray();
        Map<Integer, ActivityGroup> activityGroupMap = ActivityManager.getInstance().getExchangeGroupMap();
        for (Map.Entry<Integer, ActivityGroup> e: activityGroupMap.entrySet()) {
            ActivityGroup activityGroup = e.getValue();
            if (activityGroup.isValid()) {
                groupList.add(activityGroup.getDataForClient());
            }
        }
        resp.data.put("groupList",groupList);
        resp.data.put("type",3);
        resp.send(ErrCode.SUCC);
    }
    private void getNormalGroup(Player player,GameResp resp){
        JSONArray groupList = new JSONArray();
        Map<Integer, ActivityGroup> activityGroupMap = ActivityManager.getInstance().getNormalGroupMap();
        for (Map.Entry<Integer, ActivityGroup> e: activityGroupMap.entrySet()) {
            ActivityGroup activityGroup = e.getValue();
            if (activityGroup.isValid()) {
                groupList.add(activityGroup.getDataForClient());
            }
        }
        groupList.add(makeExchangeGroup());
        resp.data.put("groupList",groupList);
        resp.data.put("type",2);
        resp.send(ErrCode.SUCC);
    }
    private JSONObject makeExchangeGroup(){
        JSONObject object = new JSONObject();
        object.put("groupId",8);
        object.put("type",8);
        object.put("startTime",0);
        object.put("endTime",1635731200);
        object.put("name","兑换活动");
        object.put("intro","兑换活动");
        return object;
    }
    private void getNewPlayerGroup(Player player,GameResp resp ){
        QuestList list = QuestManager.getInstance().getQuestList(player.getId());
        if (list == null) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家的任务数据");
            return;
        }
        JSONArray groupList = new JSONArray();
        Map<Integer, ActivityGroup> activityGroupMap = ActivityManager.getInstance().getNewPlayerGroupMap();
        for (Map.Entry<Integer, ActivityGroup> e: activityGroupMap.entrySet()) {
            ActivityGroup activityGroup = e.getValue();
            if (activityGroup.isValid()){
                JSONObject obj = activityGroup.getDataForClient();
                obj.put("status",list.getActivityGroupStatus(activityGroup.getGroupId()));
                int startTime = TimeUtil.startOfDay(player.getRegTime());
                obj.put("startTime",startTime);
                obj.put("endTime",startTime + activityGroup.getLastTime());
                groupList.add(obj);
            }
        }
        resp.data.put("groupList",groupList);
        resp.data.put("type",1);
        resp.send(ErrCode.SUCC);
    }
}
