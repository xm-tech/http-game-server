package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.EquipConfig;
import com.xxgames.demo.config.item.EquipItem;
import com.xxgames.util.AllInGotEquips;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;

// 橱窗模特换装
public class ChangeWindowEquips extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        int wid = req.data.getIntValue("wid");
        Player p = Cache.players.get(pid);

        if (wid >= p.getWindows().size()) {
            resp.send(ErrCode.UNKONW_ERR, "不存在的模特," + wid);
            return;
        }

        JSONArray equipids = JSON.parseArray(req.data.getString("equipids"));
        AllInGotEquips aie = new AllInGotEquips(equipids, p.getGotEquips());

        if (!aie.isIn()) {
            EquipItem equipItem = EquipConfig.getInstance().getItem(aie.getNotInId());
            resp.send(ErrCode.UNKONW_ERR, equipItem.getName() + " 图鉴尚未收藏");
            return;
        }

        // 判断别的模特是否已穿对应的服饰
        for (int i = 0; i < p.getWindows().size(); i++) {
            JSONObject window = p.getWindows().getJSONObject(i);
            int id = window.getIntValue("id");
            if (id == wid) {
                continue;
            }
            JSONArray equips = window.getJSONArray("equips");
            for (int j = 0; j < equipids.size(); j++) {
                int eid = equipids.getIntValue(j);
                EquipItem equipItem = EquipConfig.getInstance().getItem(eid);
                if (equips.contains(eid)) {
                    resp.send(ErrCode.UNKONW_ERR, "服饰[" + equipItem.getName() + "]已被别的模特[" + id + "]使用");
                    return;
                }
            }
        }
        //向任务系统发送消息
        p.questEventListener.dispatchEvent(QuestEventId.ChangeWindowDress, 1);

        JSONObject window = p.getWindows().getJSONObject(wid);
        window.put("equips", equipids);
        window.put("score",getWindowsScore(window));
        resp.data.put("window",window);
        resp.send(ErrCode.SUCC);
    }
    private int getWindowsScore(JSONObject window){
        int score = 0 ;
        int styleid = window.getInteger("styleid");
        JSONArray equips = window.getJSONArray("equips");
        for (int i = 0 ; i < equips.size(); i ++){
            int equipid = equips.getIntValue(i);
            EquipItem equipItem = EquipConfig.getInstance().getItem(equipid);
            int equipStyle = equipItem.getStyleid();
            score += equipItem.getFootfall().get(styleid == equipStyle ? 1 : 0);
        }
        return score ;
    }
}
