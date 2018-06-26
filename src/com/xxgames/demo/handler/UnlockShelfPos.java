package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.item.ShelfItem;
import com.xxgames.demo.config.config.ShelfConfig;
import com.xxgames.demo.model.Player;

// shelf->pos="0"
public class UnlockShelfPos extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int shelfidx = req.data.getIntValue("shelfidx");
        int pos = req.data.getIntValue("pos");

        Player p = Cache.players.get(pid);
        JSONArray shelfs = p.getShops().getShelves();
        JSONObject shelf = shelfs.getJSONObject(shelfidx);
        int status = shelf.getIntValue("status");
        if (status == 0) {
            resp.send(ErrCode.UNKONW_ERR, "未装修的货架，不可使用");
            return;
        }

        JSONArray goods = shelf.getJSONArray("goods");
        String posval = goods.getString(pos);
        if (!posval.equals("-1")) {
            resp.send(ErrCode.UNKONW_ERR, "不可重复解锁");
            return;
        }

        int shelfid = shelf.getIntValue("fid");
        //JSONObject shelfConfObj = DataManager.shelfs.getJSONObject(shelfid + "");
        ShelfItem config = ShelfConfig.getInstance().getItem(shelfid);
        //JSONArray open_golds = JSON.parseArray(shelfConfObj.getString("open_gold"));
        //JSONArray open_diamonds = JSON.parseArray(shelfConfObj.getString("open_diamond"));
        int open_gold = config.getOpen_gold().get(pos);//open_golds.getIntValue(pos);
        int open_diamond = config.getOpen_diamond().get(pos);//open_diamonds.getIntValue(pos);
        if (open_gold > 0) {
            boolean decrOk = p.decrGold("UnlockShelfPos", open_gold);
            if (!decrOk) {
                resp.send(ErrCode.GOLD_NOT_ENOUGH);
                return;
            }
        } else if (open_diamond > 0) {
            boolean decrOk = p.decrDiamond("UnlockShelfPos", open_diamond);
            if (!decrOk) {
                resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
                return;
            }
        }
        posval = "0";
        goods.set(pos, posval);
        resp.send(ErrCode.SUCC);
    }
}
