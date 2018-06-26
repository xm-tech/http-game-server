package com.xxgames.core;

public class ErrCode {

    public static final int UNKONW_ERR = -1;

    public static final int SYS_MAINTINING = 1000;
    // 客户端报网络错误即可
    public static final int DB_EXEC_ERR = 1001;


    public static final int SUCC = 0;
    public static final int DIAMOND_NOT_ENOUGH = 1;
    public static final int GOLD_NOT_ENOUGH = 2;
    public static final int ITEM_NOT_ENOUGH = 3;
    public static final int ACCOUNT_BANNED = 4;
    public static final int REPEAT_RENAME = 5;
    public static final int EXCEED_BAG_LIMIT = 6;
    public static final int MAIL_EMPTY = 7;
    public static final int MAIL_NOT_EXIST = 8;
    public static final int ACTIVE_VALUE_NOT_ENOUGH = 9;
    public static final int CLOTHES_SOLD_OUT = 10;

    //任务
    public static final int QUEST_OUT_OF_DATE = 2001;
    public static final int QUEST_ITEM_INCOMPATIBLE = 2002; //任务物品不匹配
}
