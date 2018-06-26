package com.xxgames.demo.model.quest.task;

/**
 * Created by Tony on 2017/6/9.
 */

public interface ITaskFinish {
    void onTaskSuccess(int taskId);

    void onTaskFailed(int taskId);
}
