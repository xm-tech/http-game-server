package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.RobotItem;
import com.xxgames.util.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/18.
 */
public class RobotConfig {
    public static final long robot_id = -1L;

    private ArrayList<RobotItem> _itemList = null;

    public void InitConfig(String fname) throws Exception {
        _itemList = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<RobotItem>>(){});
    }

    public RobotItem getItem(int index){
        return _itemList.get(index);
    }

    public static RobotConfig getInstance() {
        return RobotConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static RobotConfig instance = new RobotConfig();
    }
}
