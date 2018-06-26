package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.GardenConfig;
import com.xxgames.demo.config.item.GardenItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;
import com.xxgames.util.BagAddResult;
import com.xxgames.util.BagType;
import com.xxgames.util.Data;
import com.xxgames.util.TimeUtil;


/**
 * 消耗金币
 * 每日上限5次
 * 每次购买产生 10 min CD
 * 购买奖励: 金苹果x1, 随机种子x1
 */
public class GardenLowBuy extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        int lastBuyTime = p.getGarden().getIntValue(0);
        int todayBuyNum = p.getGarden().getIntValue(1);
        int now = TimeUtil.nowInt();

        // 每天清空购买次数
        if (!TimeUtil.sameDay(lastBuyTime, now)) {
            todayBuyNum = 0;
        }
        GardenItem lowBuyConf = GardenConfig.getInstance().getItem(GardenConfig.FreeBuy);

        int confBuyNum = lowBuyConf.getBuyTime();
        if (todayBuyNum >= confBuyNum) {
            resp.send(ErrCode.UNKONW_ERR, "购买次数超过上限," + todayBuyNum);
            return;
        }
        // in cd time ?
        int confCdSeconds = lowBuyConf.getBuyCD();
        if (now - lastBuyTime < confCdSeconds) {
            resp.send(ErrCode.UNKONW_ERR, "cd 时间尚未结束,购买失败");
            return;
        }

        int gold = lowBuyConf.getGold();
        boolean decrOk = p.decrBags("GardenLowBuy", BagType.GOLD, 0, -gold);
        if (!decrOk) {
            resp.send(ErrCode.GOLD_NOT_ENOUGH);
            return;
        }

        // award
        JSONArray rewards = new JSONArray();
        // 固定金苹果奖励
        int apple = lowBuyConf.getApple();
        p.addBags("GardenLowBuy", BagType.APPLE, 0, apple);
        rewards.add(Data.createRewardObj(BagType.APPLE, 0, apple));

        // 随机种子奖励
        int itemid = lowBuyConf.getRandomGardenSeed();
        // 种子id
        BagAddResult bagAddResult = p.addBags("GardenLowBuy", BagType.ITEM, itemid, 1);
        rewards.add(bagAddResult.getAdded());

        //向任务系统发送购买种子的消息
        p.questEventListener.dispatchEvent(QuestEventId.BuySeed, 1);

        // 更新花园结构
        todayBuyNum += 1;
        lastBuyTime = now;
        p.getGarden().set(0, lastBuyTime);
        p.getGarden().set(1, todayBuyNum);

        resp.data.put("rewards", rewards);
        resp.send(ErrCode.SUCC);
    }
}
