package com.xxgames.demo.config.item;

/**
 * Created by Administrator on 2017/7/15.
 */
public class LogisticsItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOpen_type() {
        return open_type;
    }

    public void setOpen_type(int open_type) {
        this.open_type = open_type;
    }

    public int getOpen_para() {
        return open_para;
    }

    public void setOpen_para(int open_para) {
        this.open_para = open_para;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    int id;
    int open_type;
    int open_para;
    int gold;
    int diamond;
}
