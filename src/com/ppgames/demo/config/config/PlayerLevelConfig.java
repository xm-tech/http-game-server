package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.PlayerLevelItem;
import com.ppgames.util.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class PlayerLevelConfig {
    private ArrayList<PlayerLevelItem> _itemList = null;

    public void InitConfig(String fname) throws Exception {
        _itemList = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<PlayerLevelItem>>(){});
    }

    public PlayerLevelItem getItem(int index){
        return _itemList.get(index-1);
    }

    public static PlayerLevelConfig getInstance() {
        return PlayerLevelConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static PlayerLevelConfig instance = new PlayerLevelConfig();
    }
}
