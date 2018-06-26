package com.xxgames.core.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 专门用来处理游戏日志的数据源
 */
public final class GameLogDataSource extends AbstractDataSource {

    public static final GameLogDataSource instance = new GameLogDataSource();

    @Override
    public void init() throws NamingException {
        log = LoggerFactory.getLogger(GameLogDataSource.class);
        Context ct = new InitialContext();
        dataSource = (DataSource) ct.lookup("java:comp/env/jdbc/druid_log");
        log.info(getName() + " init succ");
    }

    @Override
    public void close() {
        DruidDataSource gs = (DruidDataSource) dataSource;
        log.info("try to close GameLogDataSource: " + gs);
        gs.close();
        dataSource = null;
        log.info(getName() + " closed");
    }

    @Override
    public String getName() {
        return "GameLogDataSource";
    }

}
