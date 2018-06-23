package com.ppgames.demo.log;

import com.ppgames.util.TimeUtil;

public final class DrawLog extends Log {

    public DrawLog(final long pid, final int itemid, final int num, final String source) {
        super(pid, itemid, num, TimeUtil.nowInt(), source);
        tableName = "t_log_draw";
    }

}
