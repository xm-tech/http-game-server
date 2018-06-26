package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.LogisticsLevelItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class LogisticsLevelConfig {
    private HashMap<Integer, LogisticsLevelItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<LogisticsLevelItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<LogisticsLevelItem>>(){});
        _itemMap = new HashMap<Integer, LogisticsLevelItem>();
        for (LogisticsLevelItem item : tmplist) {
            _itemMap.put(item.getLevel(), item);
        }
    }

    public LogisticsLevelItem getItem(int id){
        return _itemMap.get(id);
    }

    public static LogisticsLevelConfig getInstance() {
        return LogisticsLevelConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static LogisticsLevelConfig instance = new LogisticsLevelConfig();
    }
}
