package com.xxgames.demo.config.item;

/**
 * Created by Administrator on 2017/7/15.
 */
public class DressRoomLevelItem {
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getBuff() {
        return buff;
    }

    public void setBuff(int buff) {
        this.buff = buff;
    }

    public int getNeed_level() {
        return need_level;
    }

    public void setNeed_level(int need_level) {
        this.need_level = need_level;
    }

    private int level;
    private int gold;
    private int buff;
    private int need_level;
}
