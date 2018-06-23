package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

public class DeleteFriend extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        long rid = req.data.getLongValue("rid");
        Player p = Cache.players.get(pid);

        if (p == null) {
            resp.send(ErrCode.UNKONW_ERR, "玩家信息出错,没有此玩家");
            return;
        }

        boolean hasFriend = false;
        for (int i = 0; i < p.getFriendList().size(); i++) {
            if (p.getFriendList().get(i) == rid) {
                hasFriend = true;
                break;
            }
        }
        if (!hasFriend) {
            resp.send(ErrCode.UNKONW_ERR, "没有此好友");
            return;
        }

        Player friend = Cache.players.get(rid);
        if (friend == null) {
            resp.send(ErrCode.UNKONW_ERR, "玩家信息出错,没有此好友");
            return;
        }

        //从好友列表移除
        for (int i = 0; i < p.getFriendList().size(); i++) {
            if (p.getFriendList().get(i) == rid) {
                p.getFriendList().remove(i);
                break;
            }
        }

        for (int i = 0; i < friend.getFriendList().size(); i++) {
            if (friend.getFriendList().get(i) == pid) {
                friend.getFriendList().remove(i);
                break;
            }
        }

        resp.send(ErrCode.SUCC);
    }
}
