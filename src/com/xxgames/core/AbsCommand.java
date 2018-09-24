package com.xxgames.core;

public abstract class AbsCommand<Req, Resp> implements Command {
    Req req;
    Resp resp;

    @Override
    public void exec() {
        
    }
}
