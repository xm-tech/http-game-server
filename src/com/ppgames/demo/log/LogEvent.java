package com.ppgames.demo.log;

import java.util.EventObject;

public class LogEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    public LogEvent(Log source) {
        super(source);
    }

    public Log getLog() {
        return (Log) getSource();
    }
}
