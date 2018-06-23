package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.EquipItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/13.
 */
public class EquipConfig {
    private HashMap<Integer, EquipItem> _equipMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<EquipItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<EquipItem>>(){});
        _equipMap = new HashMap<Integer, EquipItem>();
        for (EquipItem equip : tmplist) {
            _equipMap.put(equip.getId(), equip);
        }
    }

    public void InitMapConfig(String fname) throws Exception{
        _equipMap = JSON.parseObject(Data.getFileData(fname), new TypeReference<HashMap<Integer, EquipItem>>(){});
    }

    public EquipItem getItem(int equipid) {
        return _equipMap.get(equipid);
    }
    public Set<Integer> getAllEquipIds(){
        return _equipMap.keySet();
    }

    public static EquipConfig getInstance() {
        return SingletonHolder.instance;
    }
    static class SingletonHolder {
        static EquipConfig instance = new EquipConfig();
    }
}
