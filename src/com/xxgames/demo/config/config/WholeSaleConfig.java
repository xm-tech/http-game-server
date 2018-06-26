package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.WholeSaleItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class WholeSaleConfig {
    private HashMap<Integer, WholeSaleItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<WholeSaleItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<WholeSaleItem>>(){});
        _itemMap = new HashMap<Integer, WholeSaleItem>();
        for (WholeSaleItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public WholeSaleItem getItem(int id){
        return _itemMap.get(id);
    }

    public static WholeSaleConfig getInstance() {
        return WholeSaleConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static WholeSaleConfig instance = new WholeSaleConfig();
    }
}
