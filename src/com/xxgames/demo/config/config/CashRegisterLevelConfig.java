package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.CashRegisterLevelItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class CashRegisterLevelConfig {
    private HashMap<Integer, CashRegisterLevelItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<CashRegisterLevelItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<CashRegisterLevelItem>>(){});
        _itemMap = new HashMap<Integer, CashRegisterLevelItem>();
        for (CashRegisterLevelItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public CashRegisterLevelItem getItem(int id){
        return _itemMap.get(id);
    }

    public static CashRegisterLevelConfig getInstance() {
        return CashRegisterLevelConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static CashRegisterLevelConfig instance = new CashRegisterLevelConfig();
    }
}
