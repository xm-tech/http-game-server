package com.ppgames.core.exception;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class DbExecException extends SQLException {

    public DbExecException() {
        super();
    }

    public DbExecException(String reason) {
        super(reason);
    }

}
