package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.ShelfLevel;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class ShelLevelConfig {
    private HashMap<Integer, ShelfLevel> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<ShelfLevel> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<ShelfLevel>>(){});
        _itemMap = new HashMap<Integer, ShelfLevel>();
        for (ShelfLevel item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public ShelfLevel getItem(int id){
        return _itemMap.get(id);
    }

    public static ShelLevelConfig getInstance() {
        return ShelLevelConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static ShelLevelConfig instance = new ShelLevelConfig();
    }
}
