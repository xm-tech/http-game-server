package com.xxgames.demo.config.item;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class CheckInChestItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNeed_days() {
        return need_days;
    }

    public void setNeed_days(int need_days) {
        this.need_days = need_days;
    }

    public ArrayList<Integer> getItem_id() {
        return item_id;
    }

    public void setItem_id(ArrayList<Integer> item_id) {
        this.item_id = item_id;
    }

    public ArrayList<Integer> getItem_type() {
        return item_type;
    }

    public void setItem_type(ArrayList<Integer> item_type) {
        this.item_type = item_type;
    }

    public ArrayList<Integer> getItem_num() {
        return item_num;
    }

    public void setItem_num(ArrayList<Integer> item_num) {
        this.item_num = item_num;
    }

    private int id;
    private int need_days;
    private ArrayList<Integer> item_id;
    private ArrayList<Integer>item_type;
    private ArrayList<Integer>item_num;
}
