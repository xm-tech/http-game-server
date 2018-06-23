package com.ppgames.demo.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by PhonePadPC on 2017/8/7.
 */
public class SettleResult {
    private long totalGold;
    private int totalExp;
    // <货架id,金币收益>
    private JSONObject shelfGoldMap;

    public SettleResult() {
        totalGold = 0L;
        totalExp = 0;
        shelfGoldMap = new JSONObject();
    }
    public void clear(){
        totalGold = 0L;
        totalExp = 0;
        shelfGoldMap = new JSONObject();
    }
    public void add(int shelfid, long goldAdd) {
        long shelfGold = shelfGoldMap.getLongValue(shelfid + "");
        shelfGold += goldAdd;
        shelfGoldMap.put(shelfid + "", shelfGold);
        totalGold += goldAdd;
    }

    public void addExp(int expAdd) {
        totalExp += expAdd;
    }

    public JSONObject toJson() {
        JSONObject ret = new JSONObject();
        ret.put("totalGold", totalGold);
        ret.put("totalExp", totalExp);
        ret.put("shelfGoldMap", shelfGoldMap);
        return ret;
    }
}
