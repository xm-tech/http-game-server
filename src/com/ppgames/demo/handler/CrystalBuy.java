package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.DreamCrystalStoreConfig;
import com.ppgames.demo.config.item.DreamCrystalStoreConfigItem;
import com.ppgames.demo.model.Player;
import com.ppgames.util.BagType;

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
