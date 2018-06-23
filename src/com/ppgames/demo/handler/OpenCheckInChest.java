package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

// 换装
public class OpenCheckInChest extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int chestId = req.data.getIntValue("cid");
        Player p = Cache.players.get(pid);
        //再签到
        if (p.OpenCheckInChest(chestId)) {
            resp.send(ErrCode.SUCC);
        } else {
            resp.send(ErrCode.UNKONW_ERR, "已开启!");
        }
    }


}
