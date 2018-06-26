package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.battle.PlayerPvp;
import com.xxgames.util.TimeUtil;

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
