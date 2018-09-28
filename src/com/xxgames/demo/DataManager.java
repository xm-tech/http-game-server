package com.xxgames.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.ActiveRewardsConfig;
import com.xxgames.demo.config.config.ActiveValueQuestConfig;
import com.xxgames.demo.config.config.BlueprintConfig;
import com.xxgames.demo.config.config.CashRegisterLevelConfig;
import com.xxgames.demo.config.config.CheckInChestConfig;
import com.xxgames.demo.config.config.CheckInConfig;
import com.xxgames.demo.config.config.CustomerQuestConfig;
import com.xxgames.demo.config.config.DreamCrystalStoreConfig;
import com.xxgames.demo.config.config.DressRoomLevelConfig;
import com.xxgames.demo.config.config.EquipConfig;
import com.xxgames.demo.config.config.EquipsCapacityExpandConfig;
import com.xxgames.demo.config.config.FaceConfig;
import com.xxgames.demo.config.config.FactoryConfig;
import com.xxgames.demo.config.config.FactoryLevelConfig;
import com.xxgames.demo.config.config.FactoryMakeConfig;
import com.xxgames.demo.config.config.FurnitureConfig;
import com.xxgames.demo.config.config.GardenConfig;
import com.xxgames.demo.config.config.GardenStoreConfig;
import com.xxgames.demo.config.config.GoodsBoxItemConfig;
import com.xxgames.demo.config.config.ItemConfig;
import com.xxgames.demo.config.config.LogisticsConfig;
import com.xxgames.demo.config.config.LogisticsLevelConfig;
import com.xxgames.demo.config.config.MainLineQuestConfig;
import com.xxgames.demo.config.config.NpcQuestConfig;
import com.xxgames.demo.config.config.OfficeConfig;
import com.xxgames.demo.config.config.PlayerLevelConfig;
import com.xxgames.demo.config.config.RankListPvpConfig;
import com.xxgames.demo.config.config.RankListSaleConfig;
import com.xxgames.demo.config.config.RobotConfig;
import com.xxgames.demo.config.config.SaleQuestConfig;
import com.xxgames.demo.config.config.ShelLevelConfig;
import com.xxgames.demo.config.config.ShelfConfig;
import com.xxgames.demo.config.config.ShopExpandConfig;
import com.xxgames.demo.config.config.StampStoreConfig;
import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.config.config.SystemShopConfig;
import com.xxgames.demo.config.config.WholeSaleConfig;
import com.xxgames.demo.config.config.WindowConfig;
import com.xxgames.demo.config.item.ShelfItem;
import com.xxgames.demo.dao.PlayerDao;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.battle.PvpRanks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class DataManager {
    /**
     * 玩家货物的位置表<pid,<equiptype,["货架编号,pos","货架编号,pos"]>
     */
    public static Map<Long, ConcurrentHashMap<Integer, JSONArray>> players_goods_places = new HashMap<>();
    /**
     * 玩家背包中服饰类别和服饰的映射关系 <pid,<equiptype,[equipid order by 利润 desc]>>
     */
    public static Map<Long, Map<Integer, ArrayList<Integer>>> player_equiptype_maps = new HashMap<>();

    public static Map<Long, Long> tokens = new ConcurrentHashMap<>();
    public static List<String> all_player_names = new ArrayList<>();

    /**
     * pvp 部位对应pk结果数组中的索引
     */
    public static Map<Integer, Integer> pvp_part_index = new HashMap<>();


    public static void init() throws Exception {

        EquipConfig.getInstance().InitConfig("t_equip.json");
        FurnitureConfig.getInstance().InitConfig("t_furniture.json");
        ItemConfig.getInstance().InitConfig("t_item.json");

        SystemConfConfig.getInstance().InitConfig("t_sysconf.json");
        ShelfConfig.getInstance().InitConfig("t_shelf.json");
        ShelLevelConfig.getInstance().InitConfig("t_shelf_level.json");

        EquipsCapacityExpandConfig.getInstance().InitConfig("t_equips_capacity_expand.json");
        WholeSaleConfig.getInstance().InitConfig("t_wholesale_market.json");
        LogisticsConfig.getInstance().InitConfig("t_logistics.json");
        LogisticsLevelConfig.getInstance().InitConfig("t_logistics_level.json");

        FaceConfig.getInstance().InitConfig("t_face.json");
        DressRoomLevelConfig.getInstance().InitConfig("t_dressroom_level.json");
        WindowConfig.getInstance().InitConfig("t_window.json");
        PlayerLevelConfig.getInstance().InitConfig("t_player_level.json");

        GardenConfig.getInstance().InitConfig("t_garden.json");
        GardenStoreConfig.getInstance().InitConfig("t_garden_store.json");
        FactoryConfig.getInstance().InitConfig("t_factory.json");
        FactoryLevelConfig.getInstance().InitConfig("t_factory_level.json");
        FactoryMakeConfig.getInstance().InitConfig("t_factory_make.json");

        CashRegisterLevelConfig.getInstance().InitConfig("t_cashregister_level.json");
        OfficeConfig.getInstance().InitConfig("t_office.json");
        SystemShopConfig.getInstance().InitConfig("t_system_shop.json");
        ShopExpandConfig.getInstance().InitConfig("t_shop_expand.json");

        CheckInConfig.getInstance().InitConfig("t_check_in.json");
        CheckInChestConfig.getInstance().InitConfig("t_check_in_chest.json");
        MainLineQuestConfig.getInstance().InitConfig("t_main_line.json");
        ActiveValueQuestConfig.getInstance().InitConfig("t_active_value.json");
        CustomerQuestConfig.getInstance().InitConfig("t_customer_quest.json");
        NpcQuestConfig.getInstance().InitConfig("t_npc_quest.json");
        SaleQuestConfig.getInstance().InitConfig("t_sale_quest.json");

        ActiveRewardsConfig.getInstance().InitConfig("t_active_rewards.json");
        RankListPvpConfig.getInstance().InitConfig("t_ranking_list_pvp.json");
        RankListSaleConfig.getInstance().InitConfig("t_ranking_list_sale.json");
        GoodsBoxItemConfig.getInstance().InitConfig("t_goods_box.json");

        RobotConfig.getInstance().InitConfig("t_robot.json");
        StampStoreConfig.getInstance().InitConfig("t_stamp_store.json");
        DreamCrystalStoreConfig.getInstance().InitConfig("t_dream_crystal_store.json");
        BlueprintConfig.getInstance().InitConfig("t_blueprint.json");

        int[] parts = { 8, 3, 4, 5, 6, 12, 7, 9, 10, 13 };
        for (int i = 0; i < parts.length; i++) {
            pvp_part_index.put(parts[i], i);
        }
        //System.out.println("active_value_quest:" + active_value_quest);
        all_player_names = PlayerDao.getInstance().getAllNames();
        Cache.rebuild();
        PvpRanks.rebuild();

//        createOneShelf(2001, 0);
//        createOneShelf(2002, 1);
//        createOneFlowPot(3007, 0);
//        createOneFlowPot(3008, 1);
//        createOneCashRegister(1001, 0);

//        Player p = new Player();
        JSONArray bags = new JSONArray();

        JSONArray item0 = new JSONArray();
        item0.add(0);
        item0.add(1);
        item0.add(2);

        JSONArray item1 = new JSONArray();
        item1.add(0);
        item1.add(2);
        item1.add(10);

        JSONArray gold = new JSONArray();
        gold.add(4);
        gold.add(-1);
        gold.add(1000);

        JSONArray diamond = new JSONArray();
        diamond.add(5);
        diamond.add(-1);
        diamond.add(1000);

        JSONArray faceFrags = new JSONArray();
        faceFrags.add(7);
        faceFrags.add(750001);
        faceFrags.add(5);

        bags.add(item0);
        bags.add(item1);
        bags.add(gold);
        bags.add(diamond);
        bags.add(faceFrags);

//        JSONObject addBags = p.addBags(bags);
//
//        System.out.println(addBags.toJSONString());

        //JSONObject oneFaceConf = faces.getJSONObject("750002");
        //FaceItem oneFaceConf = FaceConfig.getInstance().getItem(750002);
        //JSONObject buff = JSON.parseObject(oneFaceConf.getString("buff"));
        //JSONArray buffArr0 = buff.getJSONArray("1");
        //JSONArray buffArr1 = buff.getJSONArray("12");
        //System.out.println("oneFaceConf: " + oneFaceConf.toJSONString());
        //System.out.println("buffArr0: " + buffArr0);
        //System.out.println("buffArr1: " + buffArr1);


//
//        Player player = Cache.players.get(245343L);
//        double styleBuff = player.getStyleBuff(10);
//        System.out.println("styleBuff" + styleBuff);
    }

    // 根据货架配置信息构造1货架结构, shelfid: t_shelf->id
    public static JSONObject createOneShelf(int shelfid, int id) {
        JSONObject shelf = new JSONObject();
        shelf.put("id", id);
        shelf.put("level", 1);
        shelf.put("fid", shelfid);
        shelf.put("status", 0);

        ShelfItem shelfItemConfig = ShelfConfig.getInstance().getItem(shelfid);
        JSONArray goods = new JSONArray();
        shelf.put("goods", goods);
        for (int i = 0; i < shelfItemConfig.getStart_state().size(); i++) {
            int state = shelfItemConfig.getStart_state().get(i);
            goods.add(i, state == 1? "0" : "-1");
        }
        System.out.println(shelf.toJSONString());
        return shelf;
    }

    // 构造1花盆
    public static JSONObject createOneFlowPot(int fid, int id) {
        JSONObject flowPot = new JSONObject();
        flowPot.put("id", id);
        flowPot.put("fid", fid);
        JSONArray seed = new JSONArray();
        seed.add(-1);
        seed.add(-1);
        flowPot.put("seed", seed);
        flowPot.put("status", 0);
        System.out.println(flowPot.toJSONString());
        return flowPot;
    }

    // 构造1收银台
    public static JSONObject createOneCashRegister(int fid, int id) {
        JSONObject cashRegister = new JSONObject();
        cashRegister.put("id", id);
        cashRegister.put("fid", fid);
        cashRegister.put("level", 1);
        cashRegister.put("status", 0);
        System.out.println(cashRegister.toJSONString());
        return cashRegister;
    }

    // 构造1物流
    // 客户端现在是通过 beginTime==0 来判断是否是空闲物流(正常应该是通过车里面有无货物来判断), 所以立即结束1条物流后(尚未收取), 我们暂时把 beginTime 置为-1, 已解决这个问题.
    public static JSONObject createOneLogistics(int newid) {
        JSONObject newObj = new JSONObject();
        newObj.put("id", newid);
        newObj.put("level", 1);
        newObj.put("equip", new JSONObject());
        newObj.put("beginTime", 0);
        newObj.put("decredTime", 0);
        return newObj;
    }

    // 构造1工厂
    public static JSONObject createOneFactory(int newid) {
        JSONObject newObj = new JSONObject();
        newObj.put("id", newid);
        newObj.put("level", 1);
        newObj.put("equip", new JSONObject());
        newObj.put("beginTime", 0);
        newObj.put("decredTime", 0);
        return newObj;
    }

    // 构造玩家背包中服饰类别和对应服饰集合(按收益降序排序)的映射
    public static Map<Integer, ArrayList<Integer>> buildAndGetTypeEquipMaps(Player p) {
        // 重建
        Map<Integer, ArrayList<Integer>> equipTypeMaps = new HashMap<>();
        Set<String> equipids = p.getEquips().keySet();
        for (String eid : equipids) {
            int etype = EquipConfig.getInstance().getItem(Integer.parseInt(eid)).getType();
            ArrayList typeEquips = equipTypeMaps.get(etype);
            if (typeEquips == null) {
                typeEquips = new ArrayList();
            }
            int ieid = Integer.parseInt(eid);
            if (typeEquips.contains(ieid)) {
                continue;
            } else {
                typeEquips.add(ieid);
            }
            // FIXME 利润最大优先排序
//            Collections.sort(typeEquips, new Comparator<Integer>() {
//                @Override
//                public int compare(Integer o1, Integer o2) {
//
//                    return 0;
//                }
//            });
            equipTypeMaps.put(etype, typeEquips);
        }
        player_equiptype_maps.put(p.getId(), equipTypeMaps);
        return equipTypeMaps;
    }

    public static int getEquipStyle(int equipid) {
        return EquipConfig.getInstance().getItem(equipid).getStyleid();
    }

    public static int getEquipType(int equipid) {
        return EquipConfig.getInstance().getItem(equipid).getType();
    }

    public static List<Integer> getEquipScore(int equipid) {
        return EquipConfig.getInstance().getItem(equipid).getScore();
    }

}
