package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.BlueprintConfig;
import com.xxgames.demo.config.config.EquipConfig;
import com.xxgames.demo.config.config.FurnitureConfig;
import com.xxgames.demo.config.config.ItemConfig;
import com.xxgames.demo.config.item.EquipItem;
import com.xxgames.demo.config.item.FurnitureItem;
import com.xxgames.demo.model.Player;
import com.xxgames.util.BagType;

public class Sell extends GameAct {

    @Override
    public void exec(GameReq req, GameResp resp) {
        // 物品类别(0-服装,1-道具,2-家具)
        int sellNum = req.data.getIntValue("num");
        if (sellNum <= 0) {
            resp.send(ErrCode.UNKONW_ERR, "销售失败,num=" + sellNum);
            return;
        }

        int itemType = req.data.getIntValue("type");
        int itemId = req.data.getIntValue("id");

        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        switch (itemType) {
        case 0:
            // 服饰
            int playerEquipNum = p.getBagNum(BagType.EQUIP, itemId).intValue();
            if (playerEquipNum < sellNum) {
                resp.send(ErrCode.ITEM_NOT_ENOUGH, "数量不够");
                return;
            }
            boolean decrOk = p.decrBags("Sell", BagType.EQUIP, itemId, sellNum);
            if (!decrOk) {
                resp.send(ErrCode.ITEM_NOT_ENOUGH, "数量不够");
                return;
            }

            EquipItem equip = EquipConfig.getInstance().getItem(itemId);
            int goldPrice = equip.getGoldsellprice() * sellNum;
            p.addBags("Sell", BagType.GOLD, 0, goldPrice);
            break;
        case 1:
            // 道具
            if (!p.decrBags("Sell", BagType.ITEM, itemId, -sellNum)) {
                resp.send(ErrCode.ITEM_NOT_ENOUGH, "数量不够");
                return;
            }
            p.addBags("Sell", BagType.GOLD, 0,
                ItemConfig.getInstance().getItem(itemId).getGoldsellprice() * sellNum);
                      //DataManager.items.getJSONObject(itemId + "").getIntValue("goldsellprice"));
            break;
        case 2:
            // 家具, 暂时只处理了普通家具
            int playerFunitureNum = p.getFurnitures().getIntValue(itemId + "");
            if (playerFunitureNum < sellNum) {
                resp.send(ErrCode.ITEM_NOT_ENOUGH, "数量不够");
                return;
            }

            p.decrBags("Sell", BagType.FURNITURE, itemId, -sellNum);

            FurnitureItem config = FurnitureConfig.getInstance().getItem(itemId);
            if(config == null){
                resp.send(ErrCode.UNKONW_ERR, "家具不存在");
                return;
            }
            //JSONObject confFurnituresObj = DataManager.furnitures.getJSONObject(itemId + "");
            //int funitureGoldPrice = confFurnituresObj.getIntValue("goldsellprice");
            playerFunitureNum -= sellNum;
            if (playerFunitureNum == 0) {
                p.getFurnitures().remove(itemId + "");
            } else {
                p.getFurnitures().put(itemId + "", playerFunitureNum);
            }
            p.getGold().getAndAdd(config.getGoldsellprice() * sellNum);
            break;
        case 3:
            // 蓝图
            if (!p.decrBags("Sell", BagType.BLUEPRINT, itemId, -sellNum)) {
                resp.send(ErrCode.ITEM_NOT_ENOUGH, "数量不够");
                return;
            }
            int addGold = BlueprintConfig.getInstance().getItem(itemId).getGoldsellprice();
            p.addBags("Sell", BagType.GOLD, 0,
                addGold * sellNum);
            break;

        default:
            resp.send(ErrCode.UNKONW_ERR, "销售失败");
            return;
        }

        resp.send(ErrCode.SUCC);
    }

}
