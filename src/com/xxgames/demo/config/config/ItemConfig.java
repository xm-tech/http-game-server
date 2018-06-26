package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.Item;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class ItemConfig {
    private HashMap<Integer, Item> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<Item> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<Item>>(){});
        _itemMap = new HashMap<Integer, Item>();
        for (Item item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public Item getItem(int furnitureId){
        return _itemMap.get(furnitureId);
    }

    public static ItemConfig getInstance() {
        return ItemConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static ItemConfig instance = new ItemConfig();
    }
}
