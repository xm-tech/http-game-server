package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

// 解锁工厂可定制服饰(消耗图纸)
public class UnlockFactoryEquip extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int eid = req.data.getIntValue("eid");
        Player p = Cache.players.get(pid);
        int drawid = eid - 200000;
        if (p.getFactoryEquips().contains(eid)) {
            resp.send(ErrCode.UNKONW_ERR, "不可重复解锁");
            return;
        }


        int drawNum = p.getDraws().getIntValue(drawid + "");
        if (drawNum <= 0) {
            resp.send(ErrCode.UNKONW_ERR, "缺少图纸,解锁失败");
            return;
        }

        // 消耗图纸
        drawNum -= 1;
        if (drawNum == 0) {
            p.getDraws().remove(drawid + "");
        } else {
            p.getDraws().put(drawid + "", drawNum);
        }
        // 解锁(添加eid到可定制服饰集)
        p.getFactoryEquips().add(eid);

        resp.send(ErrCode.SUCC);
    }
}
