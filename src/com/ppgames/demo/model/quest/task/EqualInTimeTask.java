package com.ppgames.demo.model.quest.task;

import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.questEvent.QuestEvent;

/**
 * Created by PhonePadPC on 2017/8/31.
 */
public class EqualInTimeTask extends ListenTask {
    protected int completeValue;
    private int startTime ;
    private int endTime;
    public void handleEvent(QuestEvent fe) {
        checkComplete(Integer.parseInt(fe.getObj().toString()));
    }
    @Override
    public void Init() {
        super.Init();
        Player p = Cache.players.get(quest.getPid());
        checkComplete(p.getPlayerDataInTime(eventName,startTime,endTime));
    }
    public void setCompleteValue(int value) {
        completeValue = value;
    }

    void checkComplete(int value) {
        if (value >= completeValue) {
            status = ETaskStatus.SUCCESS.ordinal();
            quest.getQuestEventListener().unregistEventListener(eventName, this);
            finishCallBack.onTaskSuccess(id);
        }
    }
}
