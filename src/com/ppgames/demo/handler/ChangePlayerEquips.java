package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.EquipConfig;
import com.ppgames.demo.config.item.EquipItem;
import com.ppgames.util.AllInGotEquips;
import com.ppgames.demo.model.Player;

// 主角换装(试衣间换装)
public class ChangePlayerEquips extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        JSONArray equipids = JSON.parseArray(req.data.getString("equipids"));
        AllInGotEquips aie = new AllInGotEquips(equipids, p.getGotEquips());

        if (!aie.isIn()) {
            EquipItem equipItem = EquipConfig.getInstance().getItem(aie.getNotInId());
            resp.send(ErrCode.UNKONW_ERR, equipItem.getName() + " 图鉴尚未收藏");
            return;
        }

        p.getDressRoom().put("equips", equipids);
        resp.send(ErrCode.SUCC);
    }
}
