package com.ppgames.demo.model.quest.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.demo.DataManager;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.CustomerQuestConfig;
import com.ppgames.demo.config.item.QuestItems.CustomerQuestItem;
import com.ppgames.demo.model.Player;
import com.ppgames.util.BagType;
import com.ppgames.util.EDressStyle;
import com.ppgames.util.EEquip;
import com.ppgames.util.RandomUtil;

import java.util.Random;

/**
 * Created by Tony on 2017/6/21.
 */
public class PayEquipTask extends PayTask {
    /**
     * 服饰的风格
     */
    private int equipStyle;

    /**
     * 交付1件服饰给系统(删掉服饰)
     *
     * @param type
     * @param eid
     * @param num
     *
     * @return
     */
    @Override
    public boolean payItem(int type, int eid, int num) {
        if (status == ETaskStatus.IN_PROGRESS.ordinal()) {
            Player p = Cache.players.get(quest.getPid());
            boolean enough = p.bagEnough(BagType.EQUIP, eid, num);
            if (!enough) {
                return false;
            }
            int style = DataManager.getEquipStyle(eid);
            if (payType == type && equipStyle == style) {
                payedNum += num;
                if (payedNum >= payCount) {
                    missionComplete(eid);
                    return true;
                }
            }
            return false;
        }

        return false;
    }

    @Override
    public void init() {
        super.init();
    }
    public void initStyle(long questid){
        super.init();
        CustomerQuestItem conf = CustomerQuestConfig.getInstance().getItem((int)questid);
        JSONArray array = JSON.parseArray(conf.getBuy_random());
        if (array.size() == 0){
            Random random = new Random();
            payType = EEquip.values()[random.nextInt(EEquip.values().length - 3) + 1].value();
            equipStyle = EDressStyle.values()[random.nextInt(EDressStyle.values().length - 1)].value();
        }
        else {
            int randindex = RandomUtil.nextInt(0,array.size());
            JSONObject object = array.getJSONObject(randindex);
            payType = object.getInteger("type");
            equipStyle = object.getInteger("style");
        }

    }
    public int getEquipStyle() {
        return equipStyle;
    }
}
