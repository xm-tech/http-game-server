package com.ppgames.demo.tag;

import java.util.Map;

/**
 * Created by PhonePadPC on 2017/7/14.
 */
public class TimeCount {
    public TimeCount (){
        this.count = 0 ;
        this.time = 0;
    }
    public TimeCount(Map m) {
        this.time = Integer.parseInt(m.get("time").toString());
        this.count = Long.parseLong(m.get("count").toString());
    }
    public TimeCount(int time ,long count ){
        this.time = time ;
        this.count = count ;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    private int time ;
    private long count ;
}
