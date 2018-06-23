package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.EquipsCapacityExpand;
import com.ppgames.util.Data;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class EquipsCapacityExpandConfig {
    private ArrayList<EquipsCapacityExpand> _itemList = null;

    public void InitConfig(String fname) throws Exception {
        _itemList = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<EquipsCapacityExpand>>(){});
    }

    public EquipsCapacityExpand getItem(int index){
        return _itemList.get(index);
    }

    public int getCapacityMax() { return _itemList.size(); }

    public static EquipsCapacityExpandConfig getInstance() {
        return EquipsCapacityExpandConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static EquipsCapacityExpandConfig instance = new EquipsCapacityExpandConfig();
    }
}
