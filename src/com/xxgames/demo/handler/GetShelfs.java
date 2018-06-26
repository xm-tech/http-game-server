package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

public class GetShelfs extends GameAct {

    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);
        JSONArray shelfs = p.getShops().getShelves();
        resp.data.put("shelves", shelfs);
        resp.send(ErrCode.SUCC);
    }
}
