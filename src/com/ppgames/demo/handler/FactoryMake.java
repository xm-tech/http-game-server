package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.FactoryMakeConfig;
import com.ppgames.demo.config.item.FactoryMakeItem;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.model.quest.questEvent.QuestEventId;
import com.ppgames.util.TimeUtil;

// 工厂定制
public class FactoryMake extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);
        int eid = req.data.getIntValue("eid");
        int eNum = req.data.getIntValue("enum");

        if (eNum <= 0) {
            resp.send(ErrCode.UNKONW_ERR, "请选择定制数量");
            return;
        }

        if (!p.getFactoryEquips().contains(eid)) {
            resp.send(ErrCode.UNKONW_ERR, "尚未解锁该服饰");
            return;
        }

        //JSONObject buyConf = DataManager.factory_make.getJSONObject(eid + "");
        FactoryMakeItem buyConf = FactoryMakeConfig.getInstance().getItem(eid);
        int goldbuyprice = buyConf.getPrice();
        if (p.getGold().get() < goldbuyprice) {
            resp.send(ErrCode.GOLD_NOT_ENOUGH);
            return;
        }

        int fid = req.data.getIntValue("fid");

        JSONObject factory = p.getFactorys().getJSONObject(fid);
        int beginTime = factory.getIntValue("beginTime");
        JSONObject equip = factory.getJSONObject("equip");
        if (equip.size() > 0 || beginTime > 0) {
            resp.send(ErrCode.UNKONW_ERR, "该工厂正在使用中");
            return;
        }

        //向任务系统发送消息
        p.questEventListener.dispatchEvent(QuestEventId.FactoryMake, eNum);

        // 扣金币
        p.getGold().getAndAdd(-goldbuyprice);
        // 货物放入工厂
        equip.put(eid + "", eNum);
        factory.put("beginTime", TimeUtil.nowInt());
        resp.send(ErrCode.SUCC);
    }
}
