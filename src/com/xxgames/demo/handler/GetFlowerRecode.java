package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

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
