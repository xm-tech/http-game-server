package com.xxgames.demo.config.item.QuestItems;

/**
 * Created by Tony on 2017/7/17.
 */

public class MainLineQuestItem extends QuestItem {

    private int nextId;

    private int storyId;


    public void setNextId(int nextId) {
        this.nextId = nextId;
    }
    public int getNextId() {
        return nextId;
    }


    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }
    public int getStoryId() {
        return storyId;
    }

}