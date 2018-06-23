package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.FactoryLevelConfig;
import com.ppgames.demo.config.config.SystemConfConfig;
import com.ppgames.demo.config.item.FactoryLevelItem;
import com.ppgames.demo.model.Player;
import com.ppgames.util.TimeUtil;

import java.util.Set;

// 立即结束1工厂
public class AccelerateFactory extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int fid = req.data.getIntValue("fid");
        Player p = Cache.players.get(pid);

        JSONObject factorys = p.getFactorys().getJSONObject(fid);

        int beginTime = factorys.getIntValue("beginTime");
        if (beginTime == 0 || factorys.getJSONObject("equip").size() == 0) {
            resp.send(ErrCode.UNKONW_ERR, "工厂已到达，或者空工厂，加速失败");
            return;
        }

        JSONObject equip = factorys.getJSONObject("equip");
        Set<String> keys = equip.keySet();
        if (keys.size() == 0) {
            resp.send(ErrCode.UNKONW_ERR, "空工厂不能加速");
            return;
        }
        if (p.getEquipSize() >= p.getEquipsCapacity()) {
            resp.send(ErrCode.UNKONW_ERR, "背包容量已满");
            return;
        }

        // 单循环
        for (String equipid : keys) {
            // 车里面货物数量
            int inNum = equip.getIntValue(equipid);
            int decredTime = factorys.getIntValue("decredTime");
            int level = factorys.getIntValue("level");
            FactoryLevelItem levelConf = FactoryLevelConfig.getInstance().getItem(level);
            // 判断是否到收取时间
            int confPerSec = levelConf.getTime();
            int now = TimeUtil.nowInt();
            int leftTime = (confPerSec * inNum) - (now - beginTime + decredTime);
            if (leftTime < 0) {
                resp.send(ErrCode.UNKONW_ERR, "该工厂已达到");
                return;
            }
            int time = SystemConfConfig.getInstance().getCfg().getClearTransportCDSecondsPerDiamond();
            int needDiamond = leftTime / time + 1;
            boolean decrOk = p.decrDiamond("AccelerateFactory", needDiamond);
            if (!decrOk) {
                resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
                return;
            }
            factorys.put("beginTime", -1);
        }

        resp.data.put("fid", fid);
        resp.send(ErrCode.SUCC);
    }
}
