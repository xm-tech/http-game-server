package com.xxgames.demo.config.item.QuestItems;

/**
 * Created by Tony on 2017/7/17.
 */
public class TaskItem {
    private int id;
    private int type;
    private String eventname;
    private int count;
    private String desc;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }
    public String getEventname() {
        return eventname;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }
}
