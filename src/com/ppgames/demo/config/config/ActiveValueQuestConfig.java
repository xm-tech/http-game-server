package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.QuestItems.ActiveValueQuestItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tony on 2017/7/17.
 */
public class ActiveValueQuestConfig {
    private HashMap<Integer, ActiveValueQuestItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<ActiveValueQuestItem>
            tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<ActiveValueQuestItem>>(){});
        _itemMap = new HashMap<Integer, ActiveValueQuestItem>();
        for (ActiveValueQuestItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public ActiveValueQuestItem getItem(int id){
        return _itemMap.get(id);
    }

    public static ActiveValueQuestConfig getInstance() {
        return ActiveValueQuestConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static ActiveValueQuestConfig instance = new ActiveValueQuestConfig();
    }
}
