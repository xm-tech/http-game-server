package com.ppgames.demo.model.quest;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tony on 2017/6/12.
 */
public class QuestManager {

    private static Logger logger = Logger.getLogger(QuestManager.class);
    private Map<Long, QuestList> questListManaager = new ConcurrentHashMap<Long, QuestList>();

    public static QuestManager getInstance() {
        return SingletonHolder.instance;
    }

    public Map<Long, QuestList> getQuestListManaager() {
        return this.questListManaager;
    }

    public void AddQuestList(Long playerId, QuestList questList) {
        questListManaager.put(playerId, questList);
    }

    public QuestList getQuestList(long pid) {
        if (questListManaager == null) {
            logger.error("getQuestList() id = " + pid + ", error = " + " manager is null!");
            return null;
        }

        if (!questListManaager.containsKey(pid)) {
            logger.error("getQuestList() id = " + pid + ", error = " + " pid is not exist!");
            return null;
        }

        return questListManaager.get(pid);
    }

    static class SingletonHolder {
        static QuestManager instance = new QuestManager();
    }
}
