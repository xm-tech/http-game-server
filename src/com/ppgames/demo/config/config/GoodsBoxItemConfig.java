package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.GoodsBoxItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PhonePadPC on 2017/7/17.
 */
public class GoodsBoxItemConfig {

    private HashMap<Integer, GoodsBoxItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
//        ArrayList<GoodsBoxItem> tmplist = new ArrayList<GoodsBoxItem>();
//        tmplist = Data.getDataArrayGeneric(fname,tmplist);
        ArrayList<GoodsBoxItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<GoodsBoxItem>>(){});
        _itemMap = new HashMap<Integer, GoodsBoxItem>();
        for (GoodsBoxItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }
    public GoodsBoxItem getItem(int id ){
        return _itemMap.get(id);
    }

    public static GoodsBoxItemConfig getInstance() {
        return GoodsBoxItemConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static GoodsBoxItemConfig instance = new GoodsBoxItemConfig();
    }
}
