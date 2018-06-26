package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.GardenItem;
import com.xxgames.util.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/17.
 */
public class GardenConfig {
    public static final int FreeBuy = 0;
    public static final int BuyOne = 1;
    public static final int BuyTen = 2;

    public static final int SevenStarSeedID = 3;

    private ArrayList<GardenItem> _itemList = null;

    public void InitConfig(String fname) throws Exception {
        _itemList = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<GardenItem>>(){});
    }

    public GardenItem getItem(int level){
        return _itemList.get(level);
    }

    public static GardenConfig getInstance() {
        return GardenConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static GardenConfig instance = new GardenConfig();
    }
}
