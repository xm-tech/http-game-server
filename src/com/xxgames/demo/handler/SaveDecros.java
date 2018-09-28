package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;

import java.util.List;
import java.util.Set;

public class SaveDecros extends GameAct {

    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        JSONObject furnitures = JSON.parseObject(req.data.getString("furnitures"));
        String decros = req.data.getString("decros");
        Player p = Cache.players.get(pid);

        // 本次装修使用的货架ids
        JSONArray shelves = furnitures.getJSONArray("shelves");
        // 本次装修使用的普通家具ids
        JSONObject normals = furnitures.getJSONObject("normal");
        // 本次装修使用的收银台ids
        JSONArray cashRegisters = furnitures.getJSONArray("cashRegisters");
        // 本次装修使用的花盆ids
        JSONArray flowerPots = furnitures.getJSONArray("flowerPots");

        boolean normalFlag = checkNormalFurniture(p,normals);
        if (!normalFlag){
            resp.send(ErrCode.ITEM_NOT_ENOUGH,  "数量不足");
        }
        JSONArray shelfArr = p.getShops().getShelves();
        List<Integer> usedShlves = JSONArray.parseArray(JSON.toJSONString(shelves), Integer.class);

        boolean shlveFlag = checkShlves(p,usedShlves);
        if (!shlveFlag) {
            resp.send(ErrCode.ITEM_NOT_ENOUGH,  "货架上还有货物，不能收回");
        }

        p.setDecorateFurnitures(normals);
        // 货架

        for (int i = 0; i < shelfArr.size(); i++) {
            int status = shelfArr.getJSONObject(i).getInteger("status");
            boolean isUsed = usedShlves.contains(shelfArr.getJSONObject(i).getInteger("id"));
            if (status == 1 && !isUsed) {
                shelfArr.getJSONObject(i).put("status", 0);
            } else if (status == 0 && isUsed) {
                shelfArr.getJSONObject(i).put("status", 1);
                p.questEventListener.dispatchEvent(QuestEventId.UseShelf, 1);
            }
        }
        // 花盆
        for (int i = 0; i < p.getFlowerPots().size(); i++) {
            p.getFlowerPots().getJSONObject(i).put("status", 0);
        }
        for (int i = 0; i < flowerPots.size(); i++) {
            int flowPotId = flowerPots.getIntValue(i);
            p.getFlowerPots().getJSONObject(flowPotId).put("status", 1);
        }
        // 收银台
        for (int i = 0; i < p.getCashRegisters().size(); i++) {
            p.getCashRegisters().getJSONObject(i).put("status", 0);
        }
        for (int i = 0; i < cashRegisters.size(); i++) {
            int cashid = cashRegisters.getIntValue(i);
            p.getCashRegisters().getJSONObject(cashid).put("status", 1);
        }

        p.getShops().setDecros(JSON.parseObject(decros));

        resp.send(ErrCode.SUCC);
    }
    private boolean checkShlves(Player player ,List<Integer> usedShlves){
        JSONArray shelfArr = player.getShops().getShelves();
        for (int i = 0; i < shelfArr.size(); i++) {
            JSONObject shelf = shelfArr.getJSONObject(i);
            int status = shelf.getInteger("status");
            boolean isUsed = usedShlves.contains(shelf.getInteger("id"));
            if (status == 1 && !isUsed) {
                JSONArray goods = shelf.getJSONArray("goods");
                for (int j = 0 ;j < goods.size() ;j ++){
                    String posval = goods.getString(j);
                    if (posval.length() > 1 && !posval.equals("-1")) {
                        String[] split = posval.split("-");
                        int equipId = Integer.parseInt(split[0]);
                        int num = Integer.parseInt(split[1]);
                        if (num > 0) return false;
                    }
                }
            }
        }
        return true;
    }
    private boolean checkNormalFurniture(Player player ,JSONObject furniture ){
        Set<String> fids = furniture.keySet();
        for (String fid : fids) {
            // 本次使用的该家具数量
            int costNum = furniture.getIntValue(fid);
            // 背包里拥有的该家具总量
            int hasNum = player.getFurnitures().getIntValue(fid);
            if (hasNum < costNum) {
                return false;
            }
        }
        return true ;
    }

    private SaveDecros() {
        //no instance
        msgId = 3;
    }

    public static SaveDecros getInstance() {
        return SingletonHolder.saveDecros;
    }

    private static class SingletonHolder {
        private static SaveDecros saveDecros = new SaveDecros();
    }

}