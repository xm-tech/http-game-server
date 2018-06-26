package com.xxgames.core.exception;

import com.xxgames.core.ErrCode;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

    private int errCode;

    // 只有1种构造函数，强制传递仨参数
    public BusinessException(int errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    private static void test() {
        try {
            throw new BusinessException(ErrCode.DB_EXEC_ERR, "aaa", null);
        } catch (BusinessException e) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        test();
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("GameException, errCode[").append(errCode).append("]");
        b.append(",errMsg[").append(this.getMessage()).append("],");
        b.append(System.lineSeparator());
        b.append("stackTrace[");
        b.append(System.lineSeparator());
        StackTraceElement[] stackTrace = this.getStackTrace();
        for (StackTraceElement trace : stackTrace) {
            b.append(trace).append(System.lineSeparator());
        }
        b.append("]");

        return b.toString();
    }
}
