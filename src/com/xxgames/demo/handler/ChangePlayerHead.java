package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

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
