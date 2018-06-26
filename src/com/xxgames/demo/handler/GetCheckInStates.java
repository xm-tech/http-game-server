package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

// 换装
public class GetCheckInStates extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);
        p.RefreshCheckInStates();
        resp.data.put("checkIn", p.getCheckIn());
        resp.send(ErrCode.SUCC);
    }
}
