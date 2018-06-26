package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.model.Player;
import com.xxgames.util.TimeUtil;

// 花钻石立即结束物流
public class AccelerateLogistics extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int lid = req.data.getIntValue("lid");
        Player p = Cache.players.get(pid);

        JSONObject logistics = p.getLogistics().getJSONObject(lid);
        if (logistics.getJSONObject("equip").size() > 0) {
            resp.send(ErrCode.UNKONW_ERR, "物流已到达，加速失败");
            return;
        }

        int beginTime = logistics.getIntValue("beginTime");
        int decredTime = logistics.getIntValue("decredTime");
        int now = TimeUtil.nowInt();
        if (beginTime == 0 || now - beginTime >= decredTime) {
            resp.send(ErrCode.UNKONW_ERR, "物流已到达，加速失败");
            return;
        }
        int leftTime = decredTime - (now - beginTime);
        int time = SystemConfConfig.getInstance().getCfg().getClearTransportCDSecondsPerDiamond();
        int needDiamond = leftTime / time + 1;
        boolean decrOk = p.decrDiamond("AccelerateLogistics", needDiamond);
        if (!decrOk) {
            resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
            return;
        }
        logistics.put("beginTime", 0);
        logistics.put("decredTime", 0);
        resp.data.put("lid", lid);
        resp.send(ErrCode.SUCC);
    }
}
