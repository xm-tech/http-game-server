package com.xxgames.demo.log;

import com.xxgames.util.TimeUtil;

public class FaceFragLog extends Log {

    public FaceFragLog(final long pid, final int itemid, final int num, final String source) {
        super(pid, itemid, num, TimeUtil.nowInt(), source);
        tableName = "t_log_facefrag";
    }
}
