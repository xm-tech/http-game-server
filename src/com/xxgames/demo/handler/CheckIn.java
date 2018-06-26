package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

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
