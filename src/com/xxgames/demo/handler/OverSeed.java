package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.model.Player;
import com.xxgames.util.TimeUtil;

// 花盆收获
public class OverSeed extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        // 花盆id
        int id = req.data.getIntValue("id");

        Player p = Cache.players.get(pid);

        JSONObject flowPot = p.getFlowerPots().getJSONObject(id);
        int status = flowPot.getIntValue("status");
        if (status == 0) {
            resp.send(ErrCode.UNKONW_ERR, "未装修的花盆,收获失败");
            return;
        }

        JSONArray seed = flowPot.getJSONArray("seed");
        int itemid = seed.getIntValue(0);
        if (itemid == -1) {
            resp.send(ErrCode.UNKONW_ERR, "未播种过,收获失败");
            return;
        }

        int sowTime = seed.getIntValue(1);
        int now = TimeUtil.nowInt();
        int seed_time = SystemConfConfig.getInstance().getCfg().getSeed_time();
        if (now - sowTime < seed_time) {
            resp.send(ErrCode.UNKONW_ERR, "未到收获时间,收获失败");
            return;
        }

        // 更新花盆状态为初始状态
        seed.set(0, -1);
        seed.set(1, -1);
        // 收获
        JSONObject awards = p.openBox(itemid);
        resp.data.putAll(awards);
        resp.send(ErrCode.SUCC);
    }
}
