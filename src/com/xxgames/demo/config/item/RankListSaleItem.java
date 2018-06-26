package com.xxgames.demo.config.item;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;

/**
 * Created by PhonePadPC on 2017/7/14.
 */
public class RankListSaleItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
        this.rewards_list = rewards_list;
        rewards_list = JSON.parseObject(this.rewards, new TypeReference<ArrayList<PropItem>>(){});
    }

    public ArrayList<PropItem> getRewards_list() {
        return rewards_list;
    }

    private int id ;
    private int max;
    private int min ;
    private String rewards ;
    private ArrayList<PropItem> rewards_list = null;
}
