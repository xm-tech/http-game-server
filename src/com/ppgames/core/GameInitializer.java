package com.ppgames.core;

import com.ppgames.core.thread.AsynService;
import com.ppgames.demo.DataManager;
import com.ppgames.demo.TimeManager;
import com.ppgames.demo.dao.AllDao;
import com.ppgames.demo.rank.RankManager;
import com.ppgames.demo.task.DbSaveTask;
import com.ppgames.demo.task.LogTask;
import com.ppgames.demo.task.PvpRankRefreshTask;
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