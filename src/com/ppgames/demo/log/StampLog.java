package com.ppgames.demo.log;

import com.ppgames.util.TimeUtil;

/**
 * Created by PhonePadPC on 2017/7/20.
 */
public class StampLog extends Log{
    public StampLog(final long pid, final int num, final String source) {
        super(pid, -9, num, TimeUtil.nowInt(), source);
        tableName = "t_log_stamp";
    }
}
