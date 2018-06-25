package com.ppgames.core.thread;

import com.ppgames.demo.rank.RankManager;
import com.ppgames.demo.task.DbSaveTask;
import com.ppgames.demo.task.LogTask;
import com.ppgames.demo.task.PvpRankRefreshTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class AsynService {

    private static final ExecutorService dbsaveExecutor =
        Executors.newSingleThreadExecutor(new GameThreadFactory(DbSaveTask.class.getCanonicalName()));

    private static final ExecutorService pvpRankRefreshExecutor =
        Executors.newSingleThreadExecutor(new GameThreadFactory(PvpRankRefreshTask.class.getCanonicalName()));

    private static final ExecutorService logExecutor =
        Executors.newSingleThreadExecutor(new GameThreadFactory(LogTask.class.getCanonicalName()));

    private static final ExecutorService RankManagerExecutor =
        Executors.newSingleThreadExecutor(new GameThreadFactory(RankManager.class.getCanonicalName()));

    public static void submit(final Runnable task) {
        if (task instanceof DbSaveTask) {
            dbsaveExecutor.execute(task);
        } else if (task instanceof PvpRankRefreshTask) {
            pvpRankRefreshExecutor.execute(task);
        } else if (task instanceof LogTask) {
            logExecutor.execute(task);
        } else if (task instanceof RankManager) {
            RankManagerExecutor.execute(task);
        }
    }

    static <T> Future<T> submit(Callable<T> task) {
//        return dbsaveExecutor.submit(task);
        return null;
    }

}
