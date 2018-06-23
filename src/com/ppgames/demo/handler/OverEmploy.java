package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.OfficeConfig;
import com.ppgames.demo.config.item.OfficeItem;
import com.ppgames.demo.model.Player;
import com.ppgames.util.TimeUtil;

public class OverEmploy extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int eid = req.data.getIntValue("eid");
        Player p = Cache.players.get(pid);

        OfficeItem employeeConf = OfficeConfig.getInstance().getItem(eid);
        // 持续时间(秒)
        int needTime = employeeConf.getTime();
        int beginTime = p.getEmployees().getIntValue(eid + "");
        int now = TimeUtil.nowInt();

        if (now - beginTime < needTime) {
            resp.send(ErrCode.UNKONW_ERR, "未到结束时间," + eid);
            return;
        }

        // 结束,删除结构
        p.getEmployees().remove(eid + "");
        resp.send(ErrCode.SUCC);
    }
}
