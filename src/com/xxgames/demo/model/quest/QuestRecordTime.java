package com.xxgames.demo.model.quest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.model.quest.task.Task;
import com.xxgames.util.TimeUtil;

/**
 * Created by Tony on 2017/6/13.
 */

public abstract class QuestRecordTime extends Quest {

    /**
     * 记录任务开始时间,单位秒
     */
    protected int beginTime;

    public QuestRecordTime() {
    }

    public QuestRecordTime(JSONObject object, JSONArray tasksData, long pid, QuestList list) {
        super(object, tasksData, pid, list);
        if (object.containsKey("beginTime")) {
            this.beginTime = object.getIntValue("beginTime");
        } else {
            this.beginTime = TimeUtil.nowInt();
        }
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    @Override
    public JSONObject getDataForClient() {
        JSONObject obj = super.getDataForClient();
        obj.put("beginTime", beginTime);
        return obj;
    }

    @Override
    public JSONObject getDataForDb()
    {
        JSONObject obj = super.getDataForDb();
        obj.put("beginTime", beginTime);
        return obj;
    }

    protected boolean checkOutOfDateByDay() {
        if (TimeUtil.sameDay(beginTime, TimeUtil.nowInt())) {
            return false;
        } else {
            return true;
        }
    }

    protected void resetAllTasks() {
        for (Task task : tasks) {
            task.reset();
        }
    }

    protected void reset() {
        resetAllTasks();
        status = EQuestStatus.IN_PROGRESS.ordinal();
        beginTime = TimeUtil.nowInt();
    }

}
