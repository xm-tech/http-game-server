package com.xxgames.demo.model.quest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.model.quest.task.DressUpTask;
import com.xxgames.util.TimeUtil;

import java.util.List;

/**
 * Created by Tony on 2017/6/23.
 */
public class QuestNpc extends QuestRecordTime {

    //对应配置表里的id
    private long dataId;

    public QuestNpc(JSONObject object, JSONArray tasksData, long pid, QuestList list) {
        super(object, tasksData, pid, list);
    }

    @Override
    public void onTaskFailed(int taskId) {
        //questList.
        status = EQuestStatus.FAILED.ordinal();
        questList.npcQuestFailed();
    }

    @Override
    public void onTaskSuccess(int taskId) {
        super.onTaskSuccess(taskId);

        if (status == EQuestStatus.SUCCESS.ordinal()) {
            questList.npcQuestFinish();
        }
    }

    public boolean isOutOfDate() {
        return TimeUtil.nowInt() - beginTime >= SystemConfConfig.getInstance().getCfg().getNpc_task_last();
    }

    public JSONObject submit(List<Integer> equips) {
        return ((DressUpTask) tasks.get(0)).checkFinish(equips);
    }

    @Override
    public JSONObject getDataForClient() {
        JSONObject obj = super.getDataForClient();
        obj.put("dataId", dataId);
        return obj;
    }

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }
}
