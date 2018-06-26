package com.xxgames.demo.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.PropItem;
import com.xxgames.demo.model.Player;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by PhonePadPC on 2017/7/21.
 */
public class ActivityBase {
    public ActivityBase(){
        aid = 0 ;
        preAid = 0;
        groupId = 0 ;
        type = 0 ;
        name = "";
        intro = "";
        extra = "" ;
        rewards = new ArrayList<PropItem>();
        lastTime = 0 ;
    }
    public ActivityBase(Map m){
        aid = Integer.parseInt(m.get("aid").toString());
        preAid = Integer.parseInt(m.get("preAid").toString());
        groupId = Integer.parseInt(m.get("groupId").toString());
        name = m.get("name").toString();
        intro = m.get("intro").toString();
        type = Integer.parseInt(m.get("type").toString());
        tasks = JSON.parseArray(m.get("tasks").toString());
        rewards = JSON.parseObject(m.get("rewards").toString(), new TypeReference<ArrayList<PropItem>>(){});
    }
    private int aid;
    private int groupId;
    private int preAid;
    private int type;
    private int lastTime ;
    private String name ;
    private String intro;
    private ArrayList<PropItem> rewards ;
    private JSONArray tasks = new JSONArray();


    public JSONArray getTasks() {
        return tasks;
    }

    public void setTasks(JSONArray tasks) {
        this.tasks = tasks;
    }

    public int getLastTime() {
        return lastTime;
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }
    public int getPreAid() {
        return preAid;
    }

    public void setPreAid(int preAid) {
        this.preAid = preAid;
    }
    private String extra;
    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public ArrayList<PropItem> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<PropItem> rewards) {
        this.rewards = rewards;
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
    public void giveReward(Player player){

    }

}
