package com.xxgames.demo.handler;

import com.xxgames.core.GameAct;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameActs {

    // <msgid,GameAct>
    private Map<Integer, GameAct> all = new ConcurrentHashMap<>();

    public static void init() {
        SingletonHolder.instance.register();
    }

    private synchronized void register() {
        all.clear();

        MsgIds[] values = MsgIds.values();
        if (values != null && values.length > 0) {
            for (MsgIds msgIds : values) {
                int msgId = msgIds.getVal();
                GameAct gameAct = msgIds.getGameAct();
                register(msgId, gameAct);
            }
        }

        System.err.println("GameActs init succ");
    }

    private void register(final Integer msgid, GameAct gameAct) {
        all.put(msgid, gameAct);
    }

    public static GameAct get(final int msgid) {
        return SingletonHolder.instance.all.get(msgid);
    }

    public static boolean contains(final int msgid) {
        return SingletonHolder.instance.all.containsKey(msgid);
    }

    private GameActs() {
    }

    private static class SingletonHolder {
        private static GameActs instance = new GameActs();
    }


}
