package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.OfficeItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class OfficeConfig {
    private HashMap<Integer, OfficeItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<OfficeItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<OfficeItem>>(){});
        _itemMap = new HashMap<Integer, OfficeItem>();
        for (OfficeItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public OfficeItem getItem(int id){
        return _itemMap.get(id);
    }

    public static OfficeConfig getInstance() {
        return OfficeConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static OfficeConfig instance = new OfficeConfig();
    }
}
