package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.QuestItems.CustomerQuestItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tony on 2017/7/17.
 */
public class CustomerQuestConfig {
    private HashMap<Integer, CustomerQuestItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<CustomerQuestItem>
            tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<CustomerQuestItem>>(){});
        _itemMap = new HashMap<Integer, CustomerQuestItem>();
        for (CustomerQuestItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public CustomerQuestItem getItem(int id){
        return _itemMap.get(id);
    }

    public static CustomerQuestConfig getInstance() {
        return CustomerQuestConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static CustomerQuestConfig instance = new CustomerQuestConfig();
    }


}
