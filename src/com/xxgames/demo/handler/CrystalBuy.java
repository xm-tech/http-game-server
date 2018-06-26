package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.DreamCrystalStoreConfig;
import com.xxgames.demo.config.item.DreamCrystalStoreConfigItem;
import com.xxgames.demo.model.Player;
import com.xxgames.util.BagType;

public class CrystalBuy extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int itemid = req.data.getIntValue("itemid");
        int num = req.data.getIntValue("num");
        Player p = Cache.players.get(pid);

        DreamCrystalStoreConfigItem storeConf = DreamCrystalStoreConfig.getInstance().getItem(itemid);//DataManager.dream_crystal_store.getJSONObject(itemid + "");
        // 水晶数量判断
        int price = storeConf.getPrice();
        if (p.checkMonthCard()){
            price = (int)(price*1.0*0.9);
        }
        int need = price * num;
        boolean enough = p.bagEnough(BagType.CRYSTAL, 0, need);
        if (!enough) {
            resp.send(ErrCode.UNKONW_ERR, "水晶数量不够,购买失败");
            return;
        }
        // 扣水晶
        p.decrBags("CrystalBuy", BagType.CRYSTAL, 0, -need);
        p.addBags("CrystalBuy", storeConf.getType(), itemid, num);

        resp.send(ErrCode.SUCC);
    }
}
