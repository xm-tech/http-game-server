package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.DataManager;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.item.ShelfItem;
import com.ppgames.demo.config.ShelfLevel;
import com.ppgames.demo.config.config.ShelLevelConfig;
import com.ppgames.demo.config.config.ShelfConfig;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.questEvent.QuestEventId;
import com.ppgames.util.BagType;

import java.util.concurrent.ConcurrentHashMap;

// 给1个货架格子添加货物
public class AddGoods extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int shelfidx = req.data.getIntValue("shelfidx");
        int equipid = req.data.getIntValue("equipid");
        int num = req.data.getIntValue("num");
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

        if (posval.equals("-1")) {
            resp.send(ErrCode.UNKONW_ERR, "未解锁");
            return;
        }

        boolean enough = p.bagEnough(BagType.EQUIP, equipid, num);
        if (!enough) {
            resp.send(ErrCode.ITEM_NOT_ENOUGH, "数量不足");
            return;
        }

        int oldNum = 0;
        if (posval.length() > 1) {
            String[] split = posval.split("-");
            int oldEquipId = Integer.parseInt(split[0]);
            oldNum = Integer.parseInt(split[1]);
            if (equipid != oldEquipId) {
                resp.send(ErrCode.UNKONW_ERR, "该位置只能放已有的服饰");
                return;
            }
        }

        int shelfid = shelf.getIntValue("fid");// t_shelf->id

        // 位置数量上限判断
        int level = shelf.getIntValue("level");
        int levelId = (shelfid * 100) + level;
        ShelfLevel shelfLevel = ShelLevelConfig.getInstance().getItem(levelId);
        int maxGoodsNum = shelfLevel.getPosition_num().get(pos);
        if (oldNum + num > maxGoodsNum) {
            resp.send(ErrCode.UNKONW_ERR, "该位置可放数量超限");
            return;
        }

        //JSONObject shelfConfObj = DataManager.shelfs.getJSONObject(shelfid + "");
        ShelfItem config = ShelfConfig.getInstance().getItem(shelfid);
        int typeid = DataManager.getEquipType(equipid);
        //JSONArray confTypes = JSON.parseArray(shelfConfObj.getString("sell_type"));
        int confType = config.getSell_type().get(pos);//confTypes.getIntValue(pos);
        if (confType != typeid) {
            resp.send(ErrCode.UNKONW_ERR, "服饰类型错误," + confType + "," + typeid);
            return;
        }

        // 扣背包
        p.decrBags("AddGoods", BagType.EQUIP, equipid, -num);

        // 上架 FIXME 可能需要同步暂时先忽略
        int newNum = oldNum + num;
        posval = equipid + "-" + newNum;
        goods.set(pos, posval);

        //向任务系统发送消息
        p.questEventListener.dispatchEvent(QuestEventId.ShelfAddGoods, 1);


        // 货物位置表维护
        ConcurrentHashMap<Integer, JSONArray> goods_places = DataManager.players_goods_places.get(pid);
        if (goods_places == null) {
            p.int_goods_places();
            goods_places = DataManager.players_goods_places.get(pid);
        }
        JSONArray places = goods_places.get(typeid);
        if (places == null) {
            places = new JSONArray();
        }
        // 添加货物位置信息
        String meta = shelfidx + "," + pos;
        if (!places.contains(meta)) {
            places.add(meta);
        }
        goods_places.put(typeid, places);
        resp.send(ErrCode.SUCC);
    }

}
