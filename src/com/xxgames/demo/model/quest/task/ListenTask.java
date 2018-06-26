package com.xxgames.demo.model.quest.task;

import com.xxgames.demo.model.quest.questEvent.IQuestEventCallBack;

/**
 * Created by Tony on 2017/6/21.
 */
public abstract class ListenTask extends Task implements IListenTask, IQuestEventCallBack {

    protected String eventName;

    @Override
    public void registListner() {
        quest.getQuestEventListener().registEventListener(this.eventName, this);
    }

    public void setEventName(String eventName) {
        if (eventName != null) {
            this.eventName = eventName;
        }
    }
}
