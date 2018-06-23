package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.battle.PlayerPvp;
import com.ppgames.util.TimeUtil;

public class PvpBuyBattleNum extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        PlayerPvp pvp = p.getPvp();
        int now = TimeUtil.nowInt();
        if (!TimeUtil.sameDay(pvp.getBuybattletime(), now)) {
            pvp.setBuybattlenum(0);
        }

        // 扣钻
        int needDiamond = pvp.getBuyBattleNumDiamond();
        boolean decrOk = p.decrDiamond("PvpBuyBattleNum", needDiamond);
        if (decrOk) {
            pvp.addBuyBattleNum(1, now);
            resp.data.put("buybattlenum", pvp.getBuybattlenum());
            resp.send(ErrCode.SUCC);
        } else {
            resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
        }
        resp.data.put("useddiamond", needDiamond);
    }
}
