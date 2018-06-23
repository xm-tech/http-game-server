package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.CheckInItem;
import com.ppgames.util.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class CheckInConfig {
    private ArrayList<CheckInItem>  _itemList = null;

    public void InitConfig(String fname) throws Exception {
        _itemList = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<CheckInItem>>(){});
    }

    public CheckInItem getItem(int id){
        return _itemList.get(id);
    }

    public static CheckInConfig getInstance() {
        return CheckInConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static CheckInConfig instance = new CheckInConfig();
    }
}
