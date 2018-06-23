package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

public class SearchFriend extends GameAct {
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

        JSONObject object = new JSONObject();
        object.put("Id", friend.getId());
        object.put("Name", friend.getName());
        object.put("Lv", friend.getLevel());
        object.put("Charm", friend.getCharm());
        //object.put("Equips", friend.dressRoom.getJSONArray("equips"));
        //object.put("Heents", friend.logo);
        object.put("Head", friend.getHead());
        resp.data.put("info", object);
        resp.send(ErrCode.SUCC);
    }
}
