package com.xxgames.demo.task;

import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.log.Log;
import com.xxgames.demo.log.LogQuene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class LogTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(LogTask.class);
    public static final LogTask single = new LogTask();
    public static volatile boolean run = true;
    public static volatile boolean saving;

    public static AtomicLong count = new AtomicLong(0L);

    long db_write_period = 100L;

    private LogTask() {
        init();
    }

    public void init() {
        db_write_period = SystemConfConfig.getInstance().getCfg().getDb_log_write_period();
        if (db_write_period <= 0L) {
            db_write_period = 100L;
        }
        log.debug("db_log_write_period=" + db_write_period);
    }

    @Override
    public void run() {
        while (run) {
            sleep(db_write_period);
            if (saving) {
                continue;
            }

            // save one by one
            Log log = LogQuene.poll();
            if (log != null) {
                saving = true;
                log.save();
                saving = false;
                count.incrementAndGet();
            }
        }
    }

    private void sleep(long millSeconds) {
        try {
            Thread.sleep(millSeconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            for (int i = 0; i < 1000; i++) {
                // 模拟延迟
            }
        }
    }
}
