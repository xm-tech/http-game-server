package com.xxgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.demo.config.item.RankListSaleItem;
import com.xxgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PhonePadPC on 2017/7/14.
 */
public class RankListSaleConfig {
    private HashMap<Integer, RankListSaleItem> _rank_list_sale_map = null;
    public void InitConfig(String fname) throws Exception {
        ArrayList<RankListSaleItem> tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<RankListSaleItem>>(){});
        _rank_list_sale_map = new HashMap<Integer, RankListSaleItem>();
        for (RankListSaleItem config : tmplist) {
            _rank_list_sale_map.put(config.getId(), config);
        }
    }
    public RankListSaleItem getItem(int id){
        return _rank_list_sale_map.get(id);
    }
    public HashMap<Integer, RankListSaleItem> getMap(){
        return _rank_list_sale_map;
    }


    public static RankListSaleConfig getInstance() {
        return RankListSaleConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static RankListSaleConfig instance = new RankListSaleConfig();
    }
}
