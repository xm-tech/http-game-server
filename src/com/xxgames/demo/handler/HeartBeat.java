package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.chat.MsgQ;
import com.xxgames.demo.model.quest.QuestList;
import com.xxgames.demo.model.quest.QuestManager;
import com.xxgames.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// client heartbeat per 10 sec(conf by t_sysconf)
public class HeartBeat extends GameAct {

    private static final Logger log = LoggerFactory.getLogger(HeartBeat.class);

    @Override
    public void exec(GameReq req, GameResp resp) {
        int now = TimeUtil.nowInt();
        resp.data.put("systime", now);

        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);
        if (p == null) {
            resp.send(ErrCode.UNKONW_ERR, "token已失效,请重新登陆");
            return;
        }

        //SettleResult settle = settle(now, p);
        resp.data.put("income", p.getSettleResult().toJson());
        p.getSettleResult().clear();//清空
        resp.data.put("gold", p.getGold().get());
        resp.data.put("diamond", p.getDiamond().get());
        resp.data.put("vipLevel", p.getVipLevel());
        resp.data.put("vipExp", p.getVipExp());
        resp.data.put("exp", p.getExp());
        resp.data.put("level", p.getLevel());
        resp.data.put("shop_sell_speed",p.getShopSellSpeed());
        resp.data.put("windows",p.getWindows());
        resp.data.put("rubbishs",p.getShops().getRubbishs());

        QuestList list = QuestManager.getInstance().getQuestList(pid);
        if (list.isNeedUpdateCustomerQuests()) {
            resp.data.put("customerQuest", 1);
        }

        if (list.isNeedUpdateNpcQuest()) {
            resp.data.put("npcQuest", 1);
        }

        if(list.isNeedUpdateSaleQuest()){
            resp.data.put("saleQuest", 1);
        }
        // 在服务器压力大时,可降级,不处理聊天消息
        JSONObject chatMsg = MsgQ.getChatMsg(p.getMsgReadTime(),p.isFirstHeartBeat);
        p.isFirstHeartBeat = false;
        resp.data.put("chatList", chatMsg.getJSONArray("chatList"));
        resp.data.put("pid", pid);
        p.setMsgReadTime(chatMsg.getIntValue("maxTime"));

        resp.send(ErrCode.SUCC);
    }

    /**
     * 结算收益
     *
     * @param now
     * @param p
     *
     * @return 金币收益
     */
