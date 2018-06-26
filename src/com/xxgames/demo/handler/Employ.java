package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.OfficeConfig;
import com.xxgames.demo.config.item.OfficeItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.utils.Const;
import com.xxgames.util.TimeUtil;

public class Employ extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int eid = req.data.getIntValue("eid");
        Player p = Cache.players.get(pid);

        if (p.getEmployees().containsKey(eid + "")) {
            resp.send(ErrCode.UNKONW_ERR, "雇员正在忙,雇佣失败," + eid);
            return;
        }

        OfficeItem employeeConf = OfficeConfig.getInstance().getItem(eid);
        if (!p.enoughLevel(employeeConf.getNeed_level())){
            resp.send(ErrCode.UNKONW_ERR, "雇佣失败，需要店铺等级"+employeeConf.getNeed_level()+"级");
            return ;
        }
        long gold = employeeConf.getGold();
        if (p.getGold().get() < gold) {
            resp.send(ErrCode.GOLD_NOT_ENOUGH);
            return;
        }
        p.getGold().getAndAdd(-gold);
        if (employeeConf.getType() == Const.EMPLOYEE_TYPE_BAOJIE){
            p.getShops().setRubbishs(0);
        }

        p.getEmployees().put(eid + "", TimeUtil.nowInt());
        resp.send(ErrCode.SUCC);
    }
}
