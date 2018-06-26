package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;

// 换装
public class FaceChange extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        // 妆容id集合
        JSONArray logo = JSON.parseArray(req.data.getString("logo"));
        // 判断是否有这些妆容
        for (int i = 0; i < logo.size(); i++) {
            String faceid = logo.getString(i);
            if (!p.getFaces().containsKey(faceid)) {
                resp.send(ErrCode.UNKONW_ERR, "妆容[" + faceid + "]不存在，换装失败");
                return;
            }
        }
        //向任务系统发送消息
        p.questEventListener.dispatchEvent(QuestEventId.ChangeHeent, 1);

        p.setLogo(logo);
        resp.send(ErrCode.SUCC);
    }
}
