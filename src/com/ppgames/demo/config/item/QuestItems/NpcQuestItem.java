package com.ppgames.demo.config.item.QuestItems;

/**
 * Created by Tony on 2017/7/17.
 */
public class NpcQuestItem extends  QuestItem{
    private int beginstoryid;
    private int successstoryid;
    private int failedstoryid;

    public void setBeginstoryid(int beginstoryid) {
        this.beginstoryid = beginstoryid;
    }
    public int getBeginstoryid() {
        return beginstoryid;
    }

    public void setSuccessstoryid(int successstoryid) {
        this.successstoryid = successstoryid;
    }
    public int getSuccessstoryid() {
        return successstoryid;
    }

    public void setFailedstoryid(int failedstoryid) {
        this.failedstoryid = failedstoryid;
    }
    public int getFailedstoryid() {
        return failedstoryid;
    }

}
