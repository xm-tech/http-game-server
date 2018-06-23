package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.EquipsCapacityExpandConfig;
import com.ppgames.demo.model.Player;

public class ExpandEquipsCapacity extends GameAct {

    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);
        int num = req.data.getIntValue("num");

        if (num <= 0) {
            resp.send(ErrCode.UNKONW_ERR, "扩展失败,num=" + num);
            return;
        }

        if (p.getEquipsCapacity() + num > EquipsCapacityExpandConfig.getInstance().getCapacityMax()) {
            resp.send(ErrCode.UNKONW_ERR, "到扩展上限,扩展失败");
            return;
        }

        int goldCost = 0;
        int diamondCost = 0;
        for(int index = p.getEquipsCapacity(); index < p.getEquipsCapacity() + num; index++){
            goldCost += EquipsCapacityExpandConfig.getInstance().getItem(index).getGoldbuyprice();
            diamondCost += EquipsCapacityExpandConfig.getInstance().getItem(index).getDiamondbuyprice();
        }
        if (p.getGold().get() < goldCost) {
            resp.send(ErrCode.GOLD_NOT_ENOUGH);
            return;
        }

        if(p.getDiamond().get() < diamondCost){
            resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
            return;
        }

        p.decrGold("ExpandEquipsCapacity", goldCost);
        p.decrDiamond("ExpandEquipsCapacity", diamondCost);
        p.setEquipsCapacity(p.getEquipsCapacity() + num);
        resp.send(ErrCode.SUCC);
    }
}
