package com.ppgames.demo.log;

import com.ppgames.util.TimeUtil;

public class FaceFragLog extends Log {

    public FaceFragLog(final long pid, final int itemid, final int num, final String source) {
        super(pid, itemid, num, TimeUtil.nowInt(), source);
        tableName = "t_log_facefrag";
    }
}
