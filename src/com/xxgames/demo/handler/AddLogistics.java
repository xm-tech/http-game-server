package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.LogisticsConfig;
import com.xxgames.demo.config.item.LogisticsItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;

public class AddLogistics extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        int newLid = p.getLogistics().size();
        LogisticsItem config = LogisticsConfig.getInstance().getItem(newLid + 1);
        if(config == null){
            resp.send(ErrCode.UNKONW_ERR, "已到物流数量上限,扩充失败");
            return;
        }

        int open_type = config.getOpen_type();
        if (open_type == 1) {
            // 指定等级开启
            if (!p.enoughLevel(config.getOpen_para())) {
                resp.send(ErrCode.UNKONW_ERR, "扩充失败，需要店铺等级"+config.getOpen_para()+"级");
                return;
            }
        }

        if (config.getGold() > 0) {
            boolean decrOk = p.decrGold("AddLogistics", config.getGold());
            if (!decrOk) {
                resp.send(ErrCode.GOLD_NOT_ENOUGH);
                return;
            }

        } else if (config.getDiamond() > 0) {
            boolean decrOk = p.decrDiamond("AddLogistics", config.getDiamond());
            if (!decrOk) {
                resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
                return;
            }
        }

        // 添加新物流
        p.getLogistics().add(DataManager.createOneLogistics(newLid));
        p.questEventListener.dispatchEvent(QuestEventId.LogisticsNum, p.getLogistics().size());

        resp.data.put("lid", newLid);
        resp.send(ErrCode.SUCC);
    }
}
