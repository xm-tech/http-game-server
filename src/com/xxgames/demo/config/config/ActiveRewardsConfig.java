package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.ActiveRewardsItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PhonePadPC on 2017/7/13.
 */
public class ActiveRewardsConfig {
    private HashMap<Integer, ActiveRewardsItem> _active_rewards_map = null;
    public void InitConfig(String fname) throws Exception {
        ArrayList<ActiveRewardsItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<ActiveRewardsItem>>(){});
        _active_rewards_map = new HashMap<Integer, ActiveRewardsItem>();
        for (ActiveRewardsItem active_rewards : tmplist) {
            _active_rewards_map.put(active_rewards.getId(), active_rewards);
        }
    }
    public ActiveRewardsItem getItem(int id){
        return _active_rewards_map.get(id);
    }
    public HashMap<Integer, ActiveRewardsItem> getMap(){
        return _active_rewards_map;
    }


    public static ActiveRewardsConfig getInstance() {
        return ActiveRewardsConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static ActiveRewardsConfig instance = new ActiveRewardsConfig();
    }
}
