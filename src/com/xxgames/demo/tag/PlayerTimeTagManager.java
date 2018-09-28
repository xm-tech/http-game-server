package com.xxgames.demo.tag;

import com.xxgames.demo.dao.TagDao;
import com.xxgames.demo.model.Player;
import com.xxgames.util.TimeUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PhonePadPC on 2017/7/14.
 */
public class PlayerTimeTagManager {
    private Map<Integer, ArrayList<Integer>> _time_tag_map = new HashMap<Integer, ArrayList<Integer>>();

    private Player _player;
    public void setPlayer(Player player){
        _player = player;
    }
    public void load(int tag_id ,int time){
        addTagImpl(tag_id,time);
    }
    public void addTag(int tag_id){
        addTagImpl(tag_id,0);
    }

    private synchronized void addTagImpl(int tag_id ,int time ){
        if ( !_time_tag_map.containsKey(tag_id)){
            _time_tag_map.put(tag_id,new ArrayList<Integer>());
        }
        ArrayList<Integer> times = _time_tag_map.get(tag_id);
        int now_time = time > 0 ? time :TimeUtil.nowInt();
        times.add(now_time);

        if (time == 0){
            insertDB(tag_id,now_time);
        }
    }
    private void insertDB(int tag_id , int now_time ){
        try {
            TagDao.getInstance().insertTimeTag(_player.getId(),tag_id,now_time);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }
    public synchronized int getTagCount(int tag_id,int from_time,int end_time){
        ArrayList<Integer> times = _time_tag_map.get(tag_id);
        int ret = 0 ;
        if (times != null){
            if (times.size() > 0){
                for (int i = times.size()-1 ; i >= 0 ; i --){
                    if (times.get(i) >= from_time && times.get(i) <= end_time){
                        ret++ ;
                    }
                    else break;
                }
            }
        }
        return ret;
    }
    public synchronized int getTagCount(int tag_id,int from_time){
        ArrayList<Integer> times = _time_tag_map.get(tag_id);
        int ret = 0 ;
        if (times != null){
            if (times.size() > 0){
                for (int i = times.size()-1 ; i >= 0 ; i --){
                    if (times.get(i) >= from_time){
                        ret++ ;
                    }
                    else break;
                }
            }
        }
        return ret;
    }
}
