package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.SystemConfItem;
import com.ppgames.util.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/17.
 */
public class SystemConfConfig {
    public ArrayList<SystemConfItem> _itemList;
    public void InitConfig(String fname) throws Exception {
        _itemList = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<SystemConfItem>>(){});
    }

    public SystemConfItem getCfg() { return _itemList.get(0); }

    public static SystemConfConfig getInstance() {
        return SystemConfConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static SystemConfConfig instance = new SystemConfConfig();
    }
}
