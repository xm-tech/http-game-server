package com.xxgames.demo.model.quest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.config.config.ActiveValueQuestConfig;
import com.xxgames.demo.model.quest.task.IActiveValueTask;
import com.xxgames.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class QuestActiveValue extends QuestRecordTime {

    /**
     * 活跃度的值
     */
    private int activeValue;
    private List<Boolean> rewardTakeRecord;
    private int weekActiveValue;
    private int weekBeginTime;

    public QuestActiveValue(JSONObject object, JSONArray tasksConfigData, long pid, QuestList list) {
        super(object, tasksConfigData, pid, list);
        rewardTakeRecord = new ArrayList<>();

        if (object.containsKey("rewardRecords")) {
            rewardTakeRecord = JSONObject.parseArray(object.getString("rewardRecords"), Boolean.class);
        } else {
            JSONArray rewards = JSON.parseArray(ActiveValueQuestConfig.getInstance().getItem(0).getRewards());
            for (int i = 0; i < rewards.size(); ++i) {
                rewardTakeRecord.add(false);
            }
        }

        if (object.containsKey("activeValue")) {
            activeValue = object.getIntValue("activeValue");
        } else {
            activeValue = 0;
        }

        if (object.containsKey("weekActiveValue")) {
            weekActiveValue = object.getIntValue("weekActiveValue");

        } else {
            weekActiveValue = 0;
        }

        if (object.containsKey("weekBeginTime")) {
            weekBeginTime = object.getIntValue("weekBeginTime");
            checkWeekValue();

        } else {
            weekBeginTime = TimeUtil.getTimeForWeek(12, 5);
        }


    }

    @Override
    public JSONObject getDataForClient() {
        if (checkOutOfDateByDay()) {
            reset();
        }
        JSONObject obj = super.getDataForClient();
        obj.put("activeValue", activeValue);
        obj.put("rewardTakeRecord", rewardTakeRecord);
        obj.put("weekActiveValue", weekActiveValue);
        return obj;
    }

    @Override
    public JSONObject getDataForDb()
    {
        JSONObject obj = super.getDataForDb();
        obj.put("activeValue", activeValue);
        obj.put("rewardTakeRecord", rewardTakeRecord);
        obj.put("weekActiveValue", weekActiveValue);
        return obj;
    }

    public void takeReward(int id) {
        rewardTakeRecord.set(id, true);
    }

    public boolean isRewardTaked(int id) {
        checkTimeValue();
        return rewardTakeRecord.get(id);
    }

    public List<Boolean> getRewardTakeRecord() {
        checkTimeValue();
        return rewardTakeRecord;
    }

    @Override
    public void onTaskSuccess(int taskId) {
        checkTimeValue();
        int value = ((IActiveValueTask) tasks.get(taskId)).getAwardValue();
        activeValue += value;
        weekActiveValue += value;
    }

    public int getActivity() {
        return activeValue;
    }

    public void setActivity(int activity) {
        this.activeValue = activity;
    }

    public int getWeekActiveValue() {
        return weekActiveValue;
    }

    public void setWeekActiveValue(int weekActiveValue) {
        this.weekActiveValue = weekActiveValue;
    }

    @Override
    protected void reset() {
        super.reset();
        activeValue = 0;
        for (int i = 0; i < rewardTakeRecord.size(); ++i) {
            rewardTakeRecord.set(i, false);
        }
    }

    /**
     * 判断时间戳是否和本周2 5点的时间戳一致,若不一致 清空数据
     */
    private void checkWeekValue() {
        int time = TimeUtil.getTimeForWeek(12, 5);
        if (weekBeginTime != time) {
            weekBeginTime = time;
            weekActiveValue = 0;
        }
    }

    private void checkTimeValue() {
        checkWeekValue();
        if (checkOutOfDateByDay()) {
            reset();
        }
    }
}
