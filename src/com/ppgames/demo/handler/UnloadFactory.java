package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.FactoryLevelConfig;
import com.ppgames.demo.config.item.FactoryLevelItem;
import com.ppgames.demo.model.Player;
import com.ppgames.util.BagType;
import com.ppgames.util.TimeUtil;

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
