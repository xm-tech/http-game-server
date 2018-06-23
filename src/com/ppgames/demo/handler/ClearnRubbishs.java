package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

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
