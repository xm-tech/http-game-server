package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.FactoryLevelConfig;
import com.xxgames.demo.config.item.FactoryLevelItem;
import com.xxgames.demo.model.Player;
import com.xxgames.util.BagType;
import com.xxgames.util.TimeUtil;

import java.util.Set;

// 工厂货物收取
public class UnloadFactory extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int fid = req.data.getIntValue("fid");
        Player p = Cache.players.get(pid);

        JSONObject factorys = p.getFactorys().getJSONObject(fid);

        JSONObject equip = factorys.getJSONObject("equip");
        Set<String> keys = equip.keySet();
        if (keys.size() == 0) {
            resp.send(ErrCode.UNKONW_ERR, "空工厂不能收取");
            return;
        }
        if (p.getEquipSize() >= p.getEquipsCapacity()) {
            resp.send(ErrCode.UNKONW_ERR, "背包容量已满");
            return;
        }

        // 单循环
        for (String equipid : keys) {
            // 工厂里面货物数量
            int inNum = equip.getIntValue(equipid);

            // 判断是否到收取时间
            int beginTime = factorys.getIntValue("beginTime");
            int decredTime = factorys.getIntValue("decredTime");
            int level = factorys.getIntValue("level");
            FactoryLevelItem levelConf = FactoryLevelConfig.getInstance().getItem(level);
            int confPerSec = levelConf.getTime();
            int now = TimeUtil.nowInt();
            if (now - beginTime + decredTime < confPerSec * inNum) {
                resp.send(ErrCode.UNKONW_ERR, "时间未到，收取失败");
                return;
            }

            if (!p.addBags("UnloadFactory", BagType.EQUIP, Integer.parseInt(equipid), inNum).isSucc()) {
                resp.send(ErrCode.UNKONW_ERR, "背包容量已满");
                return;
            }

            // 清空物工厂
            factorys.put("equip", new JSONObject());
            factorys.put("beginTime", 0);
            factorys.put("decredTime", 0);
        }

        resp.data.put("fid", fid);
        resp.send(ErrCode.SUCC);
    }
}
