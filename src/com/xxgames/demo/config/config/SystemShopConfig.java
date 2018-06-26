package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.SystemShopItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class SystemShopConfig {
    private HashMap<Integer, SystemShopItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<SystemShopItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<SystemShopItem>>(){});
        _itemMap = new HashMap<Integer, SystemShopItem>();
        for (SystemShopItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public SystemShopItem getItem(int id){
        return _itemMap.get(id);
    }

    public static SystemShopConfig getInstance() {
        return SystemShopConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static SystemShopConfig instance = new SystemShopConfig();
    }
}
