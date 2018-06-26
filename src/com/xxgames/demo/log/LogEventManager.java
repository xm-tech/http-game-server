package com.xxgames.demo.log;

public final class LogEventManager {

    public static final LogEventManager instance = new LogEventManager(new LogListener());

    public LogListener l;

    public LogEventManager(final LogListener l) {
        this.l = l;
    }

    // 产生 log 事件
    public void fireLogEvent(final Log log) {
        notifyListener(new LogEvent(log));
    }

    public void notifyListener(LogEvent e) {
        l.onHappen(e);
    }
}