//    public SettleResult settle(int now, Player p) {
//        return new SettleResult();
//        if (!DataManager.players_goods_places.containsKey(p.getId())) {
//            log.debug("new user, no goods");
//            return new SettleResult();
//        }
//
//        // 计算上次结算到目前时刻整个时间段里应结算的次数，记录结算时间戳
//        int settleCount;
//        int today_settle_count ;
//        if (p.getSettleTime() == 0) {
//            settleCount = 1;
//            today_settle_count = 1 ;
//            // 货架为空也要记录结算时间戳
//            p.setSettleTime(now);
//        } else {
//            int today_time = TimeUtil.startOfDay();
//            int sell_speed = p.getShopSellSpeed();
//            settleCount = (now - p.getSettleTime()) / sell_speed;
//            if (settleCount <= 0) {
//                log.debug(p.getId() + ",settleCount<=0");
//                return new SettleResult();
//            }
//            p.setSettleTime(p.getSettleTime() + settleCount * sell_speed);
//
//            if (p.getSettleTime() < today_time){
//                today_settle_count = settleCount - ((today_time - p.getSettleTime()) / sell_speed);
//            }
//            else {
//                today_settle_count = settleCount;
//            }
//        }
//
////        JSONArray levelSellTypes = JSON.parseArray(DataManager.level_selltypes.getString(p.getLevel() + ""));
//
//        // 暂去掉 销售类别的判断 FIXME 临时注释
////        if (levelSellTypes.size() <= 0) {
////            log.debug(p.getId() + ",levelSellTypes.size()<=0");
////            return 0;
////        }
//
//        Map<Integer, JSONArray> player_goods_places = DataManager.players_goods_places.get(p.getId());
//        // 目前货架上共计在售的服饰数量
//        int sellingEquipCount = 0;
//        Set<Integer> sellingEquipTypes = player_goods_places.keySet();
//        for (int st : sellingEquipTypes) {
//            sellingEquipCount += player_goods_places.get(st).size();
//        }
//        if (sellingEquipCount <= 0) {
//            log.debug(p.getId() + ",sellingEquipCount<=0");
//            return new SettleResult();
//        }
//
//        JSONArray shelfs = p.getShops().getShelves();
//        // 实际结算次数
//        int loopCount = Math.min(sellingEquipCount, settleCount);
//        int not_today_settle_count = 0 ;
//        if (loopCount < settleCount - today_settle_count){
//            today_settle_count = 0 ;
//        }
//        else {
//            today_settle_count = loopCount - (settleCount - today_settle_count);
//        }
//        not_today_settle_count = loopCount - today_settle_count;
//
//        SettleResult settleResult = new SettleResult();
//
//        // 遍历售货次数
//        for (int i = 0; i < loopCount; i++) {
//            // 随机1种服饰类别
////            int sellType = levelSellTypes.getIntValue(RandomUtils.nextInt(levelSellTypes.size()));
////            JSONArray places = player_goods_places.get(sellType);
//
//            int sellType = -1;
//            for (int st : sellingEquipTypes) {
//                if (player_goods_places.get(st).size() > 0) {
//                    sellType = st;
//                }
//            }
//
//            if (sellType == -1) {
//                continue;
//            }
//            JSONArray places = player_goods_places.get(sellType);
//            if (places == null || places.size() == 0) {
//                // 没有该类货物在售
//                log.debug(p.getId() + "," + i + "," + sellType + ",no goods");
//                continue;
//            }
//            // 随1种该类别货物
//            int place = RandomUtils.nextInt(0, places.size());
//            // 货架编号,pos
//            String meta = places.getString(place);
//            String[] goodmeta = StrUtil.split(meta, ",");
//            int shelfid = Integer.parseInt(goodmeta[0]);
//            int pos = Integer.parseInt(goodmeta[1]);
//            JSONObject shelf = shelfs.getJSONObject(shelfid);
//            int status = shelf.getIntValue("status");
//            if (status == 0) {
//                log.debug("undecro," + p.getId() + "," + shelfid);
//                continue;
//            }
//            JSONArray goods = shelf.getJSONArray("goods");
//            String good = goods.getString(pos);
//            String[] split = good.split("-");
//            int equipid = Integer.parseInt(split[0]);
//            int equipnum = Integer.parseInt(split[1]);
//            // FIXME 要不要同步  暂时每次扣5件
//            int sell_num = 0 ;
//            if (equipnum <= 5) {
//                // 售罄
//                sell_num = equipnum;
//                good = "0";
//                // 维护货物位置信息
//                places.remove(meta);
//            } else {
//                // 扣1件
//                equipnum -= 5;
//                sell_num = 5;
//                good = equipid + "-" + equipnum;
//            }
//            // 保存货架信息
//            goods.set(pos, good);
//            // 记录收益
//            EquipItem equip = EquipConfig.getInstance().getItem(equipid);
//            int price = equip.getGoldsellprice() + ActivityManager.getInstance().getBuffShopAddPrice();
//            long goldsellprice = price * sell_num;
//            int sell_exp = equip.getSell_exp() * sell_num;
//            settleResult.add(shelfid, goldsellprice);
//            settleResult.addExp(sell_exp);
//            if (i + 1 > not_today_settle_count){
//                p.addTodaySell(goldsellprice);
//            }
//
//            log.debug(
//                "sell," + p.getId() + "," + sellType + "," + meta + "," + equipid + "," + equip.getName() +
//                equipnum +
//                "," + goldsellprice + "," +
//                sell_exp);
//        }
//
//        if (settleResult.totalGold > 0) {
//            p.addBags("settle", BagType.GOLD, 0, (int) settleResult.totalGold); // FIXME 可能因丢失精度而少加
//            p.addBags("settle", BagType.EXP, 0, settleResult.totalExp);
//        }

//        return settleResult;
//    }
}