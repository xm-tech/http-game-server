package com.ppgames.demo.config.item;

/**
 * Created by Administrator on 2017/7/15.
 */
public class CashRegisterLevelItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDecrsecond() {
        return decrsecond;
    }

    public void setDecrsecond(int decrsecond) {
        this.decrsecond = decrsecond;
    }

    public int getNeed_level() {
        return need_level;
    }

    public void setNeed_level(int need_level) {
        this.need_level = need_level;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    private int id;
    private int level;
    private int decrsecond;
    private int need_level;
    private int gold;
}
