package com.ppgames.demo.model.quest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by Tony on 2017/6/13.
 */
public class QuestFacotry {
    /**
     * @param type 任务类型
     * @param savedData 数据库存档数据(如任务的状态，上次任务的时间等等信息)
     * @param tasksConfigData 配置文件中的tasks的数据
     * @param pid 玩家的id
     * @param list questList的引用
     *
     * @return
     */
    public static Quest createQuest(EQuestType type, JSONObject savedData, JSONArray tasksConfigData, long pid,
                                    QuestList list) {
        switch (type) {
        case MAIN_LINE:
            Quest q = new QuestMainLine(savedData, tasksConfigData, pid, list);
            return q;
        case ACTIVE_VALUE:
            return new QuestActiveValue(savedData, tasksConfigData, pid, list);
        case DAILY_CUSTOMER:
            return new QuestCustomer(savedData, tasksConfigData, pid, list);
        case DAILY_NPC:
            return new QuestNpc(savedData, tasksConfigData, pid, list);
        case DAILY_SALE:
            return new QuestSale(savedData, tasksConfigData, pid, list);
        case ACTIVITY:
            return new QuestActivity(savedData, tasksConfigData, pid, list);
        default:
            return null;
        }
    }
}

