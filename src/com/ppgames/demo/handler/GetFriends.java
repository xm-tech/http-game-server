package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

import java.util.ArrayList;
import java.util.List;

public class GetFriends extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        JSONArray friendList = new JSONArray();
        Player p = Cache.players.get(pid);

        if (p == null) {
            resp.send(ErrCode.UNKONW_ERR, "玩家信息出错,没有此玩家");
            return;
        }
        List<Long> npc_id_list = new ArrayList<>();
        //add npc friend
        for (int npc_i = 0 ; npc_i < p.getNpcFriendList().size(); npc_i ++){
            JSONObject object = new JSONObject();
            object.put("Id", p.getNpcFriendList().getJSONObject(npc_i).getIntValue("id"));
            object.put("Charm", p.getNpcFriendList().getJSONObject(npc_i).getIntValue("charm"));
            object.put("Name", "npc");
            object.put("Lv", 0);
            object.put("Equips", new JSONArray());
            object.put("Heents", new JSONArray());
            object.put("Head", 0);
            friendList.add(object);
        }
        //add friend
        for (int i = 0; i < p.getFriendList().size(); i++) {
            long id = p.getFriendList().get(i);

            Player friend = Cache.players.get(id);
            if (friend == null) {
                resp.send(ErrCode.UNKONW_ERR, "玩家信息出错,没有此好友");
                continue;
            }
            JSONObject object = new JSONObject();
            object.put("Id", friend.getId());
            object.put("Name", friend.getName());
            object.put("Lv", friend.getLevel());
            object.put("Charm", friend.getCharm());
            object.put("Equips", friend.getDressRoom().getJSONArray("equips"));
            object.put("Heents", friend.getLogo());
            object.put("Head", friend.getHead());
            friendList.add(object);

        }

        resp.data.put("friends", friendList);
        resp.data.put("requestCount", p.getFriendRequestList().size());
        resp.send(ErrCode.SUCC);
    }
}
