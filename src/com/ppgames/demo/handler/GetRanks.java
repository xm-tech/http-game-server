package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.cache.RankCache;
import com.ppgames.demo.model.Player;
import com.ppgames.demo.rank.RankManager;
import com.ppgames.demo.utils.Const;

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
