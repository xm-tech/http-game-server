package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;

public class AgreeAddFriend extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        long rid = req.data.getLongValue("rid");
        if (!Cache.players.containsKey(rid)) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家");
            return;
        }

        Player friend = Cache.players.get(rid);
        Player p = Cache.players.get(pid);
        //FIX . 从SystemConf表里面读取好友上限的配置
        if (p.getFriendList().size() >= 50) {
            resp.send(ErrCode.UNKONW_ERR, "好友已满");
            //从request列表中移除此条请求
            return;
        }

        if (friend.getFriendList().size() >= 50) {
            resp.send(ErrCode.UNKONW_ERR, "对方玩家好友已满");
            //从request列表中移除此条请求
            return;
        }

        //互相添加好友
        friend.getFriendList().add(pid);
        p.getFriendList().add(rid);

        //从请求列表移除
        for (int i = 0; i < p.getFriendRequestList().size(); i++) {
            if (p.getFriendRequestList().get(i) == rid) {
                p.getFriendRequestList().remove(i);
                break;
            }
        }

        JSONObject object = new JSONObject();
        object.put("Id", friend.getId());
        object.put("Name", friend.getName());
        object.put("Lv", friend.getLevel());
        object.put("Charm", friend.getCharm());
        object.put("Equips", friend.getDressRoom().getJSONArray("equips"));
        object.put("Heents", friend.getLogo());
        object.put("Head", friend.getHead());

        resp.data.put("friend", object);
        resp.send(ErrCode.SUCC);
    }
}
