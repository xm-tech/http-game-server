package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.GardenStoreItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class GardenStoreConfig {
    private HashMap<Integer, GardenStoreItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<GardenStoreItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<GardenStoreItem>>(){});
        _itemMap = new HashMap<Integer, GardenStoreItem>();
        for (GardenStoreItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public GardenStoreItem getItem(int id){
        return _itemMap.get(id);
    }

    public static GardenStoreConfig getInstance() {
        return GardenStoreConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static GardenStoreConfig instance = new GardenStoreConfig();
    }
}
