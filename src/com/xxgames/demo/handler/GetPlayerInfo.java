package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

/**
 * Created by PhonePadPC on 2017/8/25.
 */
public class GetPlayerInfo extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        long targetPid = req.data.getLongValue("targetPid");

        Player player = Cache.players.get(pid);
        if (player == null){
            return ;
        }
        Player targetPlayer = Cache.players.get(targetPid);
        if (targetPlayer == null){
            resp.send(ErrCode.UNKONW_ERR, "该玩家不存在");
            return ;
        }
        JSONObject targetPlayerInfo = new JSONObject();
        targetPlayerInfo.put("targetPid",targetPid);
        targetPlayerInfo.put("name",targetPlayer.getName());
        targetPlayerInfo.put("level",targetPlayer.getLevel());
        targetPlayerInfo.put("head",targetPlayer.getHead());
        targetPlayerInfo.put("charm",targetPlayer.getCharm());
        targetPlayerInfo.put("speed",targetPlayer.getShopSellSpeed());
        targetPlayerInfo.put("vip",targetPlayer.getVipLevel());

        resp.data.put("targetPlayerInfo", targetPlayerInfo);
        resp.send(ErrCode.SUCC);
    }
}
