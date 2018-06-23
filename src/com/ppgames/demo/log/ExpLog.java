package com.ppgames.demo.log;

import com.ppgames.util.TimeUtil;

public class ExpLog extends Log {

    public ExpLog(final long pid, final int num, final String source) {
        super(pid, -3, num, TimeUtil.nowInt(), source);
        tableName = "t_log_exp";
    }

}