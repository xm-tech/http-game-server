package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.QuestItems.MainLineQuestItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tony on 2017/7/17.
 */
public class MainLineQuestConfig {
    private HashMap<Integer, MainLineQuestItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<MainLineQuestItem>
            tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<MainLineQuestItem>>(){});
        _itemMap = new HashMap<Integer, MainLineQuestItem>();
        for (MainLineQuestItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public MainLineQuestItem getItem(int id){
        return _itemMap.get(id);
    }

    public static MainLineQuestConfig getInstance() {
        return MainLineQuestConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static MainLineQuestConfig instance = new MainLineQuestConfig();
    }

}
