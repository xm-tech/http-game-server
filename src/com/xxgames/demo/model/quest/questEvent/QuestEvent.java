package com.xxgames.demo.model.quest.questEvent;

import java.util.EventObject;

/**
 * Created by Tony on 2017/6/12.
 */
public class QuestEvent extends EventObject {
    private Object obj;
    private String sName;

    public QuestEvent(Object source, String sName) {
        super(source);
        this.obj = source;
        this.sName = sName;
    }

    public Object getObj() {
        return obj;
    }

    public String getsName() {
        return sName;
    }
}
