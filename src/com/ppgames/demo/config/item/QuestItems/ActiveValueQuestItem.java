package com.ppgames.demo.config.item.QuestItems;

import java.util.List;

/**
 * Created by Tony on 2017/7/17.
 */
public class ActiveValueQuestItem extends QuestItem{
    private List<Integer> conditions;

    public List<Integer> getConditions() {
        return conditions;
    }

    public void setConditions(List<Integer> conditions) {
        this.conditions = conditions;
    }
}
