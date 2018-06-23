package com.ppgames.demo.model.quest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.demo.model.quest.task.Task;

/**
 * Created by Tony on 2017/6/13.
 */
public class QuestMainLine extends Quest {
    public QuestMainLine(JSONObject object, JSONArray tasksData, long pid, QuestList list) {
        super(object, tasksData, pid, list);
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
}
