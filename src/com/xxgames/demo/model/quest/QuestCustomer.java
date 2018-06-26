package com.xxgames.demo.model.quest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;
import com.xxgames.demo.model.quest.task.PayEquipTask;
import com.xxgames.demo.model.quest.task.Task;
import com.xxgames.util.TimeUtil;

/**
 * Created by Tony on 2017/6/21.
 */
public class QuestCustomer extends QuestRecordTime {

    private long dataId;

    public QuestCustomer(JSONObject object, JSONArray tasksData, long pid, QuestList list) {
        super(object, tasksData, pid, list);
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

        status = EQuestStatus.SUCCESS.ordinal();
    }

    public void takeRewards() {
        status = EQuestStatus.FINISH.ordinal();
        getQuestEventListener().dispatchEvent(QuestEventId.CustomerTask, 1);
        questList.customerQuestFinish(this);
    }

    public boolean isOutOfDate() {
        //Fix 从配置表读取
        return TimeUtil.nowInt() - beginTime >= SystemConfConfig.getInstance().getCfg().getCustomer_task_last();
    }

    public boolean payEquip(int eid, int num) {
        if (eid <= 0) {
            return false;
        }
        int type = DataManager.getEquipType(eid);//DataManager.equips.getJSONObject(eid + "").getInteger("type");

        return ((PayEquipTask) tasks.get(0)).payItem(type, eid, num);
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
