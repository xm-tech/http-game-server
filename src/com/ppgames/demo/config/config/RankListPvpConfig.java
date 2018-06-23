package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.RankListPvpItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PhonePadPC on 2017/7/14.
 */
public class RankListPvpConfig {
    private HashMap<Integer, RankListPvpItem> _rank_list_pvp_map = null;
    public void InitConfig(String fname) throws Exception {
        ArrayList<RankListPvpItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<RankListPvpItem>>(){});
        _rank_list_pvp_map = new HashMap<Integer, RankListPvpItem>();
        for (RankListPvpItem config : tmplist) {
            _rank_list_pvp_map.put(config.getId(), config);
        }
    }
    public RankListPvpItem getItem(int id){
        return _rank_list_pvp_map.get(id);
    }
    public HashMap<Integer, RankListPvpItem> getMap(){
        return _rank_list_pvp_map;
    }


    public static RankListPvpConfig getInstance() {
        return RankListPvpConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static RankListPvpConfig instance = new RankListPvpConfig();
    }
}
