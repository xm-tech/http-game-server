package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.FaceItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class FaceConfig {
    private HashMap<Integer, FaceItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<FaceItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<FaceItem>>(){});
        _itemMap = new HashMap<Integer, FaceItem>();
        for (FaceItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public FaceItem getItem(int id){
        return _itemMap.get(id);
    }

    public static FaceConfig getInstance() {
        return FaceConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static FaceConfig instance = new FaceConfig();
    }
}
