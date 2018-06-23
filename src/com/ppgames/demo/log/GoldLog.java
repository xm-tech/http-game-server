package com.ppgames.demo.log;

import com.ppgames.util.TimeUtil;

public class GoldLog extends Log {

    public GoldLog(final long pid, final int num, final String source) {
        super(pid, -2, num, TimeUtil.nowInt(), source);
        tableName = "t_log_gold";
    }

}