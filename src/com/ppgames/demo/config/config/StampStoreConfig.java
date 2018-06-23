package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.StampStoreItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PhonePadPC on 2017/7/20.
 */
public class StampStoreConfig {
    private HashMap<Integer, StampStoreItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<StampStoreItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<StampStoreItem>>(){});
        _itemMap = new HashMap<Integer, StampStoreItem>();
        for (StampStoreItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public StampStoreItem getItem(int id){
        return _itemMap.get(id);
    }

    public static StampStoreConfig getInstance() {
        return StampStoreConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static StampStoreConfig instance = new StampStoreConfig();
    }
}
