package com.xxgames.core;

import com.xxgames.core.db.GameLogDataSource;
import com.xxgames.core.db.GameLogicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 统一定义系统使用的 sl4j 日志
public class Loggers {

    public static Logger gateWay = LoggerFactory.getLogger(GateWay.class);
    public static Logger gameContextListener = LoggerFactory.getLogger(GameContextListener.class);
    public static Logger gameInitializer = LoggerFactory.getLogger(GameInitializer.class);
    public static Logger gameLogicDataSource = LoggerFactory.getLogger(GameLogicDataSource.class);
    public static Logger gameLogDataSource = LoggerFactory.getLogger(GameLogDataSource.class);

}
