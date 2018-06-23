package com.ppgames.core.async;

public class AsyncGameReq {
    private Runnable task;

    public AsyncGameReq(final Runnable task) {
        this.task = task;
    }
}
