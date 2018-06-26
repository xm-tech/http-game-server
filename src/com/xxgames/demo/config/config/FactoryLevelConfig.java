package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.FactoryLevelItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class FactoryLevelConfig {
    private HashMap<Integer, FactoryLevelItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<FactoryLevelItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<FactoryLevelItem>>(){});
        _itemMap = new HashMap<Integer, FactoryLevelItem>();
        for (FactoryLevelItem item : tmplist) {
            _itemMap.put(item.getLevel(), item);
        }
    }

    public FactoryLevelItem getItem(int id){
        return _itemMap.get(id);
    }

    public static FactoryLevelConfig getInstance() {
        return FactoryLevelConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static FactoryLevelConfig instance = new FactoryLevelConfig();
    }
}
