package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.QuestItems.SaleQuestItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JMX on 2017/7/25.
 */
public class SaleQuestConfig {
    private HashMap<Integer, SaleQuestItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<SaleQuestItem>
            tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<SaleQuestItem>>(){});
        _itemMap = new HashMap<Integer, SaleQuestItem>();
        for (SaleQuestItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public SaleQuestItem getItem(int id){
        return _itemMap.get(id);
    }

    public static SaleQuestConfig getInstance() {
        return SaleQuestConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static SaleQuestConfig instance = new SaleQuestConfig();
    }
}
