package com.ppgames.demo.model.quest.task;

/**
 * Created by Tony on 2017/6/13.
 */
public class AwardValueTask extends EqualOrMoreTask {
    /**
     * 保存奖励的数值
     */
    private int awardValue;


    public int getAwardValue() {
        return awardValue;
    }

    public void setAwardValue(int awardValue) {
        this.awardValue = awardValue;
    }
}
