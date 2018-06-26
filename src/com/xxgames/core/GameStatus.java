package com.xxgames.core;

public enum GameStatus {
    // instances
    NORMAL(0), MAINTAIN(1), CLOSED(2);

    public int status;

    GameStatus(int status) {
        this.status = status;
    }

    public static void main(String[] args) {
        System.err.println(NORMAL.status + "-" + MAINTAIN.status);
    }
}