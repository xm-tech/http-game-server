package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.GardenConfig;
import com.ppgames.demo.config.item.GardenItem;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.questEvent.QuestEventId;
import com.ppgames.util.BagAddResult;
import com.ppgames.util.BagType;
import com.ppgames.util.Data;
import com.ppgames.util.TimeUtil;

/**
 * 花园高级购买1次, 消耗钻石,无次数上限,无cd
 * 每日第1次免费
 * 奖励:金苹果x10,种子x1,服饰x1
 */
public class GardenHighBuy extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        int lastBuyTime = p.getGarden().getIntValue(2);
        int now = TimeUtil.nowInt();

        GardenItem highBuyConf = GardenConfig.getInstance().getItem(GardenConfig.BuyOne);
        // 1次奖励:金苹果x10,随机种子x1,服饰x1
        // 服饰奖励
        int equipid = highBuyConf.getRandomGardenEquip();
        BagAddResult bagAddResult = p.addBags("GardenHighBuy", BagType.EQUIP, equipid, 1);
        if (!bagAddResult.isSucc()) {
            resp.send(ErrCode.UNKONW_ERR, "背包空间已满,购买失败");
            return;
        }

        int needDiamond = 0;
        //每天免费购买一次
        if (TimeUtil.sameDay(lastBuyTime, now)) {
            needDiamond = highBuyConf.getDiamond();
        }
        boolean ok = p.decrDiamond("GardenHighBuy", needDiamond);
        if (!ok) {
            resp.send(ErrCode.DIAMOND_NOT_ENOUGH, "钻石不够");
            return;
        }

        //给客户端发送的服装奖励信息
        JSONArray rewards = new JSONArray();
        rewards.add(bagAddResult.getAdded());

        // 固定金苹果奖励
        int apple = highBuyConf.getApple();
        p.addBags("GardenHighBuy", BagType.APPLE, 0, apple);
        rewards.add(Data.createRewardObj(BagType.APPLE, 0, apple));

        // 随机种子(道具)奖励
        int itemid = highBuyConf.getRandomGardenSeed();
        BagAddResult gardenHighBuy = p.addBags("GardenHighBuy", BagType.ITEM, itemid, 1);
        // 给客户端发送的道具奖励信息
        rewards.add(gardenHighBuy.getAdded());

        //向任务系统发送购买种子的消息
        p.questEventListener.dispatchEvent(QuestEventId.BuySeed, 1);

        // 更新高级购买1次的时间戳
        p.getGarden().set(2, now);

        resp.data.put("rewards", rewards);
        resp.send(ErrCode.SUCC);
    }
}
