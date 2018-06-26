package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

/**
 * Created by PhonePadPC on 2017/7/19.
 */
public class ClearnRubbishs extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int num = req.data.getInteger("num");
        if (num > 10) num = 10 ;
        if (num < 0) num = 0 ;
        Player p = Cache.players.get(pid);
        if (p != null){
            for (int i = 0 ; i < num ; i ++){
                p.getShops().clearnOneRubbish();
            }
        }

        resp.send(ErrCode.SUCC);
    }
}
