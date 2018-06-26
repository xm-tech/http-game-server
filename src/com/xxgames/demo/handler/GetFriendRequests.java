package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

public class GetFriendRequests extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        JSONArray friendRequestList = new JSONArray();
        Player p = Cache.players.get(pid);
        for (int i = 0; i < p.getFriendRequestList().size(); i++) {
            long id = p.getFriendRequestList().get(i);
            Player friend = Cache.players.get(id);
            JSONObject object = new JSONObject();
            object.put("Id", friend.getId());
            object.put("Name", friend.getName());
            object.put("Lv", friend.getLevel());
            object.put("Charm", friend.getCharm());
            object.put("Equips", friend.getDressRoom().getJSONArray("equips"));
            object.put("Heents", friend.getLogo());
            object.put("Head", friend.getHead());
            friendRequestList.add(object);
        }

        resp.data.put("requests", friendRequestList);
        resp.send(ErrCode.SUCC);
    }
}
