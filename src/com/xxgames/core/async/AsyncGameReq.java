package com.xxgames.core.async;

public class AsyncGameReq {
    private Runnable task;

    public AsyncGameReq(final Runnable task) {
        this.task = task;
    }
}
