package com.ppgames.demo.activity;


import com.alibaba.fastjson.JSONObject;
import com.ppgames.demo.dao.AllDao;
import com.ppgames.demo.utils.Const;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PhonePadPC on 2017/7/21.
 */
public class ActivityManager {

    private ActivityManager(){
    }
    public static ActivityManager getInstance() {
        return ActivityManager.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static ActivityManager instance = new ActivityManager();
    }
    public static final Logger log = Logger.getLogger(ActivityManager.class);

    public Map<Integer, ActivityGroup> getNewPlayerGroupMap() {
        return newPlayerGroupMap;
    }

    private Map<Integer ,ActivityGroup> newPlayerGroupMap = new HashMap<>();
    private Map<Integer ,ActivityGroup> normalGroupMap = new HashMap<>();

    public Map<Integer, ActivityGroup> getNormalGroupMap() {
        return normalGroupMap;
    }
    public Map<Integer, ActivityGroup> getExchangeGroupMap() {
        return exchangeGroupMap;
    }


    private Map<Integer ,ActivityGroup> exchangeGroupMap = new HashMap<>();

    private ActivityGroup shopDecrSecond = null;
    private ActivityGroup shopAddPrice = null;

    public void load(){
        rebuildAllGroups();
    }
    private void clear(){
        newPlayerGroupMap.clear();
        clearNormal();
    }
    private void clearNormal(){
        normalGroupMap.clear();
        exchangeGroupMap.clear();
        shopDecrSecond = null;
        shopAddPrice = null;
    }
    private void rebuildNewPlayerGroup()throws SQLException {
        List<Map<String, Object>> allGroups = AllDao.activityDao.getAllNewPlayerGroups();
        for (Map<String, Object> m : allGroups) {
            ActivityGroup activityGroup = new ActivityGroup(m);
            newPlayerGroupMap.put(activityGroup.getGroupId(),activityGroup);
        }
        log.debug("rebuildAllGroups, " + newPlayerGroupMap.size());

        List<Map<String, Object>> allActivitys = AllDao.activityDao.getAllNewPlayerActivityTasks();
        for (Map<String, Object> m : allActivitys) {
            ActivityBase activity = new ActivityBase(m);
            ActivityGroup group = newPlayerGroupMap.get(activity.getGroupId());
            if (group != null)
            {
                activity.setLastTime(group.getLastTime());
                group.addActivity(activity);
            }
        }
    }
    private void rebuildNormalGroup()throws SQLException{
        List<Map<String, Object>> allGroups = AllDao.activityDao.getAllNormalGroups();
        for (Map<String, Object> m : allGroups) {
            ActivityGroup activityGroup = new ActivityGroup(m);
            if (activityGroup.getType() == Const.ACT_TYPE_BUFF){
                String type_str = activityGroup.getExtra().getString("type");
                if (type_str.equals("ShopDecrSecond")){
                    shopDecrSecond = activityGroup;
                }
                else if (type_str.equals("ShopAddPrice")){
                    shopAddPrice = activityGroup;
                }
            }
            else {
                normalGroupMap.put(activityGroup.getGroupId(),activityGroup);
            }
        }

        List<Map<String, Object>> allActivitys = AllDao.activityDao.getAllNormalActivity();
        for (Map<String, Object> m : allActivitys) {
            ActivityBuy activity = new ActivityBuy(m);
            ActivityGroup group = normalGroupMap.get(activity.getGroupId());
            if (group != null)
            {
                activity.setLastTime(group.getLastTime());
                group.addActivity(activity);
            }
        }
    }
    private void rebuildExchangeGroup()throws SQLException{
        List<Map<String, Object>> allGroups = AllDao.activityDao.getAllExchangeGroups();
        for (Map<String, Object> m : allGroups) {
            ActivityGroup activityGroup = new ActivityGroup(m);
            exchangeGroupMap.put(activityGroup.getGroupId(),activityGroup);
        }

        List<Map<String, Object>> allActivitys = AllDao.activityDao.getAllExchangeActivity();
        for (Map<String, Object> m : allActivitys) {
            ActivityExchange activity = new ActivityExchange(m);
            ActivityGroup group = exchangeGroupMap.get(activity.getGroupId());
            if (group != null)
            {
                activity.setLastTime(group.getLastTime());
                group.addActivity(activity);
            }
        }
    }
    private void rebuildAllGroups() {
        try {
            clear();
            rebuildNewPlayerGroup();
            rebuildNormalGroup();
            rebuildExchangeGroup();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }
    }
    public synchronized ActivityGroup getNewPlayerGroup(int group_id){
        return newPlayerGroupMap.get(group_id);
    }
    public synchronized ActivityGroup getNormalGroup(int group_id){
        return normalGroupMap.get(group_id);
    }
    public synchronized ActivityGroup getExchangeGroup(int group_id){
        return exchangeGroupMap.get(group_id);
    }
    public ActivityBase getActivity(int group_id ,int aid){
        ActivityGroup group = getNewPlayerGroup(group_id);
        if (group == null){
            group = getNormalGroup(group_id);
        }
        if (group == null){
            group = getExchangeGroup(group_id);
        }
        return group.getActivity(aid);
    }
    public ActivityGroup getFundActivityGroups(){
        for (Map.Entry<Integer, ActivityGroup> e: newPlayerGroupMap.entrySet()) {
            ActivityGroup activityGroup = e.getValue();
            if (activityGroup.isValid() && activityGroup.getType() == Const.ACT_TYPE_FUND_LEVEL_UP){
                return activityGroup;
            }
        }
        return null;
    }

    public int getBuffShopDecrSecond(){
        if (shopDecrSecond == null) return 0 ;
        if (!shopDecrSecond.isValid()) return 0;
        JSONObject ob = shopDecrSecond.getExtra();
        return ob.getInteger("effect") ;
    }
    public int getBuffShopAddPrice(){
        if (shopAddPrice == null) return 0 ;
        if (!shopAddPrice.isValid()) return 0;
        JSONObject ob = shopAddPrice.getExtra();
        return ob.getInteger("effect") ;
    }
    public JSONObject getBuffJsonData(){
        JSONObject ob = new JSONObject();
        if (shopAddPrice == null && shopDecrSecond == null){
            ob.put("have",0);
        }
        else if (shopAddPrice != null){
            ob.put("have",1);
            ob.put("type",1);
            ob.put("effect",shopAddPrice.getExtra().getIntValue("effect"));
            ob.put("startTime",shopAddPrice.getStartTime());
            ob.put("endTime",shopAddPrice.getEndTime());
        }
        else if (shopDecrSecond != null){
            ob.put("have",1);
            ob.put("type",2);
            ob.put("effect",shopDecrSecond.getExtra().getIntValue("effect"));
            ob.put("startTime",shopDecrSecond.getStartTime());
            ob.put("endTime",shopDecrSecond.getEndTime());
        }
        return ob;
    }
    public ActivityGroup getGroup(int type , int groupId){
        if (type == 1){
            return getNewPlayerGroup(groupId);
        }
        else if (type == 2){
            return getNormalGroup(groupId);
        }
        else if (type == 3){
            return getExchangeGroup(groupId);
        }
        return null;
    }
}
