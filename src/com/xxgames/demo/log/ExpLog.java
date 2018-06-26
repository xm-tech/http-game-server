package com.xxgames.demo.log;

import com.xxgames.util.TimeUtil;

public class ExpLog extends Log {

    public ExpLog(final long pid, final int num, final String source) {
        super(pid, -3, num, TimeUtil.nowInt(), source);
        tableName = "t_log_exp";
    }

}