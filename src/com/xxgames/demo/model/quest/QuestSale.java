package com.xxgames.demo.model.quest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.SaleQuestConfig;
import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.config.item.PropItem;
import com.xxgames.demo.model.mail.Mail;
import com.xxgames.demo.model.mail.MailManager;
import com.xxgames.demo.utils.Const;
import com.xxgames.util.TimeUtil;

import java.util.ArrayList;

/**
 * Created by JMX on 2017/7/25.
 */
public class QuestSale extends QuestRecordTime {

    private long curSaleAmount;
    private int limitTime;

    public QuestSale(JSONObject object, JSONArray tasksData, long pid, QuestList list) {
        super(object, tasksData, pid, list);

        limitTime = SystemConfConfig.getInstance().getCfg().getSale_task_limit();
    }

    public boolean isOutOfDate() {
        int timeBegin1 = TimeUtil.getTimeForDay(-1, -1, SystemConfConfig.getInstance().getCfg().getSale_task_begin1());
        int timeEnd1 = TimeUtil.getTimeForDay(-1, -1, SystemConfConfig.getInstance().getCfg().getSale_task_end1());

        int timeBegin2 = TimeUtil.getTimeForDay(-1, -1, SystemConfConfig.getInstance().getCfg().getSale_task_begin2());
        int timeEnd2 = TimeUtil.getTimeForDay(-1, -1, SystemConfConfig.getInstance().getCfg().getSale_task_end2());

        int now = TimeUtil.nowInt();

        if (beginTime >= timeBegin1 && beginTime <= timeEnd1) {
            if (now >= timeBegin1 && now <= timeEnd1) {
                return false;
            } else {
                if (status == EQuestStatus.IN_PROGRESS.ordinal() || status == EQuestStatus.SUCCESS.ordinal())
                    return now - beginTime >= limitTime;
                else
                    return true;
            }
        } else if (beginTime >= timeBegin2 && beginTime <= timeEnd2) {
            if (now >= timeBegin2 && now <= timeEnd2) {
                return false;
            } else {
                if (status == EQuestStatus.IN_PROGRESS.ordinal() || status == EQuestStatus.SUCCESS.ordinal())
                    return now - beginTime >= limitTime;
                else
                    return true;
            }
        }

        return true;
    }

    boolean isTaskEnd() {
        int now = TimeUtil.nowInt();
        return now - beginTime >= limitTime;
    }

    public void updateSaleStatus() {
        long inCome;

        if(!isTaskEnd()){
            inCome = Cache.players.get(pid).getCountTagManager().getTagCount(Const.USER_COUNT_TAG_TODAY_SELL, beginTime, TimeUtil.nowInt());
            getQuestEventListener().dispatchEvent("sale", inCome - curSaleAmount);
        }
        else {
            inCome = Cache.players.get(pid).getCountTagManager().getTagCount(Const.USER_COUNT_TAG_TODAY_SELL, beginTime, beginTime + limitTime);
            getQuestEventListener().dispatchEvent("sale", inCome - curSaleAmount);

            if(status != EQuestStatus.SUCCESS.ordinal())
                status = EQuestStatus.FAILED.ordinal();
        }

        curSaleAmount = inCome;
    }

    public void postRewardMail()
    {
        String rewardStr = SaleQuestConfig.getInstance().getItem((int)getId()).getRewards();
        ArrayList<PropItem> attached = JSON.parseObject(rewardStr, new TypeReference<ArrayList<PropItem>>(){});
        MailManager.getInstance().insertMail(pid, 0, "gm", "销售任务奖励", "", attached, Mail.MTYPE_SYSTEM);
    }

    @Override
    public JSONObject getDataForClient() {
        JSONObject obj = super.getDataForClient();
        obj.put("saleAmount", curSaleAmount);
        obj.put("restTime", limitTime + beginTime - TimeUtil.nowInt());
        return obj;
    }
}