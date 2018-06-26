package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

public class RequestAddFriend extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        long rid = req.data.getLongValue("rid");
        if (!Cache.players.containsKey(rid)) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家");
            return;
        }

        Player p = Cache.players.get(pid);
        if (p.getFriendList().contains(rid)) {
            resp.send(ErrCode.UNKONW_ERR, "已经是好友");
            return;
        }

        Player friend = Cache.players.get(rid);

        if (friend.getFriendRequestList().contains(pid)) {
            resp.send(ErrCode.UNKONW_ERR, "已经发送过申请");
            return;
        }

        friend.getFriendRequestList().add(pid);
        resp.send(ErrCode.SUCC);
    }
}
