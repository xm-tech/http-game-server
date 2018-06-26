package com.xxgames.core;

import com.xxgames.core.thread.AsynService;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.TimeManager;
import com.xxgames.demo.dao.AllDao;
import com.xxgames.demo.handler.GameActs;
import com.xxgames.demo.rank.RankManager;
import com.xxgames.demo.task.DbSaveTask;
import com.xxgames.demo.task.LogTask;
import com.xxgames.demo.task.PvpRankRefreshTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


final class GameInitializer {
    private static Logger log = LoggerFactory.getLogger(GameInitializer.class);

    private GameInitializer() {
    }

    static void init() {
        try {
            GameActs.init();
            AllDao.init();
            DataManager.init();
            AsynService.submit(DbSaveTask.single);
            AsynService.submit(PvpRankRefreshTask.single);
            AsynService.submit(LogTask.single);
            AsynService.submit(RankManager.getInstance());

            TimeManager.getInstance().init();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }
        log.debug("game init over ... ");
    }


}