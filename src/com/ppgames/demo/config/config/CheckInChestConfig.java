package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.CheckInChestItem;
import com.ppgames.util.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class CheckInChestConfig{
    private ArrayList<CheckInChestItem> _itemList = null;

    public void InitConfig(String fname) throws Exception {
        _itemList = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<CheckInChestItem>>(){});
    }

    public CheckInChestItem getItem(int id){
        return _itemList.get(id);
    }

    public static CheckInChestConfig getInstance() {
        return CheckInChestConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static CheckInChestConfig instance = new CheckInChestConfig();
    }
}
