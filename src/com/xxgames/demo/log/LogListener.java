package com.xxgames.demo.log;

import java.util.EventListener;

public class LogListener implements EventListener {

    public void onHappen(LogEvent e) {
        LogQuene.add(e.getLog());
    }
}
