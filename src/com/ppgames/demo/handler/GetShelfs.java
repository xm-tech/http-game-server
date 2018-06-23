package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

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
