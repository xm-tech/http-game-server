package com.xxgames.test.db;

public class LotteryCard implements Command {
    Playerr p;

    public LotteryCard(final long pid) {
        p = getPlayerById(pid);
    }

    @Override
    public void exec() {
        p.login();
    }


    private Playerr getPlayerById(final long pid) {
        System.out.println(pid);
        return null;
    }
}
