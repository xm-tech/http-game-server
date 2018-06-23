package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.DressRoomLevelItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/15.
 */
public class DressRoomLevelConfig {
    private HashMap<Integer, DressRoomLevelItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<DressRoomLevelItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<DressRoomLevelItem>>(){});
        _itemMap = new HashMap<Integer, DressRoomLevelItem>();
        for (DressRoomLevelItem item : tmplist) {
            _itemMap.put(item.getLevel(), item);
        }
    }

    public DressRoomLevelItem getItem(int id){
        return _itemMap.get(id);
    }

    public static DressRoomLevelConfig getInstance() {
        return DressRoomLevelConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static DressRoomLevelConfig instance = new DressRoomLevelConfig();
    }
}
