package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

// 签到
public class CheckIn extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int day = req.data.getIntValue("did");
        int hour = req.data.getIntValue("hid");

        Player p = Cache.players.get(pid);
        //再签到
        if (p.CheckIn(day, hour)) {
            resp.send(ErrCode.SUCC);
        } else {
            resp.send(ErrCode.UNKONW_ERR, "已签到!");
        }
    }


}
