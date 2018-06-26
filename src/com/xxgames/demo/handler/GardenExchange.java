package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.GardenStoreConfig;
import com.xxgames.demo.config.item.GardenStoreItem;
import com.xxgames.demo.model.Player;
import com.xxgames.util.BagAddResult;
import com.xxgames.util.BagType;

// 金苹果商店兑换 服饰/图纸/5官 等, 消耗金苹果 FIXME 判断itemid对应表里 是否存在
public class GardenExchange extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int itemid = req.data.getIntValue("itemid");
        int num = req.data.getIntValue("num");
        Player p = Cache.players.get(pid);

        GardenStoreItem storeConf = GardenStoreConfig.getInstance().getItem(itemid);
        // 苹果数量判断
        int price = storeConf.getPrice();
        if (p.checkMonthCard()){
            price = (int)(price*1.0*0.9);
        }
        int need = price * num;
        boolean enough = p.bagEnough(BagType.APPLE, 0, need);
        if (!enough) {
            resp.send(ErrCode.UNKONW_ERR, "金苹果数量不够,购买失败");
            return;
        }
        // 扣苹果
        p.decrApple("GardenExchange", need);

        int type = storeConf.getType();
        BagAddResult bagAddResult = p.addBags("GardenExchange", type, itemid, num);
        resp.data.put("extra", bagAddResult.getAdded());
        resp.send(ErrCode.SUCC);
    }
}
