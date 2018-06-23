package com.ppgames.demo.model.quest.task;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.demo.DataManager;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 2017/6/23.
 */
public class DressUpTask extends Task {
    protected int style;
    private int equipCount;
    private int npcId;

    public JSONObject checkFinish(List<Integer> equipsId) {
        JSONObject result = new JSONObject();

        List<Integer> dressEquips = new ArrayList<>();

        Player p = Cache.players.get(quest.getPid());


        for (int i = 0; i < equipsId.size(); ++i) {
            //如果玩家图鉴里没有此id,则跳过
            if (!p.getGotEquips().contains(equipsId.get(i))) {
                continue;
            }
            int type = DataManager.getEquipType(equipsId.get(i));//DataManager.equips.getJSONObject(equipsId.get(i) + "").getIntValue("type");
            if (dressEquips.contains(type)) {
                continue;
            } else {
                dressEquips.add(type);
            }
        }

        if (dressEquips.size() >= equipCount) {
            status = ETaskStatus.SUCCESS.ordinal();
            finishCallBack.onTaskSuccess(id);
            result.put("result", true);
        } else {
            status = ETaskStatus.FAILED.ordinal();
            finishCallBack.onTaskFailed(id);
            result.put("result", false);
            result.put("reason", "满足条件的衣服不足!");
        }

        return result;
    }

    public void setStyle(int value) {
        style = value;
    }

    public void setEquipCount(int equipCount) {
        this.equipCount = equipCount;
    }

    public void setNpcId(int npcId) {
        this.npcId = npcId;
    }
}
