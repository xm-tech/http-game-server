package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.util.TimeUtil;

public class GetFactorys extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        resp.data.put("factorys", p.getFactorys());
        resp.data.put("systime", TimeUtil.nowInt());
        resp.send(ErrCode.SUCC);
    }
}
