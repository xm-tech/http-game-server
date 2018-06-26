package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.util.BagType;

import java.util.concurrent.ConcurrentHashMap;

// 下架 FIXME 暂未考虑同步的问题
public class RemoveGoods extends GameAct {
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
        if ("-1".equals(posval) || "0".equals(posval)) {
            resp.send(ErrCode.CLOTHES_SOLD_OUT, "下架失败,该货物已经被卖空");
            return;
        }

        String[] split = posval.split("-");
        String equipid = split[0];
        int num = Integer.parseInt(split[1]);
        if (p.getEquipSize() >= p.getEquipsCapacity()) {
            resp.send(ErrCode.UNKONW_ERR, "背包容量已满,下架失败");
            return;
        }

        // 下架
        goods.set(pos, "0");

        // 货物位置表维护 FIXME 优化修正
        ConcurrentHashMap<Integer, JSONArray> goods_places = DataManager.players_goods_places.get(pid);
        int typeid = DataManager.getEquipType(Integer.parseInt(equipid));
        JSONArray places = goods_places.get(typeid);
        if (places == null) {
            places = new JSONArray();
            goods_places.put(typeid, places);
        }
        // 移除货物位置信息
        String meta = shelfidx + "," + pos;
        if (places.contains(meta)) {
            places.remove(meta);
            goods_places.put(typeid, places);
        }
        // 背包添加
        p.addBags("RemoveGoods", BagType.EQUIP, Integer.parseInt(equipid), num);

        resp.send(ErrCode.SUCC);
    }
}
