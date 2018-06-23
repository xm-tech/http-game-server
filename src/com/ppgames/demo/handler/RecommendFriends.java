package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;
import com.ppgames.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class RecommendFriends extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        JSONArray recommendList = new JSONArray();
        Player p = Cache.players.get(pid);
        List<Long> playerIds = new ArrayList<Long>(Cache.players.keySet());
        List<Long> recommendsId = new ArrayList<Long>();
        int i = 0;
        int j = 0;
        //fix. 从配置表里读取一次推荐的好友数量.小于100次循环
        while (i < 6 && j <= 100) {
            Player friend = getRandomPlayer(playerIds);
            j++;
            //不是玩家自己id,不在玩家好友列表中,不在玩家申请列表中,不在被申请玩家好友申请列表中
            if (recommendsId.contains(friend.getId()) || friend.getId() == pid || p.getFriendList().contains(
                friend.getId()) ||
                p.getFriendRequestList().contains(friend.getId())
                || friend.getFriendRequestList().contains(pid)) {
                continue;
            }
            i++;
            recommendsId.add(friend.getId());
            JSONObject object = new JSONObject();
            object.put("Id", friend.getId());
            object.put("Name", friend.getName());
            object.put("Lv", friend.getLevel());
            object.put("Charm", friend.getCharm());
            //object.put("Equips", friend.dressRoom.getJSONArray("equips"));
            //object.put("Heents", friend.logo);
            object.put("Head", friend.getHead());
            recommendList.add(object);
        }

        resp.data.put("list", recommendList);
        resp.send(ErrCode.SUCC);
    }

    Player getRandomPlayer(List<Long> ids) {
        int idx = RandomUtil.nextInt(ids.size());
        return Cache.players.get(ids.get(idx));
    }
}
