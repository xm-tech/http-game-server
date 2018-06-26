package com.xxgames.demo.model.quest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventListener;
import com.xxgames.demo.model.quest.task.ETaskType;
import com.xxgames.demo.model.quest.task.ITaskFinish;
import com.xxgames.demo.model.quest.task.Task;
import com.xxgames.demo.model.quest.task.TaskFacotry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 2017/6/12.
 */
public abstract class Quest implements ITaskFinish {

    protected List<Task> tasks = new ArrayList<>();
    protected long id;
    protected int status;
    protected int type;
    protected long nextId;
    protected long pid;
    protected QuestList questList;
    private QuestEventListener questEventListener;

    public Quest() {
    }

    public Quest(JSONObject object, JSONArray tasksData, long pid, QuestList list) {
        /*
        JSONArray taskArray = object.getJSONArray("tasks");
        for (Object taskObject : taskArray){
            JSONObject taskJsonObject = (JSONObject)taskObject;
            Task task = TaskFacotry.createTask(ETaskType.values()[((JSONObject) taskObject).getIntValue("type")], taskJsonObject);
            quest.addTask(task);
        }
        */
        this.pid = pid;
        Player p = Cache.players.get(pid);
        questEventListener = p.questEventListener;
        questList = list;
        Init(object, tasksData);
    }

    /*
    public Quest(Map m, JSONArray tasksData) {
        long id = Long.parseLong(m.get("id").toString());
        int type = Integer.parseInt(m.get("type").toString());
        JSONObject questData;
        switch (type) {
        case 0: //MAIN_LINE
            questData = DataManager.main_line_quests.getJSONObject(id + "");
            break;
        case 1: //ACTIVE_VALUE
            questData = DataManager.active_value_quest.getJSONObject(id + "");
            break;
        default:
            return;
        }

        Init(questData, tasksData);
        status = Integer.parseInt(m.get("status").toString());
    }
    */

    public void addTask(Task task) {
        tasks.add(task);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void onTaskSuccess(int taskId) {
        for (Task task : tasks) {
            if (task.getStatus() != Task.ETaskStatus.SUCCESS.ordinal()) {
                return;
            }
        }

        status = EQuestStatus.SUCCESS.ordinal();
    }

    @Override
    public void onTaskFailed(int taskId) {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public JSONObject getDataForClient() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("type", type);
        obj.put("status", status);
        obj.put("tasks", tasks);
        return obj;
    }

    public JSONObject getDataForDb()
    {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("status", status);
        obj.put("tasks", tasks);
        return obj;
    }


    public long getPid() {
        return pid;
    }

    protected void Init(JSONObject object, JSONArray tasksData) {
        setId(object.getIntValue("id"));
        JSONArray taskArray = JSON.parseArray(object.getString("tasks"));
        if (taskArray == null){
            taskArray = tasksData;
        }
        int i = 0;
        for (Object obj : taskArray) {
            JSONObject taskJsonObject = (JSONObject) obj;
//            System.out.println("taskJsonObject:"+taskJsonObject + ", tasksData:"+tasksData);
            Task task = TaskFacotry.createTask(ETaskType.values()[taskJsonObject.getIntValue("type")], taskJsonObject,
                                               tasksData.getJSONObject(i), this);
            addTask(task);
            task.Init();
            i++;
        }

    }

    public QuestEventListener getQuestEventListener() {
        return questEventListener;
    }

    public void setQuestEventListener(QuestEventListener questEventListener) {
        this.questEventListener = questEventListener;
    }
}
