package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.SystemShopConfig;
import com.xxgames.demo.config.item.SystemShopItem;
import com.xxgames.demo.model.Player;
import com.xxgames.util.BagAddResult;
import com.xxgames.util.BagType;

public class ShopBuy extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        // 购买的物品id
        int id = req.data.getIntValue("id");
        Player p = Cache.players.get(pid);

        SystemShopItem buyConf = SystemShopConfig.getInstance().getItem(id);
        int need_level = buyConf.getNeed_level();
        if (!p.enoughLevel(need_level)) {
            resp.send(ErrCode.UNKONW_ERR, "购买失败，需要店铺等级"+need_level+"级");
            return;
        }

        int itemid = buyConf.getItemid();
        // 购买数量上限(可拥有的上限)
        int maxNum = buyConf.getNum();
        int type = buyConf.getType();
        // if maxNum == 0 , no limit
        if (maxNum > 0) {
            Number bagNum = p.getBagNum(type, itemid);
            // 不会买钻石和金币,所以是OK的
            if (bagNum.intValue() >= maxNum) {
                resp.send(ErrCode.UNKONW_ERR, "已到上限,购买失败");
                return;
            }
        }

        int goldbuyprice = buyConf.getGoldbuyprice();
        int diamondbuyprice = buyConf.getDiamondbuyprice();
        boolean decrOk = false;
        // 扣钱,购买
        int errcode = 0;
        if (goldbuyprice > 0) {
            decrOk = p.decrBags("ShopBuy", BagType.GOLD, 0, -goldbuyprice);
            errcode = ErrCode.GOLD_NOT_ENOUGH;
        } else if (diamondbuyprice > 0) {
            decrOk = p.decrBags("ShopBuy", BagType.DIAMOND, 0, -diamondbuyprice);
            errcode = ErrCode.DIAMOND_NOT_ENOUGH;
        }
        if (!decrOk) {
            resp.send(errcode);
            return;
        }

        BagAddResult bagAddResult = p.addBags("ShopBuy", type, itemid, 1);
        resp.data.put("extra", bagAddResult.getAdded());
        resp.send(ErrCode.SUCC);
    }

}
