package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

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
