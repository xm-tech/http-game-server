package com.xxgames.demo.log;

import com.xxgames.util.TimeUtil;

public class AppleLog extends Log {

    public AppleLog(final long pid, final int num, final String source) {
        super(pid, -4, num, TimeUtil.nowInt(), source);
        tableName = "t_log_apple";
    }

}