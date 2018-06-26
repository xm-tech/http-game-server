package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

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
