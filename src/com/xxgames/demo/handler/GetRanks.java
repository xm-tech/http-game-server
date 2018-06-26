package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.cache.RankCache;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.rank.RankManager;
import com.xxgames.demo.utils.Const;

public class GetRanks extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int type = req.data.getIntValue("type");
        Player p = Cache.players.get(pid);

        JSONObject ret = new JSONObject();
        ret.put("type", type);

        switch (type) {
        case Const.RANK_TYPE_PVP:
            // 搭配榜
            ret.put("rank", RankCache.getPvpranks());
            ret.put("me", p.getPvp().getRank().get());
            ret.put("score", p.getLevel());
            break;
        case Const.RANK_TYPE_TODAY_SELL:
            // 当日营业额
            ret.put("rank", RankManager.getInstance().getTodaySellRanks());
            ret.put("me", p.getSellRank());
            ret.put("score", p.getTodaySell());
            break;
        case Const.RANK_TYPE_CHARM:
            // 总魅力榜
            ret.put("rank", RankManager.getInstance().getCharmRanks());
            ret.put("me", p.getCharmRank());
            ret.put("score", p.getCharm());
            break;
        }

        resp.data.putAll(ret);
        resp.send(ErrCode.SUCC);

    }
}
