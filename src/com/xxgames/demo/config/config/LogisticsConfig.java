package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.LogisticsItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class LogisticsConfig {
    private HashMap<Integer, LogisticsItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<LogisticsItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<LogisticsItem>>(){});
        _itemMap = new HashMap<Integer, LogisticsItem>();
        for (LogisticsItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public LogisticsItem getItem(int id){
        return _itemMap.get(id);
    }

    public static LogisticsConfig getInstance() {
        return LogisticsConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static LogisticsConfig instance = new LogisticsConfig();
    }
}
