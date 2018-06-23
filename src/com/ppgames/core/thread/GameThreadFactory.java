package com.ppgames.core.thread;

import java.util.concurrent.ThreadFactory;

public class GameThreadFactory implements ThreadFactory {
    private final String name;
    private int i;

    public GameThreadFactory(final String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(name + "-Thread-" + ++i);
        t.setDaemon(true);
        return t;
    }
}
