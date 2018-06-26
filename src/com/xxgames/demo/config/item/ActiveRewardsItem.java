package com.xxgames.demo.config.item;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;

/**
 * Created by PhonePadPC on 2017/7/14.
 */
public class ActiveRewardsItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id ;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

//    public String getRewards() {
//        return rewards;
//    }
    public ArrayList<PropItem> getRewardsList() {
        return rewards_list;
    }
    public void setRewards(String rewards) {
        this.rewards = rewards;
        rewards_list = JSON.parseObject(this.rewards, new TypeReference<ArrayList<PropItem>>(){});
    }

    private int rate;
    private String rewards ;



    private ArrayList<PropItem> rewards_list = null;
}
