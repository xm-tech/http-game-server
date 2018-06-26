package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.FaceConfig;
import com.xxgames.demo.config.item.FaceItem;
import com.xxgames.demo.model.Player;

// 消耗本类妆容碎片合成妆容
public class FaceCombine extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        String faceid = req.data.getString("faceid");
        if (p.getFaces().containsKey(faceid + "")) {
            resp.send(ErrCode.UNKONW_ERR, "不可重复合成");
            return;
        }

        // 判断碎片数量是否足够
        if (!p.getFaceFrags().containsKey(faceid)) {
            resp.send(ErrCode.UNKONW_ERR, "没有碎片");
            return;
        }

        int fragNum = p.getFaceFrags().getIntValue(faceid);
        FaceItem faceConf = FaceConfig.getInstance().getItem(Integer.parseInt(faceid));
        int combineFrag = faceConf.getCombine_frag();
        if (fragNum < combineFrag) {
            resp.send(ErrCode.UNKONW_ERR, "碎片数量不够");
            return;
        }

        // 消耗碎片
        fragNum -= combineFrag;
        p.getFaceFrags().put(faceid, fragNum);
        // 创建妆容
        JSONArray val = new JSONArray();
        val.add(1); // level
        val.add(0); // exp
        p.getFaces().put(faceid, val);

        resp.send(ErrCode.SUCC);
    }
}
