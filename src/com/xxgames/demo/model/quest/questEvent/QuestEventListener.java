package com.xxgames.demo.model.quest.questEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestEventListener {
    private HashMap<String, List<IQuestEventCallBack>> registedCallbacks =
        new HashMap<String, List<IQuestEventCallBack>>();
    private HashMap<String, List<IQuestEventCallBack>> registedCallbacksPending =
        new HashMap<String, List<IQuestEventCallBack>>();
    private HashMap<String, List<IQuestEventCallBack>> unregistedCallbacksPending =
        new HashMap<String, List<IQuestEventCallBack>>();
    private Boolean isEnuming = false;

    public void registEventListener(String eventName, IQuestEventCallBack callBack) {
        if (!this.registedCallbacks.containsKey(eventName)) {
            registedCallbacks.put(eventName, new ArrayList<IQuestEventCallBack>());
        }

        if (isEnuming) {
            if (!registedCallbacksPending.containsKey(eventName)) {
                registedCallbacksPending.put(eventName, new ArrayList<IQuestEventCallBack>());
            }
            registedCallbacksPending.get(eventName).add(callBack);
            return;
        }

        this.registedCallbacks.get(eventName).add(callBack);
    }

    public void unregistEventListener(String eventName, IQuestEventCallBack callBack) {
        if (!this.registedCallbacks.containsKey(eventName)) {
            return;
        }

        if (isEnuming) {
            if (!unregistedCallbacksPending.containsKey(eventName)) {
                unregistedCallbacksPending.put(eventName, new ArrayList<IQuestEventCallBack>());
            }
            unregistedCallbacksPending.get(eventName).add(callBack);
            return;
        }

        this.registedCallbacks.get(eventName).remove(callBack);
    }

    public void dispatchEvent(String eventName, Object eventValue) {
        if (!this.registedCallbacks.containsKey(eventName)) {
            return;
        }

        isEnuming = true;

        for (int i = 0; i < registedCallbacks.get(eventName).size(); i++) {
            IQuestEventCallBack ecb = registedCallbacks.get(eventName).get(i);
            if (ecb != null) {
                ecb.handleEvent(new QuestEvent(eventValue, eventName));
            }
        }

        for (String sEventName : this.registedCallbacksPending.keySet()) {
            for (IQuestEventCallBack ec : registedCallbacksPending.get(sEventName)) {
                if (!registedCallbacks.containsKey(sEventName)) {
                    registedCallbacks.put(sEventName, new ArrayList<IQuestEventCallBack>());
                }
                registedCallbacks.get(sEventName).add(ec);
            }
        }
        registedCallbacksPending.clear();

        for (String sEventName : this.unregistedCallbacksPending.keySet()) {
            for (IQuestEventCallBack ec : unregistedCallbacksPending.get(sEventName)) {
                if (registedCallbacks.containsKey(sEventName) && registedCallbacks.get(sEventName).contains(ec)) {
                    registedCallbacks.get(sEventName).remove(ec);
                }
            }
        }
        unregistedCallbacksPending.clear();
        isEnuming = false;

    }
}
