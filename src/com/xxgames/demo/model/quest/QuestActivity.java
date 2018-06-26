package com.xxgames.demo.model.quest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.activity.ActivityBase;
import com.xxgames.demo.activity.ActivityManager;
import com.xxgames.demo.model.quest.task.Task;
import com.xxgames.util.TimeUtil;

/**
 * Created by PhonePadPC on 2017/8/1.
 */
public class QuestActivity extends QuestRecordTime {
    private long dataId;
    private int lastTime ;
    private int groupId ;
    private int aid ;

    public ActivityBase getActivity() {
        return activity;
    }

    private ActivityBase activity ;
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }
    public QuestActivity(JSONObject object, JSONArray tasksData, long pid, QuestList list) {
        super(object, tasksData, pid, list);
        lastTime = object.getInteger("lastTime");
        groupId = object.getInteger("groupId");
        aid = object.getInteger("aid");
        activity = ActivityManager.getInstance().getActivity(groupId,aid);
    }

    @Override
    public JSONObject getDataForDb()
    {
        JSONObject obj = super.getDataForDb();
        return obj;
    }

    @Override
    public void onTaskSuccess(int taskId) {
        for (Task task : tasks) {
            if (task.getStatus() != Task.ETaskStatus.SUCCESS.ordinal()) {
                return;
            }
        }
        if (status == EQuestStatus.IN_PROGRESS.ordinal()){
            status = EQuestStatus.SUCCESS.ordinal();
        }
    }

    public boolean isOutOfDate() {
        if (lastTime < 0) return false;
        return beginTime + lastTime < TimeUtil.nowInt();
    }

    @Override
    public JSONObject getDataForClient() {

        JSONObject obj = new JSONObject();
        obj.put("aid",aid);
        obj.put("name",activity.getName());
        obj.put("intro",activity.getIntro());
        obj.put("rewards",activity.getRewards());
        obj.put("status",getStatus());
        return obj;
    }

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }
}
