package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

/**
 * Created by PhonePadPC on 2017/8/24.
 */
public class GetFlowerRecode extends GameAct{
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        resp.data.put("record", p.getFlowerRecord());
        resp.send(ErrCode.SUCC);
    }
}
