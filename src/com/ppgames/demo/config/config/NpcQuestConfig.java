package com.ppgames.demo.config.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ppgames.demo.config.item.QuestItems.NpcQuestItem;
import com.ppgames.util.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tony on 2017/7/17.
 */
public class NpcQuestConfig {
    private HashMap<Integer, NpcQuestItem> _itemMap = null;

    public void InitConfig(String fname) throws Exception {
        ArrayList<NpcQuestItem>
            tmplist = JSON.parseObject(Data.getFileData(fname), new TypeReference<ArrayList<NpcQuestItem>>(){});
        _itemMap = new HashMap<Integer, NpcQuestItem>();
        for (NpcQuestItem item : tmplist) {
            _itemMap.put(item.getId(), item);
        }
    }

    public int getItemSize(){
        return _itemMap.size();
    }


    public NpcQuestItem getItem(int id){
        return _itemMap.get(id);
    }

    public static NpcQuestConfig getInstance() {
        return NpcQuestConfig.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static NpcQuestConfig instance = new NpcQuestConfig();
    }


}
