package com.xxgames.demo.config.item.QuestItems;


/**
 * Created by Tony on 2017/7/17.
 */
public abstract class QuestItem {
    private int id;

    private String tasks;
    private String rewards;

    public String getBuy_random() {
        return buy_random;
    }

    public void setBuy_random(String buy_random) {
        this.buy_random = buy_random;
    }

    private String buy_random;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }
    public String getTasks() {
        return tasks;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }
    public String getRewards() {
        return rewards;
    }
}
