package com.ppgames.demo.model.quest.questEvent;

import java.util.EventListener;

/**
 * Created by Tony on 2017/6/12.
 */
public interface IQuestEventCallBack extends EventListener {

    void handleEvent(QuestEvent me);

}
