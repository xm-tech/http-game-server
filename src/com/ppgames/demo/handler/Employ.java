package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.OfficeConfig;
import com.ppgames.demo.config.item.OfficeItem;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.utils.Const;
import com.ppgames.util.TimeUtil;

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
