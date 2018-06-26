package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.StampStoreConfig;
import com.xxgames.demo.config.item.StampStoreItem;
import com.xxgames.demo.model.Player;
import com.xxgames.util.BagAddResult;
import com.xxgames.util.BagType;

/**
 * Created by PhonePadPC on 2017/7/20.
 */
public class StampExchange extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int itemid = req.data.getIntValue("itemid");
        int num = req.data.getIntValue("num");
        Player p = Cache.players.get(pid);

        StampStoreItem storeConf = StampStoreConfig.getInstance().getItem(itemid);
        // 邮票数量判断
        int price = storeConf.getPrice();
        if (p.checkMonthCard()){
            price = (int)(price*1.0*0.9);
        }
        int need = price * num;
        boolean enough = p.bagEnough(BagType.STAMP, 0, need);
        if (!enough) {
            resp.send(ErrCode.UNKONW_ERR, "邮票数量不够,购买失败");
            return;
        }
        // 扣邮票
        p.decrStamp("StampExchange", need);

        int type = storeConf.getType();
        BagAddResult bagAddResult = p.addBags("StampExchange", type, itemid, num);
        resp.data.put("extra", bagAddResult.getAdded());
        resp.send(ErrCode.SUCC);
    }
}
