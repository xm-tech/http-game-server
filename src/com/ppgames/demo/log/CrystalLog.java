package com.ppgames.demo.log;

import com.ppgames.util.TimeUtil;

public class CrystalLog extends Log {

    public CrystalLog(final long pid, final int num, final String source) {
        super(pid, -5, num, TimeUtil.nowInt(), source);
        tableName = "t_log_crystal";
    }

}