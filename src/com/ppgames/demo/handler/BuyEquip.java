package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.WholeSaleConfig;
import com.ppgames.demo.config.item.WholeSaleItem;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.questEvent.QuestEventId;
import com.ppgames.util.TimeUtil;

public class BuyEquip extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int cid = req.data.getIntValue("cid");
        // cid == 0 , 批发市场
        Player p = Cache.players.get(pid);
        int eid = req.data.getIntValue("eid");
        int eNum = req.data.getIntValue("enum");

        if (eNum <= 0) {
            resp.send(ErrCode.UNKONW_ERR, "请选择购买数量");
            return;
        }

        WholeSaleItem itemConfig = WholeSaleConfig.getInstance().getItem(eid);
        if (!p.enoughLevel(itemConfig.getNeed_level())) {
            resp.send(ErrCode.UNKONW_ERR, "购买失败，需要店铺等级"+itemConfig.getNeed_level()+"级");
            return;
        }
        int goldbuyprice = itemConfig.getPrice();
        if (p.getGold().get() < itemConfig.getPrice()) {
            resp.send(ErrCode.GOLD_NOT_ENOUGH);
            return;
        }

        int lid = req.data.getIntValue("lid");

        JSONObject logistics = p.getLogistics().getJSONObject(lid);

        JSONObject equip = logistics.getJSONObject("equip");
        if (equip.size() > 0) {
            resp.send(ErrCode.UNKONW_ERR, "该物流正在使用中");
            return;
        }

        int beginTime = logistics.getIntValue("beginTime");
        int decredTime = logistics.getIntValue("decredTime");
        int now = TimeUtil.nowInt();
        if(now - beginTime < decredTime){
            resp.send(ErrCode.UNKONW_ERR, "物流正在返回途中");
            return;
        }

        if (cid == 0) {
            p.questEventListener.dispatchEvent(QuestEventId.BuyFromWholeSale, eNum);
        }

        // 扣金币
        p.getGold().getAndAdd(-goldbuyprice);
        // 货物放入物流车
        equip.put(eid + "", eNum);
        //logistics.put("beginTime", TimeUtil.nowInt());
        logistics.put("beginTime", 0);
        logistics.put("decredTime", 0);
        resp.send(ErrCode.SUCC);
    }
}
