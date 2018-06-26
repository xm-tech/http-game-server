package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.ShelfItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class ShelfConfig {
    private HashMap<Integer, ShelfItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<ShelfItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<ShelfItem>>(){});
        _itemMap = new HashMap<Integer, ShelfItem>();
        for (ShelfItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public ShelfItem getItem(int id){
        return _itemMap.get(id);
    }

    public static ShelfConfig getInstance() {
        return ShelfConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static ShelfConfig instance = new ShelfConfig();
    }
}
