package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.FaceConfig;
import com.xxgames.demo.config.item.FaceItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.utils.Const;

import java.util.ArrayList;
import java.util.Set;

public class FaceLevelUp extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);

        String faceid = req.data.getString("faceid");
        if (!p.getFaces().containsKey(faceid + "")) {
            resp.send(ErrCode.UNKONW_ERR, "妆容不存在，升级失败");
            return;
        }
        // 判断是否升级
        FaceItem faceConf = FaceConfig.getInstance().getItem(Integer.parseInt(faceid));
        if (faceConf == null) {
            resp.send(ErrCode.UNKONW_ERR, "配置错误，升级失败");
            return;
        }
        ArrayList<Integer> levelUpExps = faceConf.getExp();
        int max_exp = levelUpExps.get(Const.MAX_FACE_LEVEL - 1 - 1);

        // 消耗碎片升级
        JSONArray val = p.getFaces().getJSONArray(faceid);
        int level = val.getIntValue(0);
        if (level >= 3) {
            resp.send(ErrCode.UNKONW_ERR, "妆容已最高级，升级失败");
            return;
        }
        JSONObject frags = JSON.parseObject(req.data.getString("frags"));

        int exp = val.getIntValue(1);
        Set<String> fragTypes = frags.keySet();
        for (String fragType : fragTypes) {
            // 判断该类别碎片是否存在
            if (!p.getFaceFrags().containsKey(fragType)) {
                resp.send(ErrCode.UNKONW_ERR, "不存在的碎片，升级失败");
                return;
            }
            // 判断碎片数量是否足够
            int needNum = frags.getIntValue(fragType);
            int hasNum = p.getFaceFrags().getIntValue(fragType);
            if (hasNum < needNum) {
                resp.send(ErrCode.UNKONW_ERR, "碎片数量不足，升级失败");
                return;
            }
            // 计算每种碎片增加的经验
            int oneAddExp = FaceConfig.getInstance().getItem(Integer.parseInt(fragType)).getAdd_exp();
            int addExp = needNum * oneAddExp;
            if (exp + addExp > max_exp){
                break;
            }
            exp += addExp;
        }

        // 扣碎片 FIXME 性能
        for (String fragType : fragTypes) {
            int needNum = frags.getIntValue(fragType);
            int hasNum = p.getFaceFrags().getIntValue(fragType);
            int leftNum = hasNum - needNum;
            if (leftNum <= 0) {
                p.getFaceFrags().remove(fragType);
            } else {
                p.getFaceFrags().put(fragType, leftNum);
            }
        }

        while (exp > 0 && level < 3) {
            int needExp = levelUpExps.get(level - 1);
            if(exp < needExp) break;
            exp -= needExp;
            level += 1;
        }
        val.set(0, level);
        val.set(1, exp);

        resp.data.put("level", level);
        resp.data.put("exp", exp);
        resp.send(ErrCode.SUCC);
    }
}
