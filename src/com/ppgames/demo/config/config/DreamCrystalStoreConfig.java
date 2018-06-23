package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.DreamCrystalStoreConfigItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PhonePadPC on 2017/7/14.
 */
public class DreamCrystalStoreConfig {
    private HashMap<Integer, DreamCrystalStoreConfigItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<DreamCrystalStoreConfigItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<DreamCrystalStoreConfigItem>>(){});
        _itemMap = new HashMap<Integer, DreamCrystalStoreConfigItem>();
        for (DreamCrystalStoreConfigItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public DreamCrystalStoreConfigItem getItem(int id){
        return _itemMap.get(id);
    }

    public static DreamCrystalStoreConfig getInstance() {
        return DreamCrystalStoreConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static DreamCrystalStoreConfig instance = new DreamCrystalStoreConfig();
    }
}
