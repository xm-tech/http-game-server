package com.xxgames.core;

public class EventBuffer extends MsgBuffer<Runnable> implements EventReader {
    @Override
    public void add(Runnable r) {
        super.add(r);
    }
}
