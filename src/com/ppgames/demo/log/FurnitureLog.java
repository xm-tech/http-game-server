package com.ppgames.demo.log;

import com.ppgames.util.TimeUtil;

public class FurnitureLog extends Log {

    public FurnitureLog(final long pid, final int itemid, final int num, final String source) {
        super(pid, itemid, num, TimeUtil.nowInt(), source);
        tableName = "t_log_furniture";
    }

}