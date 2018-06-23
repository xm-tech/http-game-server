package com.ppgames.demo.model.quest.task;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.demo.model.quest.Quest;

public class TaskFacotry {

    public static Task createTask(ETaskType type, JSONObject object, JSONObject configData, Quest quest) {
        Task task;
        switch (type) {
        case EQUAL_OR_MORE:
            task = object.parseObject(object.toJSONString(), EqualOrMoreTask.class);
            break;
        case AWARD_VALUE:
            task = object.parseObject(object.toJSONString(), AwardValueTask.class);
            break;
        case DO_ACTION:
            task = object.parseObject(object.toJSONString(), DoActionTask.class);
            ((DoActionTask) task).setCount(object.getIntValue("count"));
            break;
        case AWARD_DO_ACTION:
            task = object.parseObject(configData.toJSONString(), AwardDoActionTask.class);
            ((AwardDoActionTask) task).setAwardValue(object.getIntValue("awardValue"));
            ((AwardDoActionTask) task).setActionNumber(object.getIntValue("actionNumber"));
            break;
        case PAY_EQUIP_TASK:
            task = object.parseObject(object.toJSONString(), PayEquipTask.class);
//            ((PayEquipTask) task).init();
            ((PayEquipTask) task).initStyle(quest.getId());
            break;
        case DRESS_UP_TASK:
            task = object.parseObject(object.toJSONString(), DressUpTask.class);
            break;
        case EQUAL:
            task = object.parseObject(object.toJSONString(), EqualTask.class);
            break;
        case EQUAL_IN_TIME:
            task = object.parseObject(object.toJSONString(), EqualInTimeTask.class);
            break;
        default:
            return null;
        }

        task.setQuest(quest);
        task.setFinishCallBack(quest);
        if (task instanceof ListenTask) {
            ((ListenTask) task).registListner();
        }

        return task;
    }
}
