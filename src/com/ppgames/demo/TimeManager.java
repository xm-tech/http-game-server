package com.ppgames.demo;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.demo.activity.ActivityGroup;
import com.ppgames.demo.activity.ActivityManager;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.EquipConfig;
import com.ppgames.demo.config.item.EquipItem;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.QuestList;
import com.ppgames.demo.model.quest.QuestManager;
import com.ppgames.demo.utils.Const;
import com.ppgames.util.EDressStyle;
import com.ppgames.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by PhonePadPC on 2017/7/18.
 */
public class TimeManager {
    private TimeManager(){

    }
    public static TimeManager getInstance() {
        return TimeManager.SingletonHolder.instance;
    }
    static class SingletonHolder {
        static TimeManager instance = new TimeManager();
    }
    Runnable playerRubbishCreaterRunnable = new Runnable() {
        public void run() {
            Map<Long, Player> all_palyer_map = Cache.players;
            List<Player> all_player_list = new ArrayList<Player>(all_palyer_map.values());
            for (Player player : all_player_list){
                //randomPlayerWindosStyle(player);
                createPlayerRubbish(player);
            }
        }
    };
    Runnable playerWindosStyleRunnable = new Runnable() {
        public void run() {
            Map<Long, Player> all_palyer_map = Cache.players;
            List<Player> all_player_list = new ArrayList<Player>(all_palyer_map.values());
            for (Player player : all_player_list){
                randomPlayerWindosStyle(player);
            }

        }
    };
    Runnable playerAutoSellRunnable = new Runnable() {
        public void run() {
            Map<Long, Player> all_palyer_map = Cache.players;
            List<Player> all_player_list = new ArrayList<Player>(all_palyer_map.values());
            for (Player player : all_player_list){
                playerAutoSell(player);
            }
        }
    };
    Runnable addNormalActivityRunnable = new Runnable() {
        public void run() {
//            Map<Long, Player> all_palyer_map = Cache.players;
//            List<Player> all_player_list = new ArrayList<Player>(all_palyer_map.values());
//            for (Player player : all_player_list){
//                playerAutoSell(player);
//            }
            checkActivity();
        }
    };
    public void init (){
        initPlayerWindosStyle();
        initPlayerRubbishCreater();
        initPlayerAutoSell();
        initAddNormalActivity();
    }
    private void randomPlayerWindosStyle(Player player){
        JSONArray windows = player.getWindows();
        for (int i = 0 ; i < windows.size(); i ++){
            JSONObject win = windows.getJSONObject(i);
            int old_style = win.getInteger("styleid");
            int new_style = EDressStyle.randomOneStyle();
            int times = 0 ;
            while (new_style == old_style){
                new_style = EDressStyle.randomOneStyle();
                if (times++ > 10) break;
            }
            win.put("styleid", new_style);
            win.put("score",getWindowsScore(win));
        }
    }
    private int getWindowsScore(JSONObject window){
        int score = 0 ;
        int styleid = window.getInteger("styleid");
        JSONArray equips = window.getJSONArray("equips");
        for (int i = 0 ; i < equips.size(); i ++){
            int equipid = equips.getIntValue(i);
            EquipItem equipItem = EquipConfig.getInstance().getItem(equipid);
            int equipStyle = equipItem.getStyleid();
            score += equipItem.getFootfall().get(styleid == equipStyle ? 1 : 0);
        }
        return score ;
    }
    public void initPlayerWindosStyle (){
        //每天早上10点给每个玩家随机橱窗风格
        int delay_time = 10;
        int now_time = TimeUtil.nowInt();
        int ten_clock = TimeUtil.startOfDay() + 10*60*60;
        if (now_time > ten_clock ){
            delay_time = ten_clock + Const.DAY_SECOND - now_time;
        }
        else {
            delay_time = ten_clock - now_time;
        }
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(playerWindosStyleRunnable, delay_time, Const.DAY_SECOND ,TimeUnit.SECONDS);
    }

    public void initPlayerRubbishCreater(){
        //30分钟给玩家生成一个rubbish
        int period_time = 30 * 60 ;
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(playerRubbishCreaterRunnable, 0, period_time ,TimeUnit.SECONDS);
    }

    public void createPlayerRubbish(Player player){
        if (player.getShops().getRubbishs() <= 10){
            player.getShops().addRubbishs();
        }
    }

    public void initPlayerAutoSell(){
        int period_time = 20  ;
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(playerAutoSellRunnable, 0, period_time ,TimeUnit.SECONDS);
    }
    public void playerAutoSell(Player player){
        long pid =player.getId();
        if (DataManager.players_goods_places.containsKey(pid)) {
            if (DataManager.players_goods_places.get(pid).size() != 0){
                player.AutoSellGoods();
            }
        }
    }

    public void initAddNormalActivity(){
        int period_time = 1  ;
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(addNormalActivityRunnable, 0, period_time ,TimeUnit.SECONDS);
    }
    private void checkActivity(){
        Map<Integer, ActivityGroup> openGroupMap = new HashMap<>();
        int nowTime = TimeUtil.nowInt();
        Map<Integer, ActivityGroup> activityGroupMap = ActivityManager.getInstance().getNormalGroupMap();
        for (Map.Entry<Integer, ActivityGroup> e: activityGroupMap.entrySet()) {
            ActivityGroup activityGroup = e.getValue();
            if (activityGroup.getStartTime() == nowTime) {
                openGroupMap.put(activityGroup.getGroupId(),activityGroup);
            }
        }
        if (openGroupMap.size() > 0){
            Map<Long, Player> all_palyer_map = Cache.players;
            List<Player> all_player_list = new ArrayList<Player>(all_palyer_map.values());
            for (Player player : all_player_list){
                QuestList list = QuestManager.getInstance().getQuestList(player.getId());
                if (list != null){
                    list.createActivityQuest(openGroupMap);
                }

            }
        }

    }
}
