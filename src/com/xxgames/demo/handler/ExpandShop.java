package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.ShopExpandConfig;
import com.xxgames.demo.config.item.ShopExpandItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;

// 货架升级就是 t_shelf_level->id+1
public class ExpandShop extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int currencyType = req.data.getIntValue("currencytype");
        Player p = Cache.players.get(pid);

        int level = p.getShops().getLevel();

        if (level >= ShopExpandConfig.getInstance().getSize()) {
            resp.send(ErrCode.UNKONW_ERR, "已经升到顶级");
            return;
        }

        ShopExpandItem data = ShopExpandConfig.getInstance().getItem(level - 1);

        //0 是金币 1是钻石
        if (currencyType == 0) {
            if (!p.enoughLevel(data.getNeed_level())) {
                resp.send(ErrCode.UNKONW_ERR, "等级不够，不能使用金币升级");
                return;
            }
            boolean decrOk = p.decrGold("ExpandShop", data.getGold());
            if (!decrOk) {
                resp.send(ErrCode.GOLD_NOT_ENOUGH);
                return;
            }
        } else if (currencyType == 1) {
            boolean decrOk = p.decrDiamond("ExpandShop", data.getDiamond());
            if (!decrOk) {
                resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
                return;
            }
        } else {
            resp.send(ErrCode.UNKONW_ERR, "非法的货币类型");
            return;
        }

        p.questEventListener.dispatchEvent(QuestEventId.ShopExpandLevel, level + 1);
        p.getShops().setLevel(++level);
        resp.send(ErrCode.SUCC);
    }
}
