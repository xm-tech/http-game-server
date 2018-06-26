package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.GardenConfig;
import com.xxgames.demo.config.item.GardenItem;
import com.xxgames.demo.config.item.PropItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.mail.Mail;
import com.xxgames.demo.model.mail.MailManager;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;
import com.xxgames.util.BagType;

import java.util.ArrayList;

/**
 * 消耗钻石,无次数限制,无cd
 * 奖励: 金苹果x100,种子x10(至少1个3级以上种子), 服饰x10, 奖励合并
 */
public class GardenHighBuy10 extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int needMail = req.data.getInteger("needMail");

        Player p = Cache.players.get(pid);

        GardenItem high10BuyConf = GardenConfig.getInstance().getItem(GardenConfig.BuyTen);

        int needDiamond = high10BuyConf.getDiamond();
        boolean diamondEnough = p.bagEnough(BagType.DIAMOND, 0, needDiamond);
        if (!diamondEnough ) {
            resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
            return;
        }

        // 服饰x10
        boolean enoughEquipSize = p.enoughEquipSize(10);
        if (!enoughEquipSize && needMail != 1) {
            resp.send(ErrCode.UNKONW_ERR, "服饰容量不足,继续购买将以邮件的形式发放奖励，是否继续");
            return;
        }

        p.decrBags("GardenHighBuy10", BagType.DIAMOND, 0, -needDiamond);

        // 1次奖励:金苹果x10,随机种子x1,服饰x1

        //JSONArray rewards = new JSONArray();//客户端奖励数组
        ArrayList<PropItem> rewards = new ArrayList<PropItem>();
        for (int i = 0; i< 10; i++){
            int eid = high10BuyConf.getRandomGardenEquip();
            rewards.add(new PropItem(BagType.EQUIP, eid, 1));
           // rewards.add(Data.createRewardObj(BagType.EQUIP, eid, 1));
        }
        // 固定金苹果奖励
        int apple = high10BuyConf.getApple();//hign10BuyConf.getIntValue("apple");
//        rewards.add(Data.createRewardObj(BagType.APPLE, 0, apple));
        rewards.add(new PropItem(BagType.APPLE, 0, apple));
        // 只少1个7星以上种子

        Boolean sevenStarSeed = false;
        for(int i=0; i<9; i++){
            int itemid = high10BuyConf.getRandomGardenSeed();
            rewards.add(new PropItem(BagType.ITEM, itemid, 1));
            if(itemid == GardenConfig.SevenStarSeedID)
                sevenStarSeed = true;
        }
        if(sevenStarSeed){
            rewards.add(new PropItem(BagType.ITEM, high10BuyConf.getRandomGardenSeed(), 1));
        } else{
            rewards.add(new PropItem(BagType.ITEM, GardenConfig.SevenStarSeedID, 1));
        }
        if (needMail == 1){
            MailManager.getInstance().insertMail(p.getId(),0,"系统","十连抽","xxxx",rewards, Mail.MTYPE_SYSTEM);
        }
        else {
            // 发奖
            p.addBags("GardenHighBuy10", rewards);
        }


        //向任务系统发送购买种子的消息
        p.questEventListener.dispatchEvent(QuestEventId.BuySeed, 10);

        resp.data.put("rewards", rewards);
        resp.send(ErrCode.SUCC);
    }
}
