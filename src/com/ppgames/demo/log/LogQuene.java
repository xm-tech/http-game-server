package com.ppgames.demo.log;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LogQuene {
    public static final ConcurrentLinkedQueue<Log> quene = new ConcurrentLinkedQueue<>();

    public static synchronized void add(Log log) {
        quene.add(log);
    }

    public static synchronized Log poll() {
        return quene.poll();
    }
}
