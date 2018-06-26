package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.BlueprintItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PhonePadPC on 2017/8/11.
 */
public class BlueprintConfig {
    private HashMap<Integer, BlueprintItem> _itemMap = null;
    public void InitConfig(String fname) throws Exception {
        ArrayList<BlueprintItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<BlueprintItem>>(){});
        _itemMap = new HashMap<Integer, BlueprintItem>();
        for (BlueprintItem active_rewards : tmplist) {
            _itemMap.put(active_rewards.getId(), active_rewards);
        }
    }

    public BlueprintItem getItem(int id){
        return _itemMap.get(id);
    }

    public static BlueprintConfig getInstance() {
        return BlueprintConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static BlueprintConfig instance = new BlueprintConfig();
    }
}
