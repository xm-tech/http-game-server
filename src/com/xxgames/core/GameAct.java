package com.xxgames.core;

public abstract class GameAct {

    protected int msgId;

    public abstract void exec(GameReq req, GameResp resp);

    public int getMsgId() {
        return msgId;
    }

}
