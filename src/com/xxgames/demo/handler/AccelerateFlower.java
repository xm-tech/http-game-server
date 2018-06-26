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

/**
 * Created by PhonePadPC on 2017/8/9.
 */
public class AccelerateFlower extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player player = Cache.players.get(pid);
        // 花盆id
        int id = req.data.getIntValue("id");
        if (player == null) return ;
        JSONObject flowPot = player.getFlowerPots().getJSONObject(id);
        int status = flowPot.getIntValue("status");
        if (status == 0) {
            resp.send(ErrCode.UNKONW_ERR, "未装修的花盆，加速失败");
            return;
        }
        JSONArray seed = flowPot.getJSONArray("seed");
        int itemid = seed.getIntValue(0);
        if (itemid == -1) {
            resp.send(ErrCode.UNKONW_ERR, "未播种过,加速失败");
            return;
        }
        int sowTime = seed.getIntValue(1);
        int now = TimeUtil.nowInt();
        int seed_time = SystemConfConfig.getInstance().getCfg().getSeed_time();
        int leftTime = seed_time - (now - sowTime);
        if (leftTime < 0){
            resp.send(ErrCode.UNKONW_ERR, "已经成熟,加速失败");
            return;
        }
        int time = SystemConfConfig.getInstance().getCfg().getClearTransportCDSecondsPerDiamond();
        int needDiamond = leftTime / time + 1;
        boolean decrOk = player.decrDiamond("AccelerateFlower", needDiamond);
        if (!decrOk) {
            resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
            return;
        }
        sowTime = now - seed_time;
        seed.set(1,sowTime);
        resp.data.put("flowPot", flowPot);
        resp.data.put("needDiamond",needDiamond);
        resp.send(ErrCode.SUCC);
    }
}
