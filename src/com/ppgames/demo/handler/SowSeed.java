package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.questEvent.QuestEventId;
import com.ppgames.util.TimeUtil;

// 花盆播种
public class SowSeed extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        // 花盆id
        int id = req.data.getIntValue("id");
        // 种子id
        int itemid = req.data.getIntValue("itemid");
        Player p = Cache.players.get(pid);

        JSONObject flowPot = p.getFlowerPots().getJSONObject(id);
        int status = flowPot.getIntValue("status");
        if (status == 0) {
            resp.send(ErrCode.UNKONW_ERR, "未装修的花盆,无法播种");
            return;
        }

        JSONArray seed = flowPot.getJSONArray("seed");
        int oldItemid = seed.getIntValue(0);
        if (oldItemid != -1) {
            resp.send(ErrCode.UNKONW_ERR, "该花盆已播种,请先收获");
            return;
        }

        // 背包里种子数量
        int itemNum = p.getItems().getIntValue(itemid + "");
        if (itemNum <= 0) {
            resp.send(ErrCode.UNKONW_ERR, "种子数量不够,播种失败," + itemNum);
            return;
        }
        // 扣种子
        p.getItems().put(itemid + "", itemNum - 1);
        // 更新 player->flowerPots->seed
        seed.set(0, itemid);
        seed.set(1, TimeUtil.nowInt());

        //向任务系统发送消息
        p.questEventListener.dispatchEvent(QuestEventId.SowSeed, 1);

        resp.data.put("flowPot", flowPot);
        resp.send(ErrCode.SUCC);
    }
}
