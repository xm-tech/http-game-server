package com.xxgames.core.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.xxgames.core.Loggers;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 专门用来处理游戏业务逻辑的数据源
 */
public final class GameLogicDataSource extends AbstractDataSource {

    public static final GameLogicDataSource instance = new GameLogicDataSource();

    @Override
    public void init() throws NamingException {
        Context ct = new InitialContext();
        dataSource = (DataSource) ct.lookup("java:comp/env/jdbc/druid");
        Loggers.gameLogicDataSource.info(getName() + " init succ");
    }

    @Override
    public void close() {
        DruidDataSource gs = (DruidDataSource) dataSource;
        Loggers.gameLogicDataSource.info("try to close GameLogicDataSource: " + gs);
        gs.close();
        dataSource = null;
        Loggers.gameLogicDataSource.info(getName() + " closed");
    }

    @Override
    public String getName() {
        return "GameLogicDataSource";
    }

}
