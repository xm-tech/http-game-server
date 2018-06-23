package com.ppgames.demo.model.quest.task;

import com.ppgames.demo.model.quest.Quest;

public abstract class Task {

    protected int id;
    protected int type;
    protected int status;
    protected ITaskFinish finishCallBack;
    protected Quest quest;

    public Task() {
    }

    public Task(int id, ITaskFinish finishCallBack) {
        this.id = id;
        this.status = ETaskStatus.IN_PROGRESS.ordinal();
        this.finishCallBack = finishCallBack;
    }

    public void Init() {
    }

    public int getId() {
        return id;
    }

    public void setId(int value) {
        id = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int value) {
        type = value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int value) {
        this.status = value;
    }

    public void reset() {
        status = ETaskStatus.IN_PROGRESS.ordinal();
    }

    public void setFinishCallBack(ITaskFinish value) {
        finishCallBack = value;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public enum ETaskStatus {
        IN_PROGRESS,
        SUCCESS,
        FAILED
    }

}
