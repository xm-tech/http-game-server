package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.FactoryConfig;
import com.xxgames.demo.config.item.FactoryItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;

public class AddFactory extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        int newid = p.getFactorys().size();
        FactoryItem conf = FactoryConfig.getInstance().getItem(newid + 1);
        if (conf == null) {
            resp.send(ErrCode.UNKONW_ERR, "已到工厂数量上限,扩充失败");
            return;
        }

        int open_type = conf.getOpen_type();
        if (open_type == 1) {
            // 指定等级开启
            if (!p.enoughLevel(conf.getOpen_para())) {
                resp.send(ErrCode.UNKONW_ERR, "扩充失败，需要店铺等级"+conf.getOpen_para()+"级");
                return;
            }
        }

        if (conf.getGold() > 0) {
            boolean decrOk = p.decrGold("AddFactory", conf.getGold());
            if (!decrOk) {
                resp.send(ErrCode.GOLD_NOT_ENOUGH);
                return;
            }
        } else if (conf.getDiamond() > 0) {
            boolean decrOk = p.decrDiamond("AddFactory", conf.getDiamond());
            if (!decrOk) {
                resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
                return;
            }
        }

        // 添加新工厂
        p.getFactorys().add(DataManager.createOneFactory(newid));
        p.questEventListener.dispatchEvent(QuestEventId.FactorNum, p.getFactorys().size());
        resp.data.put("fid", newid);
        resp.send(ErrCode.SUCC);
    }
}
