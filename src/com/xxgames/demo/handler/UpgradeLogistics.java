package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.LogisticsLevelConfig;
import com.xxgames.demo.config.item.LogisticsLevelItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;
import com.xxgames.util.TimeUtil;

public class UpgradeLogistics extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int lid = req.data.getIntValue("lid");
        Player p = Cache.players.get(pid);

        JSONObject logistics = p.getLogistics().getJSONObject(lid);
        int level = logistics.getIntValue("level");
        LogisticsLevelItem levelItemConfig = LogisticsLevelConfig.getInstance().getItem(level);

        int needLevel = levelItemConfig.getNeed_level();
        if (needLevel == 0) {
            resp.send(ErrCode.UNKONW_ERR, "已最高级，升级失败");
            return;
        }
        if (!p.enoughLevel(needLevel)) {
            resp.send(ErrCode.UNKONW_ERR, "升级失败，需要店铺等级"+needLevel+"级");
            return;
        }
        int beginTime = logistics.getIntValue("beginTime");
        int decredTime = logistics.getIntValue("decredTime");
        int now = TimeUtil.nowInt();
        if (beginTime > 0 && now - beginTime < decredTime) {
            resp.send(ErrCode.UNKONW_ERR, "物流尚未结束,升级失败");
            return;
        }

        long gold = levelItemConfig.getGold();
        if (p.getGold().get() < gold) {
            resp.send(ErrCode.GOLD_NOT_ENOUGH);
            return;
        }
        p.getGold().getAndAdd(-gold);
        p.questEventListener.dispatchEvent(QuestEventId.LogisticsLevel, level + 1);

        logistics.put("level", ++level);
        resp.data.put("lid", lid);
        resp.send(ErrCode.SUCC);
    }
}
