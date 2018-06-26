package com.xxgames.demo.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PhonePadPC on 2017/7/27.
 */
public class ActivityGroup {
    public ActivityGroup(){
        groupId = 0 ;
        startTime = 0 ;
        endTime = 0 ;
        type = 0 ;
        name = "";
        intro = "" ;
    }
    public ActivityGroup(Map m){
        groupId = Integer.parseInt(m.get("groupId").toString());
        startTime = Integer.parseInt(m.get("startTime").toString());
        endTime = Integer.parseInt(m.get("endTime").toString());
        type = Integer.parseInt(m.get("type").toString());
        lastTime = Integer.parseInt(m.get("lastTime").toString());
        name = m.get("name").toString();
        intro = m.get("intro").toString();
        extra = JSON.parseObject(m.get("extra").toString());
    }
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
    public int getType() {
        return type;
    }

    public JSONObject getExtra() {
        return extra;
    }

    public void setExtra(JSONObject extra) {
        this.extra = extra;
    }

    public int getLastTime() {
        if (lastTime == 0){
            lastTime = endTime - startTime;
        }
        return lastTime;
    }
    private int groupId;
    private int startTime ;
    private int endTime;
    private String name ;
    private String intro ;
    private int lastTime;
    private int type ;
    private JSONObject extra;

    public Map<Integer, ActivityBase> getActivityMap() {
        return activityMap;
    }

    private Map<Integer,ActivityBase> activityMap = new HashMap<>();
    public Boolean isValid(){
        int now = TimeUtil.nowInt();
        if (getEndTime() == 0 && getStartTime() == 0) return true ;
        else return now <= getEndTime() && now >= getStartTime();
    }
    public ActivityBase getActivity(int aid ){
        return activityMap.get(aid);
    }
    public void addActivity(ActivityBase activity){
        if (activity == null) return ;
        activityMap.put(activity.getAid(),activity);
    }
    public JSONObject getDataForClient(){
        JSONObject object = new JSONObject();
        object.put("groupId",groupId);
        object.put("type",type);
        object.put("startTime",startTime);
        object.put("endTime",endTime);
        object.put("name",name);
        object.put("intro",intro);
        return object;
    }
}
