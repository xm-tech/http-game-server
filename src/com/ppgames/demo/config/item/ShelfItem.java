package com.ppgames.demo.config.item;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
public class ShelfItem {
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

    public List<Integer> getType() {
        return type;
    }

    public void setType(List<Integer> type) {
        this.type = type;
    }

    public List<Integer> getSell_type() {
        return sell_type;
    }

    public void setSell_type(List<Integer> sell_type) {
        this.sell_type = sell_type;
    }

    public List<Integer> getStart_state() {
        return start_state;
    }

    public void setStart_state(List<Integer> start_state) {
        this.start_state = start_state;
    }

    public List<Integer> getOpen_diamond() {
        return open_diamond;
    }

    public void setOpen_diamond(List<Integer> open_diamond) {
        this.open_diamond = open_diamond;
    }

    public List<Integer> getOpen_gold() {
        return open_gold;
    }

    public void setOpen_gold(List<Integer> open_gold) {
        this.open_gold = open_gold;
    }

    private int id;
    private int open_type;
    private int open_para;
    private int gold;
    private int diamond;
    private List<Integer> type;
    private List<Integer> sell_type;
    private List<Integer> start_state;
    private List<Integer> open_diamond;
    private List<Integer> open_gold;
}
