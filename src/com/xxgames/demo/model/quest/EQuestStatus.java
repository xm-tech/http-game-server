package com.xxgames.demo.model.quest;

/**
 * Created by Tony on 2017/6/12.
 */
public enum EQuestStatus {
    IN_PROGRESS,
    SUCCESS,            //任务成功
    FAILED,             //任务失败
    REJECTED,            //任务被玩家或者系统取消了
    FINISH              //任务结束,玩家已经领取奖励
}
