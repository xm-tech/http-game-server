package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.WindowConfig;
import com.ppgames.demo.config.item.WindowItem;
import com.ppgames.demo.model.Player;
import com.ppgames.util.BagType;
import com.ppgames.util.EDressStyle;

// 扩展(解锁)橱窗
public class UnlockWindow extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);
        int nwid = p.getWindows().size();

        //JSONObject winConf = DataManager.windows.getJSONObject(nwid + "");
        WindowItem winConf = WindowConfig.getInstance().getItem(nwid);
        if (winConf == null) {
            resp.send(ErrCode.UNKONW_ERR, "到扩展上限,扩展失败," + nwid);
            return;
        }
        int buylevel = winConf.getBuylevel();
        if (!p.enoughLevel(buylevel)) {
            resp.send(ErrCode.UNKONW_ERR, "扩展失败，需要店铺等级"+buylevel+"级");
            return;
        }

        int open_gold = winConf.getGold();
        int open_diamond = winConf.getDiamond();
        if (open_gold > 0) {
            if (!p.decrBags("UnlockWindow", BagType.GOLD, 0, -open_gold)) {
                resp.send(ErrCode.GOLD_NOT_ENOUGH);
                return;
            }
        } else if (open_diamond > 0) {
            if (!p.decrBags("UnlockWindow", BagType.GOLD, 0, -open_diamond)) {
                resp.send(ErrCode.DIAMOND_NOT_ENOUGH);
                return;
            }
        }

        // {"id":0, "equips":[]}
        JSONObject nwin = new JSONObject();
        nwin.put("id", nwid);
        nwin.put("equips", new JSONArray());
        nwin.put("styleid", EDressStyle.randomOneStyle());
        nwin.put("score",0 );
        p.getWindows().add(nwid, nwin);
        resp.data.put("window",nwin);
        resp.send(ErrCode.SUCC);
    }
}
