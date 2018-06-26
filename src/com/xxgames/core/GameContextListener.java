package com.xxgames.core;

import com.xxgames.core.db.GameLogDataSource;
import com.xxgames.core.db.GameLogicDataSource;
import com.xxgames.demo.task.DbSaveTask;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class GameContextListener implements ServletContextListener {

    public static GameStatus gameStatus;
    private static Logger log = LoggerFactory.getLogger(GameContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // block game req
        gameStatus = GameStatus.MAINTAIN;
        DbSaveTask.single.stop();
        gameStatus = GameStatus.CLOSED;

        GameLogicDataSource.instance.close();
        GameLogDataSource.instance.close();

        log.info("game context destroyed!");
        Enumeration drivers = DriverManager.getDrivers();
        while(drivers.hasMoreElements()){
            Driver driver = (Driver) drivers.nextElement();
            try{
                DriverManager.deregisterDriver(driver);
                log.info(String.format("deregistering jdbc driver:%s", driver));
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(String.format("deregistering jdbc driver: %s", driver));
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DOMConfigurator.configureAndWatch("config/log4j.xml");

        try {
            GameLogicDataSource.instance.init();
            GameLogDataSource.instance.init();
        } catch (NamingException e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }

        GameInitializer.init();
        gameStatus = GameStatus.NORMAL;
        log.info("game context init succ");
    }

}
