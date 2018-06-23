package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.ShopExpandItem;
import com.ppgames.util.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class ShopExpandConfig {
    private ArrayList<ShopExpandItem> _itemList = null;

    public void InitConfig(String fname) throws Exception {
        _itemList = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<ShopExpandItem>>(){});
    }

    public ShopExpandItem getItem(int level){
        return _itemList.get(level);
    }
    public int getSize() { return _itemList.size();}
    public static ShopExpandConfig getInstance() {
        return ShopExpandConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static ShopExpandConfig instance = new ShopExpandConfig();
    }
}
