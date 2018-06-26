package com.xxgames.demo.task;

import com.xxgames.demo.cache.RankCache;
import com.xxgames.demo.config.config.SystemConfConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PvpRankRefreshTask implements Runnable {
    private static Logger log = LoggerFactory.getLogger(PvpRankRefreshTask.class);
    public static final PvpRankRefreshTask single = new PvpRankRefreshTask();
    public static volatile boolean run = true;

    long pvp_rank_refresh_period = 3 * 60 * 1000L;

    private PvpRankRefreshTask() {
        init();
    }

    public void init() {
        pvp_rank_refresh_period = SystemConfConfig.getInstance().getCfg().getPvp_rank_refresh_period();
        if (pvp_rank_refresh_period <= 0L) {
            pvp_rank_refresh_period = 3 * 60 * 1000L;
        }
        log.debug("pvp_rank_refresh_period=" + pvp_rank_refresh_period);
    }

    @Override
    public void run() {
        log.debug("PvpRankRefreshTask run ...");

        while (true) {
            if (!run) {
                // exit
                break;
            }

            try {
                Thread.sleep(pvp_rank_refresh_period);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                continue;
            }

            log.debug("PvpRankRefreshTask run");
            RankCache.refreshPvpRanks();
            log.debug(RankCache.getPvpranks().toJSONString());
        }
    }


}
