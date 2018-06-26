package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

public class RefuseAddFriend extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        long rid = req.data.getLongValue("rid");

        Player p = Cache.players.get(pid);

        //从请求列表移除
        for (int i = 0; i < p.getFriendRequestList().size(); i++) {
            if (p.getFriendRequestList().get(i) == rid) {
                p.getFriendRequestList().remove(i);
                break;
            }
        }

        resp.send(ErrCode.SUCC);
    }
}
