package com.xxgames.core;

import com.xxgames.core.thread.AsynService;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.TimeManager;
import com.xxgames.demo.handler.GameActs;
import com.xxgames.demo.rank.RankManager;
import com.xxgames.demo.task.DbSaveTask;
import com.xxgames.demo.task.LogTask;
import com.xxgames.demo.task.PvpRankRefreshTask;


final class GameInitializer {

    private GameInitializer() {
    }

    static void init() {
        try {
            GameActs.init();
            DataManager.init();
            AsynService.submit(DbSaveTask.single);
            AsynService.submit(PvpRankRefreshTask.single);
            AsynService.submit(LogTask.single);
            AsynService.submit(RankManager.getInstance());

            TimeManager.getInstance().init();
        } catch (Exception e) {
            Loggers.gameInitializer.error(e.getMessage(), e);
            System.exit(1);
        }
        Loggers.gameInitializer.debug("game init over ... ");
    }


}