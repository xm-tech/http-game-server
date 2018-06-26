package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.DressRoomLevelConfig;
import com.xxgames.demo.config.item.DressRoomLevelItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;

public class UpgradeDressRoom extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        JSONObject dressRoom = p.getDressRoom();
        int level = dressRoom.getIntValue("level");

        DressRoomLevelItem config = DressRoomLevelConfig.getInstance().getItem(level);
        if (config.getNeed_level() == 0) {
            resp.send(ErrCode.UNKONW_ERR, "已最高级，升级失败");
            return;
        }
        if (!p.enoughLevel(config.getNeed_level())) {
            resp.send(ErrCode.UNKONW_ERR, "升级失败，需要店铺等级"+config.getNeed_level()+"级");
            return;
        }

        if (p.getGold().get() < config.getGold()) {
            resp.send(ErrCode.GOLD_NOT_ENOUGH);
            return;
        }

        p.questEventListener.dispatchEvent(QuestEventId.DressRoomLevel, level + 1);

        p.getGold().getAndAdd(-config.getGold());
        dressRoom.put("level", ++level);
        resp.send(ErrCode.SUCC);
    }
}
