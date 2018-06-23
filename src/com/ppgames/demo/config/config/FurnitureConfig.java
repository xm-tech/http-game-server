package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.FurnitureItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class FurnitureConfig {
    private HashMap<Integer, FurnitureItem> _furnitureMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<FurnitureItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<FurnitureItem>>(){});
        _furnitureMap = new HashMap<Integer, FurnitureItem>();
        for (FurnitureItem furniture : tmplist) {
            _furnitureMap.put(furniture.getId(), furniture);
        }
    }

    public FurnitureItem getItem(int furnitureId){
        return _furnitureMap.get(furnitureId);
    }

    public static FurnitureConfig getInstance() {
        return FurnitureConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static FurnitureConfig instance = new FurnitureConfig();
    }

}
