package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.WindowItem;
import com.ppgames.util.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class WindowConfig {
    private ArrayList<WindowItem> _itemList = null;

    public void InitConfig(String fname) throws Exception {
        _itemList = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<WindowItem>>(){});
    }

    public WindowItem getItem(int index){
        return _itemList.get(index);
    }

    public static WindowConfig getInstance() {
        return WindowConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static WindowConfig instance = new WindowConfig();
    }
}
