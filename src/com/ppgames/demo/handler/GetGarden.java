package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;
import com.ppgames.util.TimeUtil;

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
