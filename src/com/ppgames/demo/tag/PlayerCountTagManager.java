package com.ppgames.demo.tag;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.demo.model.Player;
import com.ppgames.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PhonePadPC on 2017/7/14.
 */
public class PlayerCountTagManager {
    private Map<Integer, ArrayList<TimeCount>> _time_count_map = new HashMap<Integer, ArrayList<TimeCount>>();

    private Player _player;
    public void setPlayer(Player player){
        _player = player;
    }

    public void load(int tag_id , long count , int time){
        addTagImpl(tag_id,count ,time);
    }
    public void addTag(int tag_id , long count ){
        addTagImpl(tag_id,count ,0);
    }

    ArrayList<JSONObject> dbPool = new ArrayList<>();

    public synchronized void addTagImpl(int tag_id , long count , int time ){
        if ( !_time_count_map.containsKey(tag_id)){
            _time_count_map.put(tag_id,new ArrayList<TimeCount>());
        }
        ArrayList<TimeCount> times = _time_count_map.get(tag_id);
        int now_time = time > 0 ? time : TimeUtil.nowInt();
        times.add(new TimeCount(now_time ,count));

        if (time == 0){
            pushToDBPool(tag_id,count ,now_time);
        }
    }
    public synchronized long getTagCount(int tag_id,int from_time,int end_time){
        ArrayList<TimeCount> times = _time_count_map.get(tag_id);
        int ret = 0 ;
        if (times != null){
            if (times.size() > 0){
                for (int i = times.size()-1 ; i >= 0 ; i --){
                    if (times.get(i).getTime() >= from_time && times.get(i).getTime() <= end_time){
                        ret += times.get(i).getCount() ;
                    }
                    else break;
                }
            }
        }
        return ret;
    }
    public synchronized long getTagCount(int tag_id,int from_time){
        ArrayList<TimeCount> times = _time_count_map.get(tag_id);
        int ret = 0 ;
        if (times != null){
            if (times.size() > 0){
                for (int i = times.size()-1 ; i >= 0 ; i --){
                    if (times.get(i).getTime() >= from_time){
                        ret += times.get(i).getCount() ;
                    }
                    else break;
                }
            }
        }
        return ret;
    }
    private  void pushToDBPool(int tagId,long count,int nowTime){
        JSONObject object = new JSONObject();
        object.put("tagId",tagId);
        object.put("count",count);
        object.put("time",nowTime);
        object.put("pid",_player.getId());
        synchronized(dbPool){
            dbPool.add(object);
        }
    }
    public String getDbPoolStr(){
        String str = "";
        synchronized(dbPool){
            for (JSONObject object : dbPool){
                str += "(" +object.getLong("pid") + ","
                    + object.getInteger("tagId")+","
                    + object.getLong("count")+","
                    + object.getInteger("time")
                    + "),";
            }
            dbPool.clear();
        }
        return str;
    }

}
