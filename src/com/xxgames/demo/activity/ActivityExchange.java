package com.xxgames.demo.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.PropItem;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by PhonePadPC on 2017/9/1.
 */
public class ActivityExchange extends ActivityBase {
    public ActivityExchange(Map m){
        super.setAid(Integer.parseInt(m.get("aid").toString()));
        super.setGroupId(Integer.parseInt(m.get("groupId").toString()));
        super.setName(m.get("name").toString());
        super.setIntro(m.get("intro").toString());
        super.setRewards(JSON.parseObject(m.get("rewards").toString(), new TypeReference<ArrayList<PropItem>>(){}));
        setExchangeGid(Integer.parseInt(m.get("exchangeGid").toString()));
        setNeedNum(Integer.parseInt(m.get("needNum").toString()));
        setTotalTimes(Integer.parseInt(m.get("times").toString()));
    }

    public int getExchangeGid() {
        return exchangeGid;
    }

    public void setExchangeGid(int exchangeGid) {
        this.exchangeGid = exchangeGid;
    }

    public int getNeedNum() {
        return needNum;
    }

    public void setNeedNum(int needNum) {
        this.needNum = needNum;
    }

    private int exchangeGid ;
    private int needNum ;

    public int getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(int totalTimes) {
        this.totalTimes = totalTimes;
    }

    private int totalTimes;
}
