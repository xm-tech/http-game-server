package com.ppgames.core.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.LoggerFactory;

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
        log = LoggerFactory.getLogger(GameLogicDataSource.class);
        Context ct = new InitialContext();
        dataSource = (DataSource) ct.lookup("java:comp/env/jdbc/druid");
        log.info(getName() + " init succ");
    }

    @Override
    public void close() {
        DruidDataSource gs = (DruidDataSource) dataSource;
        log.info("try to close GameLogicDataSource: " + gs);
        gs.close();
        dataSource = null;
        log.info(getName() + " closed");
    }

    @Override
    public String getName() {
        return "GameLogicDataSource";
    }

}
