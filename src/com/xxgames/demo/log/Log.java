package com.xxgames.demo.log;

import com.xxgames.core.db.GameLogDataSource;

import java.sql.SQLException;

public abstract class Log {
    protected String tableName;

    protected long pid;
    protected int itemid;
    protected int num;
    protected int time;
    protected String source;

    protected Log(final long pid, final int itemid, final int num, final int time, final String source) {
        this.pid = pid;
        this.itemid = itemid;
        this.num = num;
        this.time = time;
        this.source = source;
    }

    public Object[] sqlParam() {
        return new Object[] { pid, itemid, num, time, source };
    }

    public boolean save() {
        String sql = "insert into " + tableName + "(`pid`,`itemid`,`num`,`time`,`source`) values (?,?,?,?,?)";
        try {
            GameLogDataSource.instance.insertAndReturnKey(sql, sqlParam());
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
