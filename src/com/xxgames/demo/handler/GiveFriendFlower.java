package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.ItemConfig;
import com.xxgames.demo.config.item.Item;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;
import com.xxgames.util.TimeUtil;

public class GiveFriendFlower extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        long rid = req.data.getLongValue("rid");
        int flowerId = req.data.getIntValue("fid");
        Player p = Cache.players.get(pid);
        if (p == null){
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家");
            return;
        }
        if (!p.getItems().containsKey(flowerId + "")) {
            resp.send(ErrCode.UNKONW_ERR, "你没有此物品");
            return;
        }
        int flowerNum = p.getItems().getIntValue(flowerId + "");
        if (flowerNum <= 0) {
            p.getItems().remove(flowerId + "");
            resp.send(ErrCode.UNKONW_ERR, "数量不足");
            return;
        }
        if (Cache.players.containsKey(rid)) {
            doSendFlowerToPlayer(p,rid , flowerId);
            resp.send(ErrCode.SUCC);
        }
        else if (isNpcFriend(p,rid)){
            doSendFlowerToNpc(p,rid , flowerId);
            resp.send(ErrCode.SUCC);
        }
        else {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家");
            return;
        }

        afterSendFlower(p,flowerId);
        resp.send(ErrCode.SUCC);
    }

    private void afterSendFlower(Player p ,int flowerId){
        int flowerNum = p.getItems().getIntValue(flowerId + "");
        //向任务系统发送消息
        p.questEventListener.dispatchEvent(QuestEventId.GiveFriendFlower, 1);
        //减少1个鲜花的库存
        p.getItems().put(flowerId + "", flowerNum - 1);
        if (p.getItems().getIntValue(flowerId + "") <= 0) {
            p.getItems().remove(flowerId + "");
        }
    }
    private void doSendFlowerToNpc(Player player ,long rid ,int flowerId){
        JSONArray npc_friend_list = player.getNpcFriendList();
        for (int i = 0 ; i < npc_friend_list.size(); i ++){
            JSONObject npc = npc_friend_list.getJSONObject(i);
            if (npc != null && npc.getInteger("id") == rid){
                Item config = ItemConfig.getInstance().getItem(flowerId);
                int charm = npc.getInteger("charm");
                npc.put("charm",charm + config.getRarity());
            }
        }
    }
    private void doSendFlowerToPlayer(Player p , long rid ,int flowerId){
        Player friend = Cache.players.get(rid);
        Item config = ItemConfig.getInstance().getItem(flowerId);
        friend.setCharm((short) (friend.getCharm() + config.getRarity()));
        //送花记录
        friend.addGetFlowerRecord(p, config.getId(),TimeUtil.nowInt());
        p.addSendFlowerRecord(friend,config.getId(),TimeUtil.nowInt());
    }
    private Boolean isNpcFriend(Player player ,long pid ){
        JSONArray npc_friend_list = player.getNpcFriendList();
        for (int i = 0 ; i < npc_friend_list.size(); i ++){
            if (npc_friend_list.getJSONObject(i).getInteger("id") == pid){
                return true;
            }
        }
        return false;
    }
}
