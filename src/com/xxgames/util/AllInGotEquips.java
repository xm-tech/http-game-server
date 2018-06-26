package com.xxgames.util;

import com.alibaba.fastjson.JSONArray;

import java.security.InvalidParameterException;

/**
 * 判断是否在图鉴里头抽取成1个类
 */
public class AllInGotEquips {
    private boolean in;
    private int notInId;

    /**
     * @param equipids 待验证服饰id集
     * @param playerGotEquips 玩家图鉴
     */
    public AllInGotEquips(JSONArray equipids, JSONArray playerGotEquips) {
        if (equipids == null || equipids.size() == 0) {
            throw new InvalidParameterException("equipids length is zero");
        }
        if (playerGotEquips == null || playerGotEquips.size() == 0) {
            setIn(false);
            setNotInId(equipids.getIntValue(0));
            return;
        }
        setIn(true);
        for (int i = 0; i < equipids.size(); i++) {
            int equipid = equipids.getIntValue(i);
            if (!playerGotEquips.contains(equipid)) {
                setIn(false);
                setNotInId(equipid);
                break;
            }
        }
    }

    public boolean isIn() {
        return in;
    }

    public void setIn(boolean in) {
        this.in = in;
    }

    public int getNotInId() {
        return notInId;
    }

    public void setNotInId(int notInId) {
        this.notInId = notInId;
    }
}
