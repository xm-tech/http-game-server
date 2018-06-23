package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

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
