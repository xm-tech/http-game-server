package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.DataManager;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;
import com.ppgames.util.StrUtil;

// 创建角色、捏脸、起名、改名
public class CreateRole extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);
        String name = req.data.getString("name");
        // 妆容id集合
        String logo = req.data.getString("logo");

        if (StrUtil.isEmpty(name)) {
            resp.send(ErrCode.UNKONW_ERR, "角色名不能为空");
            return;
        }
        name = name.trim();

        if (DataManager.all_player_names.contains(name) && !p.getName().equals(name)) {
            resp.send(ErrCode.UNKONW_ERR, "角色名已被使用");
            return;
        }

        if (logo != null) {
            // 捏脸
            JSONArray logos = JSON.parseArray(logo);
            for (int i = 0; i < logos.size(); i++) {
                int faceid = logos.getIntValue(i);
                if (!p.getFaces().containsKey(faceid + "")) {
                    resp.send(ErrCode.UNKONW_ERR, "不存在的妆容:" + faceid);
                    return;
                }
            }
            p.setLogo(logos);
        } else {
            p.setLogo(new JSONArray());
        }

        // 起名、改名
        p.setName(name);
        if (!DataManager.all_player_names.contains(name)) {
            DataManager.all_player_names.add(name);
        }

        resp.send(ErrCode.SUCC);
    }
}
