package com.ppgames.core;

public interface EventReader extends MsgReader<Runnable> {
    @Override
    Runnable get(int index);
}
