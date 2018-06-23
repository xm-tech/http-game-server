package com.ppgames.demo.config.item;

/**
 * Created by Administrator on 2017/7/15.
 */
public class LogisticsLevelItem {
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int level;
    private int need_level;
    private int gold;
    private int time;
    private int num;
    private String iconPath;
    private String name;
}
