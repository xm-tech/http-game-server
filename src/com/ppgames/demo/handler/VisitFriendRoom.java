package com.ppgames.demo.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.model.Player;

import java.util.Map;

public class VisitFriendRoom extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        long rid = req.data.getLongValue("rid");

        JSONObject object = new JSONObject();
        Player friend = Cache.players.get(rid);
        if (friend == null) {
            resp.send(ErrCode.UNKONW_ERR, "没有此玩家");
        }

        object.put("level", friend.getShops().getLevel());

        JSONObject decros = (JSONObject)friend.getShops().getDecros().clone();
        //为特殊家具的数据加入furnitureId.
        AddFunitureId(decros.getJSONObject("shelves"),       friend.getShops().getShelves());
        AddFunitureId(decros.getJSONObject("cashRegisters"), friend.getCashRegisters());
        AddFunitureId(decros.getJSONObject("flowerPots"),    friend.getFlowerPots());

        object.put("decros", decros);
        resp.data.put("room", object);
        resp.send(ErrCode.SUCC);
    }

    /**
     * (装修保存的数据中只存储了在各自分类下的id，
     * 而特殊家具(货架、收银台、花盆)对应的furnitureId家具Id是保存在另一个总的数据中的)
     * 在装修保存的数据中加入furnitureId
     * 遍历保存的数据，在总的数据中查找到Id一致的数据后取出其furnitureId并插入
     * @param savedData  保存的装修的数据
     * @param allData   所有的数据
     */
    void AddFunitureId(JSONObject savedData, JSONArray allData)
    {
        for (Map.Entry<String, Object> entry : savedData.entrySet()){
            JSONArray data = (JSONArray) entry.getValue();
            for (int i = 0; i < allData.size(); ++i){
                if (allData.getJSONObject(i).getIntValue("id") == data.getIntValue(0)){
                    data.add(allData.getJSONObject(i).getIntValue("fid"));
                }
            }

        }
    }

}
