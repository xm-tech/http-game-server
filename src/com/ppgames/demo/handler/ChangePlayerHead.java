package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

// 改名
public class ChangePlayerHead extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);
        short newHeadID = req.data.getShortValue("hid");

        if (newHeadID == p.getHead()) {
            resp.send(ErrCode.UNKONW_ERR, "新头像和旧头像相同");
            return;
        }
        p.setHead(newHeadID);
        resp.send(ErrCode.SUCC);
    }
}
