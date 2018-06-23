package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.ShelfLevel;
import com.ppgames.demo.config.config.ShelLevelConfig;
import com.ppgames.demo.model.Player;

// 货架升级就是 t_shelf_level->id+1
public class ExpandShelf extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int shelfidx = req.data.getIntValue("shelfidx");
        Player p = Cache.players.get(pid);
        JSONArray shelfs = p.getShops().getShelves();
        JSONObject shelf = shelfs.getJSONObject(shelfidx);

        int status = shelf.getIntValue("status");
        if (status == 0) {
            resp.send(ErrCode.UNKONW_ERR, "未装修的货架，不可使用");
            return;
        }

        int level = shelf.getIntValue("level");
        if (level >= 10) {
            resp.send(ErrCode.UNKONW_ERR, "货架已是最高等级，扩展失败");
            return;
        }

        // 计算升级需要的金币
        int shelfid = shelf.getIntValue("fid");// t_shelf->id
        int newLevelId = (shelfid * 100) + level;
        //JSONObject newShelfLevelConf = DataManager.shelf_levels.getJSONObject(newLevelId + "");
        ShelfLevel shelfLevelConfig = ShelLevelConfig.getInstance().getItem(newLevelId);
        int gold = shelfLevelConfig.getGold();
        if (p.getGold().get() < gold) {
            resp.send(ErrCode.GOLD_NOT_ENOUGH, "金币不足");
            return;
        }
        p.getGold().getAndAdd(-gold);
        shelf.put("level", ++level);
        resp.send(ErrCode.SUCC);
    }
}
