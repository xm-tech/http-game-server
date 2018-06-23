package com.ppgames.demo.model.quest.task;

import com.ppgames.demo.model.quest.questEvent.QuestEvent;

/**
 * Created by Tony on 2017/6/13.
 */
public class DoActionTask extends ListenTask {
    protected int count = 1;
    protected int actionNumber = 0;

    public void handleEvent(QuestEvent fe) {
        if (status == ETaskStatus.IN_PROGRESS.ordinal()) {
            actionNumber += Integer.parseInt(fe.getObj().toString());
            if (actionNumber >= count) {
                actionNumber = count;
                status = Task.ETaskStatus.SUCCESS.ordinal();
                quest.getQuestEventListener().unregistEventListener(eventName, this);
                finishCallBack.onTaskSuccess(id);
            }
        }
    }

    public void setCount(int value) {
        count = value;
    }

    @Override
    public void reset() {
        super.reset();
        //重新监听事件
        quest.getQuestEventListener().unregistEventListener(eventName, this);
        quest.getQuestEventListener().registEventListener(eventName, this);

        //重置记录的数据
        actionNumber = 0;
    }

    public int getActionNumber() {
        return actionNumber;
    }

    public void setActionNumber(int value) {
        actionNumber = value;
    }


}
