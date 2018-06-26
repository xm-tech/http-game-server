package com.xxgames.demo.log;

import com.xxgames.util.TimeUtil;

public class CrystalLog extends Log {

    public CrystalLog(final long pid, final int num, final String source) {
        super(pid, -5, num, TimeUtil.nowInt(), source);
        tableName = "t_log_crystal";
    }

}