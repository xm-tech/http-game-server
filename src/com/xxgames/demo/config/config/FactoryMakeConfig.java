package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.FactoryMakeItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class FactoryMakeConfig {
    private HashMap<Integer, FactoryMakeItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<FactoryMakeItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<FactoryMakeItem>>(){});
        _itemMap = new HashMap<Integer, FactoryMakeItem>();
        for (FactoryMakeItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public FactoryMakeItem getItem(int id){
        return _itemMap.get(id);
    }

    public static FactoryMakeConfig getInstance() {
        return FactoryMakeConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static FactoryMakeConfig instance = new FactoryMakeConfig();
    }
}
