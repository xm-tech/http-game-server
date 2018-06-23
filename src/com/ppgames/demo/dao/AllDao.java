package com.ppgames.demo.dao;

public class AllDao {

    public static PlayerDao pd;
    public static PassPortDao passPortDao;
    public static MailDao md;
    public static QuestDao qd;
    public static TagDao tagDao;
    public static ActivityDao activityDao;

    public static void init() {
        pd = new PlayerDao();
        md = new MailDao();
        qd = new QuestDao();
        tagDao = new TagDao();
        passPortDao = new PassPortDao();
        activityDao = new ActivityDao();
        System.err.println("AllDao init succ");
    }
}
