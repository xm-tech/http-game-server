package com.xxgames.demo.model.quest.task;


import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.util.BagType;

/**
 * Created by Tony on 2017/6/21.
 */
public class PayTask extends Task implements IPayTask {
    protected int payType;
    protected int payCount;
    protected int payedNum;
    private int budget;

    /**
     * 系统回收一件服饰
     *
     * @param type
     * @param eid
     * @param num
     *
     * @return
     */
    @Override
    public boolean payItem(int type, int eid, int num) {
        if (status == ETaskStatus.IN_PROGRESS.ordinal()) {
            Player p = Cache.players.get(quest.getPid());
            if (!p.bagEnough(BagType.EQUIP, eid, num)) {
                return false;
            }

            if (payType == type) {
                payedNum += num;
                if (payedNum >= payCount) {
                    missionComplete(eid);
                }
            }
        }
        return false;
    }

    public void init() {

    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayedNum() {
        return payedNum;
    }

    public void setPayedNum(int payedNum) {
        this.payedNum = payedNum;
    }

    public void setPayCount(int payCount) {
        this.payCount = payCount;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    protected void missionComplete(int eid) {
        Player p = Cache.players.get(quest.getPid());
        payedNum = payCount;
        status = Task.ETaskStatus.SUCCESS.ordinal();
        finishCallBack.onTaskSuccess(id);
        p.decrBags("PayTask", BagType.EQUIP, eid, 1);
    }
}
