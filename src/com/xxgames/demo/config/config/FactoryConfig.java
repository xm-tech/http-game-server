package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.FactoryItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class FactoryConfig {
    private HashMap<Integer, FactoryItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<FactoryItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<FactoryItem>>(){});
        _itemMap = new HashMap<Integer, FactoryItem>();
        for (FactoryItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public FactoryItem getItem(int id){
        return _itemMap.get(id);
    }

    public static FactoryConfig getInstance() {
        return FactoryConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static FactoryConfig instance = new FactoryConfig();
    }
}
