package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.LogisticsLevelConfig;
import com.ppgames.demo.config.item.LogisticsLevelItem;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.questEvent.QuestEventId;
import com.ppgames.util.BagType;
import com.ppgames.util.TimeUtil;

import java.util.Set;

// 收取物流车服饰
public class UnloadLogistics extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int lid = req.data.getIntValue("lid");
        Player p = Cache.players.get(pid);

        JSONObject logistics = p.getLogistics().getJSONObject(lid);

        JSONObject equip = logistics.getJSONObject("equip");
        Set<String> keys = equip.keySet();
        if (keys.size() == 0) {
            resp.send(ErrCode.UNKONW_ERR, "空物流不能收取");
            return;
        }
        if (p.getEquipSize() >= p.getEquipsCapacity()) {
            resp.send(ErrCode.UNKONW_ERR, "背包容量已满");
            return;
        }

        // 单循环
        assert (keys.size() == 1);
        for (String equipid : keys) {
            // 车里面货物数量
            int inNum = equip.getIntValue(equipid);
            int level = logistics.getIntValue("level");

            LogisticsLevelItem levelItemConfig = LogisticsLevelConfig.getInstance().getItem(level);
            int confPerSec = levelItemConfig.getTime();
            int now = TimeUtil.nowInt();

            if (!p.addBags("UnloadLogistics", BagType.EQUIP, Integer.parseInt(equipid), inNum).isSucc()) {
                resp.send(ErrCode.UNKONW_ERR, "背包容量已满");
                return;
            }

            // 清空物流车
            logistics.put("equip", new JSONObject());
            logistics.put("beginTime", now);
            logistics.put("decredTime", confPerSec * inNum);
            resp.data.put("beginTime", now);
            resp.data.put("decredTime", confPerSec * inNum);
        }

        p.questEventListener.dispatchEvent(QuestEventId.GetFromLogistics, 1);
        resp.data.put("lid", lid);

        resp.send(ErrCode.SUCC);
    }
}
