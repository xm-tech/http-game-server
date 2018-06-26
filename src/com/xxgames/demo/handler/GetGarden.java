package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.util.TimeUtil;

public class GetGarden extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        int lastBuyTime = p.getGarden().getIntValue(0);
        int now = TimeUtil.nowInt();
        if (!TimeUtil.sameDay(lastBuyTime, now)) {
            p.getGarden().set(1, 0);
        }

        resp.data.put("garden", p.getGarden());
        resp.data.put("systime", now);
        resp.send(ErrCode.SUCC);
    }
}
