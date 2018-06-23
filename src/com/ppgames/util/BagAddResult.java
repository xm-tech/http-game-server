package com.ppgames.util;

import com.alibaba.fastjson.JSONObject;


/**
 * 背包添加结果抽取
 */
public class BagAddResult {
    boolean succ;
    JSONObject added;

    public BagAddResult() {
        succ = true;
        added = new JSONObject();
    }

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    public JSONObject getAdded() {
        return added;
    }

    public void setAdded(JSONObject added) {
        this.added = added;
    }
}
