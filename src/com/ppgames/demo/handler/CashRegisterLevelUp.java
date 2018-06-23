package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.CashRegisterLevelConfig;
import com.ppgames.demo.config.item.CashRegisterLevelItem;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.questEvent.QuestEventId;

public class CashRegisterLevelUp extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int cid = req.data.getIntValue("cid");
        Player p = Cache.players.get(pid);

        JSONObject cashRegister = p.getCashRegisters().getJSONObject(cid);
        int fid = cashRegister.getIntValue("fid");// t_cashregister_level->id
        int level = cashRegister.getIntValue("level");
        int levelId = fid * 100 + level;
        CashRegisterLevelItem levelConf = CashRegisterLevelConfig.getInstance().getItem(levelId);

        int needLevel = levelConf.getNeed_level();
        if (needLevel == 0) {
            resp.send(ErrCode.UNKONW_ERR, "已最高级，升级失败");
            return;
        }
        if (!p.enoughLevel(needLevel)) {
            resp.send(ErrCode.UNKONW_ERR, "升级失败，需要店铺等级"+needLevel+"级");
            return;
        }

        int status = cashRegister.getIntValue("status");
        if (status == 0) {
            resp.send(ErrCode.UNKONW_ERR, "未装修的收银台，不可使用");
            return;
        }

        int gold = levelConf.getGold();
        if (p.getGold().get() < gold) {
            resp.send(ErrCode.GOLD_NOT_ENOUGH, "金币不足");
            return;
        }

        p.questEventListener.dispatchEvent(QuestEventId.CashregisterLevel, level + 1);

        p.getGold().getAndAdd(-gold);
        cashRegister.put("level", ++level);
        resp.send(ErrCode.SUCC);
    }
}
