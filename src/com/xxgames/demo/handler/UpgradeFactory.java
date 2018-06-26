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
import com.xxgames.demo.model.quest.questEvent.QuestEventId;

public class UpgradeFactory extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int fid = req.data.getIntValue("fid");
        Player p = Cache.players.get(pid);

        JSONObject factory = p.getFactorys().getJSONObject(fid);
        int level = factory.getIntValue("level");
        FactoryLevelItem levelConf = FactoryLevelConfig.getInstance().getItem(level);

        int needLevel = levelConf.getNeed_level();
        if (needLevel == 0) {
            resp.send(ErrCode.UNKONW_ERR, "已最高级，升级失败");
            return;
        }
        if (!p.enoughLevel(needLevel)){
            resp.send(ErrCode.UNKONW_ERR, "升级失败，需要店铺等级"+needLevel+"级");
            return;
        }

        if (factory.getIntValue("beginTime") > 0) {
            resp.send(ErrCode.UNKONW_ERR, "工厂尚未结束,升级失败");
            return;
        }

        long gold = levelConf.getGold();
        if (p.getGold().get() < gold) {
            resp.send(ErrCode.GOLD_NOT_ENOUGH);
            return;
        }
        p.getGold().getAndAdd(-gold);

        p.questEventListener.dispatchEvent(QuestEventId.FactorLevel, level + 1);

        factory.put("level", ++level);
        resp.data.put("fid", fid);
        resp.send(ErrCode.SUCC);
    }
}
