package com.xxgames.core.service;

public abstract class IService implements Runnable {

    @Override
    public void run() {
        exec();
    }

    protected abstract void exec();
}
