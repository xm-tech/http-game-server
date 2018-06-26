package com.xxgames.core;

import java.util.ArrayList;

public class MsgBuffer<T> implements MsgReader<T> {
    private ArrayList<T> events = new ArrayList<>();

    @Override
    public int size() {
        return events.size();
    }

    @Override
    public T get(int index) {
        return events.get(index);
    }

    public void add(T r) {
        events.add(r);
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

    public void clear() {
        events.clear();
    }
}
