package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.model.Player;

// 改名
public class Rename extends GameAct {

    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);
        String name = req.data.getString("newname");
        name = name.trim();

        if (DataManager.all_player_names.contains(name) && !p.getName().equals(name)) {
            resp.send(ErrCode.UNKONW_ERR, "角色名已被使用");
            return;
        }

        // 免费改名判断
        if (p.isRenameChance()) {
            p.setRenameChance(false);
        } else {
            //剩余钻石判断
            int rename_cost = SystemConfConfig.getInstance().getCfg().getPlayer_rename_diamond();
            boolean decrOk = p.decrDiamond("Rename", rename_cost);
            if (!decrOk) {
                resp.send(ErrCode.DIAMOND_NOT_ENOUGH, "钻石不足");
                return;
            }
        }

        p.setName(name);
        if (!DataManager.all_player_names.contains(name)) {
            DataManager.all_player_names.add(name);
        }

        resp.send(ErrCode.SUCC);
    }

}
