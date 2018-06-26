package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.item.ShelfItem;
import com.xxgames.demo.config.ShelfLevel;
import com.xxgames.demo.config.config.ShelLevelConfig;
import com.xxgames.demo.config.config.ShelfConfig;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;
import com.xxgames.util.BagType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 批量上架,规则: 没卖完的格子补满原先的货,空格子按背包中满足条件服饰利润从高到低的顺序上架 FIXME 优化
public class BatchAddGoods extends GameAct {

    private static Logger log = LoggerFactory.getLogger(BatchAddGoods.class);

    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");

        Player p = Cache.players.get(pid);
        JSONArray shelfs = p.getShops().getShelves();
        JSONArray equips = new JSONArray();
        for (int pos = 0; pos < shelfs.size(); pos++) {
            JSONObject shelf = shelfs.getJSONObject(pos);
            int status = shelf.getIntValue("status");
            if (status == 0) {
                //resp.send(ErrCode.UNKONW_ERR, "未装修的货架，不可使用");
                continue;
            }
            BatchShelfAddGoods(p,shelf,pos,equips);
        }
        //向任务系统发送消息
        p.questEventListener.dispatchEvent(QuestEventId.ShelfAddGoods, 1);

        resp.data.put("shelves", p.getShops().getShelves());
        resp.data.put("equips",equips);
        resp.send(ErrCode.SUCC);
    }
    private void BatchShelfAddGoods(Player p,JSONObject shelf,int shelf_index,JSONArray equips){
        long pid = p.getId();
        int shelfid = shelf.getIntValue("fid");// t_shelf->id
        int level = shelf.getIntValue("level");
        int levelId = (shelfid * 100) + level;
        ShelfLevel shelfLevelConfig = ShelLevelConfig.getInstance().getItem(levelId);
        ShelfItem shelfconfig = ShelfConfig.getInstance().getItem(shelfid);
        log.debug("sellTypes: " + shelfconfig.getSell_type().toString());

        JSONArray goods = shelf.getJSONArray("goods");
        for (int pos = 0; pos < goods.size(); pos++) {
            String posval = goods.getString(pos);
            if (posval.equals("-1")) {
                // 未解锁
                log.debug(p.getId() + "," + shelfid + "," + level + "," + pos + ",未解锁,略过");
                continue;
            }

            int maxNum = shelfLevelConfig.getPosition_num().get(pos);
            if (posval.length() > 1) {
                // 原先有货
                String[] split = posval.split("-");
                int equipid = Integer.parseInt(split[0]);
                int oldNum = Integer.parseInt(split[1]);
                int canAddNum = maxNum - oldNum;
                if (canAddNum <= 0) {
                    // 位置数量超限 防止策划修改每个位置的上限
                    log.debug(
                        "数量超限," + p.getId() + "," + shelfid + "," + level + "," + pos + "," + posval + "," + maxNum + "," +
                            oldNum);
                    continue;
                }
                int hasnum = p.getBagNum(BagType.EQUIP, equipid).intValue();
                int addNum;
                if (hasnum > canAddNum) {
                    addNum = canAddNum;
                } else {
                    addNum = hasnum;
                }
                p.decrBags("BatchAddGoods", BagType.EQUIP, equipid, -addNum);

                int newNum = oldNum + addNum;
                posval = equipid + "-" + newNum;
                goods.set(pos, posval);
                JSONObject equip = new JSONObject();
                equip.put("id",equipid);
                equip.put("num",addNum);
                equips.add(equip);
                continue;
            }

            if (posval.equals("0")) {
                // 原先无货
                int sellType = shelfconfig.getSell_type().get(pos);//sellTypes.getIntValue(pos);
                Map<Integer, ArrayList<Integer>> equipTypeMaps = DataManager.player_equiptype_maps.get(pid);
                if (equipTypeMaps == null) {
                    equipTypeMaps = DataManager.buildAndGetTypeEquipMaps(p);
                }
                ArrayList<Integer> equipids = equipTypeMaps.get(sellType);
                if (equipids == null || equipids.size() == 0) {
                    log.debug(sellType + " null" + "," + p.getId() + "," + shelfid + "," + level + "," + pos + "," + posval);
                    continue;
                }
                // FIXME 上架利润最高的货物(利润相同则按id升序)
                Integer equipid = equipids.get(0);
                int hasnum = p.getBagNum(BagType.EQUIP, equipid).intValue();
                int addNum;
                if (hasnum > maxNum) {
                    addNum = maxNum;
                } else {
                    addNum = hasnum;
                }
                if (addNum <= 0) {
                    continue;
                }
                // 扣背包
                p.decrBags("BatchAddGoods", BagType.EQUIP, equipid, -addNum);

                posval = equipid + "-" + addNum;
                goods.set(pos, posval);

                // 货物位置表维护
                ConcurrentHashMap<Integer, JSONArray> goods_places = DataManager.players_goods_places.get(pid);
                if (goods_places == null) {
                    p.int_goods_places();
                    goods_places = DataManager.players_goods_places.get(pid);
                }
                JSONArray places = goods_places.get(sellType);
                if (places == null) {
                    places = new JSONArray();
                }
                // 添加货物位置信息
                String meta = shelf_index + "," + pos;
                if (!places.contains(meta)) {
                    places.add(meta);
                }
                goods_places.put(sellType, places);
                JSONObject equip = new JSONObject();
                equip.put("id",equipid);
                equip.put("num",addNum);
                equips.add(equip);
                log.debug(
                    "newadd," + p.getId() + "," + shelfid + "," + level + "," + pos + "," + posval + "," + maxNum + "," +
                        hasnum + "," + addNum);
                continue;
            }
        }
    }

}
