package com.xxgames.demo.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.config.config.*;
import com.xxgames.demo.config.item.*;
import com.xxgames.demo.log.LogFactory;
import com.xxgames.demo.log.LogQuene;
import com.xxgames.demo.model.battle.PlayerPvp;
import com.xxgames.demo.model.mail.Mail;
import com.xxgames.demo.model.mail.MailManager;
import com.xxgames.demo.model.quest.questEvent.QuestEventId;
import com.xxgames.demo.model.quest.questEvent.QuestEventListener;
import com.xxgames.demo.tag.PlayerCountTagManager;
import com.xxgames.demo.tag.PlayerTimeTagManager;
import com.xxgames.demo.utils.Const;
import com.xxgames.util.*;
import org.apache.commons.lang3.RandomUtils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Player {

    private final AtomicLong diamond;
    private final AtomicLong gold;
    public transient QuestEventListener questEventListener = new QuestEventListener();
    // 基础信息
    // min(id)=sid*100000
    private long id;
    private String name;
    private short health;
    private short charm;
    private int totalPay;
    private int regTime;
    private int loginTime;
    // 金苹果数量
    private int apple;
    //邮票
    private int stamp;
    //头像ID,读表获取
    private short head;
    private short level;
    private int exp;
    private int vipExp;
    private byte vipLevel;
    private JSONArray logo;
    // 店铺信息
    private PlayerShops shops;
    private JSONObject items;
    // 服饰
    private JSONObject equips;
    private int equipsCapacity;
    // 剩余的
    private JSONObject furnitures;
    // 装修用掉的
    private JSONObject decorateFurnitures;
    // 逻辑结算时间戳. 假设心跳是 6 秒, 第2次心跳的时候记录的结算时间戳是:(settleTime+10(settlePeriod) 秒, 第1次结算的时候记录的是当前时间戳
    private int settleTime;
    // 结算周期/秒
    private byte settlePeriod;
    // 物流集合
    private JSONArray logistics;
    // 妆容
    private JSONObject faces;
    // 妆容碎片
    private JSONObject faceFrags;
    // 获得过的服饰id集合,做图鉴用
    private JSONArray gotEquips;
    // 试衣间
    private JSONObject dressRoom;
    // 橱窗
    private JSONArray windows;
    // [上次购买低级种子的时间戳,今日已购买低级种子的次数]
    private JSONArray garden;
    // 图纸,和服饰11对应
    private JSONObject draws;
    // 花盆
    private JSONArray flowerPots;
    // 工厂列表
    private JSONArray factorys;
    // 已解锁的可定制服饰
    private JSONArray factoryEquips;
    // 收银台
    private JSONArray cashRegisters;
    // 雇员列表
    private JSONObject employees;
    //第一次改名资格
    private boolean renameChance;
    //签到
    private JSONObject checkIn;
    private List<Long> friendRequestList;
    private List<Long> friendList;
    private PlayerPvp pvp;

    private JSONArray npcFriendList;

    private int charmRank = 0;

    public int getSellRank() {
        return sellRank;
    }

    public void setSellRank(int sellRank) {
        this.sellRank = sellRank;
    }

    private int sellRank = -1 ;

    // 上次聊天消息读取时间戳
    private int msgReadTime;

    public boolean isFirstHeartBeat = false;
    private JSONArray flowerRecord ;

    public JSONArray getFlowerRecord() {
        return flowerRecord;
    }

    public void setFlowerRecord(JSONArray flowerRecord) {
        this.flowerRecord = flowerRecord;
    }

    public SettleResult getSettleResult() {
        return settleResult;
    }

    private SettleResult settleResult = new SettleResult();

    private PlayerCountTagManager countTagManager = new PlayerCountTagManager();

    public PlayerCountTagManager getCountTagManager() {

        return countTagManager;
    }

    public PlayerTimeTagManager getTimeTagManager() {
        return timeTagManager;
    }


    private PlayerTimeTagManager timeTagManager = new PlayerTimeTagManager();


    public Player() {
        setId(1000);
        setLogo(JSON.parseArray(SystemConfConfig.getInstance().getCfg().getPlayer_init_logo()));
        setName("");
        level = 1;
        exp = 0;
        gold = new AtomicLong(SystemConfConfig.getInstance().getCfg().getPlayer_init_gold());
        diamond = new AtomicLong(SystemConfConfig.getInstance().getCfg().getPlayer_init_dimaond());
        setHealth((short) SystemConfConfig.getInstance().getCfg().getPlayer_init_health());
        setCharm((short) SystemConfConfig.getInstance().getCfg().getPlayer_init_charm());
        setTotalPay(0);
        vipLevel = 0;
        vipExp = 0;
        int now = TimeUtil.nowInt();
        setRegTime(now);
        setLoginTime(now);

        shops = new PlayerShops(this);
        shops.init();
//        setShops(new JSONObject());
//        getShops().put("level", SystemConfConfig.getInstance().getCfg().getShop_init_level());
//        getShops().put("decros", JSON.parseObject(SystemConfConfig.getInstance().getCfg().getShop_init_decros()));
//        getShops().put("shelves", JSON.parseArray(SystemConfConfig.getInstance().getCfg().getShelf_init_conf()));
//        getShops().put("rubbish",0);

        equips = JSON.parseObject(SystemConfConfig.getInstance().getCfg().getPlayer_init_equips());
        setEquipsCapacity(SystemConfConfig.getInstance().getCfg().getPlayer_init_equips_capacity());
        setItems(JSON.parseObject(SystemConfConfig.getInstance().getCfg().getPlayer_init_items()));
        setFurnitures(JSON.parseObject(SystemConfConfig.getInstance().getCfg().getPlayer_init_funitures()));

        setDecorateFurnitures(
            JSON.parseObject(SystemConfConfig.getInstance().getCfg().getPlayer_init_decorate_funitures()));
        setSettleTime(0);
        setSettlePeriod((byte) SystemConfConfig.getInstance().getCfg().getShop_init_sell_spead());
        setLogistics(JSON.parseArray(SystemConfConfig.getInstance().getCfg().getPlayer_init_logistics()));

        setFaces(JSON.parseObject(SystemConfConfig.getInstance().getCfg().getPlayer_init_faces()));
        setFaceFrags(new JSONObject());

        setGotEquips(JSON.parseArray(SystemConfConfig.getInstance().getCfg().getPlayer_init_got_equips()));

        setDressRoom(new JSONObject());
        getDressRoom().put("level", 1);
        getDressRoom()
            .put("equips", JSON.parseArray(SystemConfConfig.getInstance().getCfg().getPlayer_init_dressroom_equips()));

        //fixme 橱窗风格随机
        setWindows(JSON.parseArray(SystemConfConfig.getInstance().getCfg().getPlayer_init_windows()));

        setGarden(new JSONArray());
        getGarden().add(0);
        getGarden().add(0);
        getGarden().add(0);
        setApple(0);
        setStamp(0);

        setDraws(new JSONObject());

        setFlowerPots(JSON.parseArray(SystemConfConfig.getInstance().getCfg().getPlayer_init_flowerpots()));

        setFactorys(JSON.parseArray(SystemConfConfig.getInstance().getCfg().getPlayer_init_factorys()));
        setFactoryEquips(new JSONArray());

        setCashRegisters(JSON.parseArray(SystemConfConfig.getInstance().getCfg().getPlayer_init_cashregisters()));
        setEmployees(new JSONObject());

        setRenameChance(true);
        setHead((short) 0);
        setCheckIn(JSON.parseObject(SystemConfConfig.getInstance().getCfg().getPlayer_init_checkIn()));
        RefreshCheckInStates();
        setFriendList(new ArrayList<Long>());
        setFriendRequestList(new ArrayList<Long>());
        setNpcFriendList(JSON.parseArray(SystemConfConfig.getInstance().getCfg().getPlayer_init_friend()));

        setPvp(new PlayerPvp());
        setMsgReadTime(0);
        setFlowerRecord(new JSONArray());

        monthCard = new JSONObject();
        monthCard.put("startTime",0);
        monthCard.put("endTime",0);


        charmRank = 0;
        countTagManager.setPlayer(this);
        timeTagManager.setPlayer(this);
    }

    public Player(long pid) {
        this();
        setId(pid);
    }

    @SuppressWarnings("rawtypes")
    public Player(Map m) {
        setId(Long.parseLong(m.get("id") + ""));
        setLogo(JSON.parseArray(m.get("logo") + ""));
        setName(m.get("name") == null? "" : m.get("name") + "");
        level = Short.parseShort(m.get("level") + "");
        exp = Integer.parseInt(m.get("exp") + "");
        gold = new AtomicLong(Long.parseLong(m.get("gold") + ""));
        diamond = new AtomicLong(Long.parseLong(m.get("diamond") + ""));
        setHealth(Short.parseShort(m.get("health") + ""));
        setCharm(Short.parseShort(m.get("charm") + ""));
        setTotalPay(Integer.parseInt(m.get("totalpay") + ""));
        vipLevel = Byte.parseByte(m.get("viplevel") + "");
        vipExp = Integer.parseInt(m.get("vipexp") + "");
        setRegTime(Integer.parseInt(m.get("regtime") + ""));
        setLoginTime(Integer.parseInt(m.get("logintime") + ""));
        shops = new PlayerShops(this);
        shops.parseFormJson(m.get("shops").toString());
        equips = JSON.parseObject(m.get("equips").toString());
        setEquipsCapacity(Integer.parseInt(m.get("equipscapacity") + ""));
        setItems(JSON.parseObject(m.get("items").toString()));
        setFurnitures(JSON.parseObject(m.get("furnitures").toString()));
        setDecorateFurnitures(JSON.parseObject(m.get("decoratefurnitures").toString()));
        setSettleTime(Integer.parseInt(m.get("settletime").toString()));
        setSettlePeriod(Byte.parseByte(m.get("settleperiod").toString()));
        setLogistics(JSON.parseArray(m.get("logistics").toString()));
        setFaces(JSON.parseObject(m.get("faces").toString()));
        setFaceFrags(JSON.parseObject(m.get("facefrags").toString()));
        setGotEquips(JSON.parseArray(m.get("gotequips").toString()));
        setDressRoom(JSON.parseObject(m.get("dressroom").toString()));
        setWindows(JSON.parseArray(m.get("windows").toString()));
        setGarden(JSON.parseArray(m.get("garden").toString()));
        setApple(Integer.parseInt(m.get("apple") + ""));
        setStamp(Integer.parseInt(m.get("stamp") + ""));
        setDraws(JSON.parseObject(m.get("draws").toString()));
        setFlowerPots(JSON.parseArray(m.get("flowerpots").toString()));
        setFactorys(JSON.parseArray(m.get("factorys").toString()));
        setFactoryEquips(JSON.parseArray(m.get("factoryequips").toString()));
        setCashRegisters(JSON.parseArray(m.get("cashregisters").toString()));
        setEmployees(JSON.parseObject(m.get("employees").toString()));
        setRenameChance(Boolean.parseBoolean(m.get("renamechance").toString()));
        setHead(Short.parseShort(m.get("head") + ""));
        setCheckIn(JSON.parseObject(m.get("checkin").toString()));
        RefreshCheckInStates();
        setFriendList(JSON.parseArray(m.get("friendlist").toString(), Long.class));
        setFriendRequestList(JSON.parseArray(m.get("friendrequestlist").toString(), Long.class));
        setNpcFriendList(JSON.parseArray(m.get("npcfriendlist").toString()));
        setPvp(new PlayerPvp(JSON.parseObject(m.get("pvp").toString())));
        setMsgReadTime(Integer.parseInt(m.get("msgreadtime").toString()));
        setFlowerRecord(JSON.parseArray(m.get("flowerrecord").toString()));
        setMonthCard(JSON.parseObject(m.get("monthCard").toString()));
        charmRank = 0;
        countTagManager.setPlayer(this);
        timeTagManager.setPlayer(this);
    }

    public static void main(String[] args) {
        Player p = new Player();
        Object json = JSON.toJSON(p);
        System.err.println(json);
    }

    public boolean enoughEquipSize(int need) {
        return getEquipsCapacity() - getEquipSize() >= need;
    }

    public boolean enoughLevel(int need) {
        return level >= need;
    }

    /**
     * 是否有足够的某样物品
     *
     * @param type
     * @param id
     * @param need
     *
     * @return
     */
    public boolean bagEnough(int type, int id, int need) {
        if (need < 0) {
            throw new InvalidParameterException("parm must >=zero," + need);
        }
        Number bagNum = getBagNum(type, id);
        return bagNum.intValue() >= need;
    }

    /**
     * 获取实际存放数量
     */
    public int getEquipSize() {
        // FIXME 性能问题
        int size = 0;
        Set<String> keys = equips.keySet();
        for (String k : keys) {
            EquipItem equip = EquipConfig.getInstance().getItem(Integer.parseInt(k));
            int overlapnum = equip.getOverlapnum();
            int hasNum = equips.getIntValue(k);
            int occupyNum = hasNum / overlapnum + 1;
            size += occupyNum;
        }
        return size;
    }

    /**
     * 初始化货物位置信息
     */
    public void int_goods_places() {
        if (DataManager.players_goods_places.containsKey(getId())) {
            return;
        }
//        DataManager.players_goods_places.put(getAid(), new ConcurrentHashMap<Integer, JSONArray>());
//        ConcurrentHashMap<Integer, JSONArray> player_goods_places = DataManager.players_goods_places.get(getAid());
        ConcurrentHashMap<Integer, JSONArray> player_goods_places = new ConcurrentHashMap<Integer, JSONArray>();
        DataManager.players_goods_places.put(getId(), player_goods_places);

        // 遍历玩家所有货架
        JSONArray shelfs = getShops().getShelves();
        for (int i = 0; i < shelfs.size(); i++) {
            JSONObject shelf = shelfs.getJSONObject(i);
            int status = shelf.getIntValue("status");
            if (status == 0) {
                continue;
            }
            JSONArray goods = shelf.getJSONArray("goods");
            // 遍历每1格子
            for (int j = 0; j < goods.size(); j++) {
                String item = goods.getString(j);
                if (item.length() <= 2) {
                    continue;
                }
                String[] split = item.split("-");
                String equipidStr = split[0];
                int typeid = DataManager.getEquipType(
                    Integer.parseInt(equipidStr));//DataManager.equips.getJSONObject(equipidStr).getIntValue("type");
                JSONArray places = player_goods_places.get(typeid);
                if (places == null) {
                    places = new JSONArray();
                }
                // 添加货物位置信息
                String meta = i + "," + j;
                places.add(meta);
                player_goods_places.put(typeid, places);
            }
        }
    }

    private boolean updateEquip(String equipid, int num) {
        if (num < 0) {
            return false;
        }

        Map<Integer, ArrayList<Integer>> playerEquips = DataManager.player_equiptype_maps.get(getId());
        if (playerEquips == null) {
            // 重启过后，且背包服饰未更新 且 未一键上架 的情况下
            playerEquips = DataManager.buildAndGetTypeEquipMaps(this);
        }
        int equipType = DataManager
            .getEquipType(Integer.parseInt(equipid));//DataManager.equips.getJSONObject(equipid).getIntValue("type");

        if (num == 0) {
            // 背包删除
            equips.remove(equipid);
            // 类型映射表更新
            ArrayList<Integer> equips = playerEquips.get(equipType);
            if (equips == null) {
                // FIXME 为什么会为null
                equips = new ArrayList<>();
            }
            for (Integer eid : equips) {
                // FIXME 能否不遍历
                if (equipid.equals(eid + "")) {
                    equips.remove(eid);
                    System.out.println("服饰类型映射表更新,remove:" + getId() + "," + equipType + "," + eid);
                    break;
                }
            }
            playerEquips.put(equipType, equips);
        } else {
            // 背包更新
            equips.put(equipid, num);
            // 类型映射表更新
            ArrayList<Integer> equips = playerEquips.get(equipType);
            if (equips == null) {
                // 第1次获得该类服饰
                equips = new ArrayList<>();
            }
            // 原先映射表里是否存在
            boolean has = false;
            for (Integer eid : equips) {
                if (equipid.equals(eid + "")) {
                    has = true;
                    break;
                }
            }
            if (!has) {
                // 原先类型映射表中不存在(比如新加的服饰)，添加进去
                equips.add(Integer.parseInt(equipid));
                System.out.println("服饰类型映射表更新,add:" + getId() + "," + equipType + "," + equipid);
                playerEquips.put(equipType, equips);
            }
            // 收藏到图鉴
            int equipidInt = Integer.parseInt(equipid);
            if (!getGotEquips().contains(equipidInt)) {
                getGotEquips().add(equipidInt);
            }
        }
        return true;
    }

    public void addNpcCharm(long npcId, int addNum) {
        for (int i = 0; i < npcFriendList.size(); i++) {
            JSONObject npc = npcFriendList.getJSONObject(i);
            if (npc != null && npc.getInteger("id") == npcId) {
                int charm = npc.getInteger("charm");
                npc.put("charm", charm + addNum);
            }
        }
    }

    public void addExp(int expAdd) {
        if (expAdd <= 0) {
            return;
        }
        exp += expAdd;

        PlayerLevelItem levelConf = PlayerLevelConfig.getInstance().getItem(level);
        if (levelConf == null) {
            // 已升到最高级, 不加经验
            return;
        }
        int needExp = levelConf.getExp();
        while (exp - needExp >= 0) {
            exp -= needExp;
            // TODO 发送邮件
            levelConf = PlayerLevelConfig.getInstance().getItem(level + 1);
            if (levelConf == null) {
                // 到最高级
                System.out.println("maxLevel," + getId() + "," + level);
                break;
            }
            level += 1;
            needExp = levelConf.getExp();
            questEventListener.dispatchEvent(QuestEventId.Level, level);
            //           this.questEventListener.dispatchEvent(QuestEventId);
        }
    }

    private void addItems(String itemid, int num) {
        int itemNum = getItems().getIntValue(itemid + "");
        itemNum += num;
        getItems().put(itemid + "", itemNum);
    }

    private void addDraws(String id, int num) {
        int itemNum = getDraws().getIntValue(id + "");
        itemNum += num;
        getDraws().put(id + "", itemNum);
    }

    private void addFaces(int id, String op) {
        if (!getFaces().containsKey(id + "")) {
            // 没脸, 出脸
            JSONArray meta = new JSONArray();
            meta.add(0, 1);
            meta.add(1, 0);
            getFaces().put(id + "", meta);
            LogQuene.add(LogFactory.createFaceLog(this.id, id, 1, op));
        } else {
            // 有脸,转换成碎片
            FaceItem faceCfg = FaceConfig.getInstance().getItem(id);
            int addNum = faceCfg.getCombine_frag() / 2;
            int fnum = getFaceFrags().getIntValue(id + "");
            fnum += addNum;
            getFaceFrags().put(id + "", fnum);
            LogQuene.add(LogFactory.createFaceFragLog(this.id, id, addNum, op));
        }
    }

    private void addFaceFrags(String id, int num, String op) {
        int itemNum = getFaceFrags().getIntValue(id + "");
        itemNum += num;
        getFaceFrags().put(id, itemNum);
        LogQuene.add(LogFactory.createFaceFragLog(this.id, Integer.parseInt(id), num, op));
    }

    private void addOrdinalFurnitures(String id, int num) {
        int fNum = getFurnitures().getIntValue(id);
        fNum += num;
        getFurnitures().put(id, fNum);
    }

    public boolean exceedBagLimit(int type, int id, int num) {
        return false;
    }

    public boolean addBags(String op, JSONObject reward) {
        BagAddResult bagAddResult =
            addBags(op, reward.getIntValue("type"), reward.getIntValue("id"), reward.getIntValue("num"));
        return bagAddResult.isSucc();
    }

    /**
     * 添加背包
     *
     * @param op 操作类型
     * @param type
     * @param id
     * @param addNum
     *
     * @return
     */
    public BagAddResult

    addBags(final String op, final int type, final int id, final int addNum) {
        BagAddResult ret = new BagAddResult();

        if (addNum <= 0) {
            ret.setSucc(false);
            return ret;
        }
        switch (type) {
        case BagType.ITEM:
            addItems(id + "", addNum);
            ret.setAdded(Data.createRewardObj(BagType.ITEM, id, addNum));
            LogQuene.add(LogFactory.createItemLog(this.id, id, addNum, op));
            break;
        case BagType.EQUIP:
            int hasnum = equips.getIntValue(id + "");
            int leftNum = hasnum + addNum;
            boolean upRet = updateEquip(id + "", leftNum);
            ret.setSucc(upRet);
            if (upRet) {
                ret.setAdded(Data.createRewardObj(BagType.EQUIP, id, addNum));
                LogQuene.add(LogFactory.createEquipLog(this.id, id, addNum, op));
            }
            break;
        case BagType.BLUEPRINT:
            addDraws(id + "", addNum);
            ret.setAdded(Data.createRewardObj(BagType.BLUEPRINT, id, addNum));
            LogQuene.add(LogFactory.createDrawLog(this.id, id, addNum, op));
            break;
        case BagType.FACE:
            addFaces(id, op);
            break;
        case BagType.GOLD:
            gold.addAndGet(addNum);
            ret.setAdded(Data.createRewardObj(BagType.GOLD, id, addNum));
            LogQuene.add(LogFactory.createGoldLog(this.id, addNum, op));
            break;
        case BagType.DIAMOND:
            diamond.addAndGet(addNum);
            ret.setAdded(Data.createRewardObj(BagType.DIAMOND, id, addNum));
            LogQuene.add(LogFactory.createDiamondLog(this.id, addNum, op));
            break;
        case BagType.FURNITURE:
            //JSONObject furnitureConf = DataManager.furnitures.getJSONObject(id + "");
            FurnitureItem furnitureConfig = FurnitureConfig.getInstance().getItem(id);
            int tmpNum = addNum;
            //int ftype = furnitureConf.getIntValue("type");
            if (furnitureConfig.getType() == FurnitureType.CASHREGISTER) {
                // 收银台
                JSONObject oneCashRegister = DataManager.createOneCashRegister(id, genNewCashRegisterId());
                getCashRegisters().add(oneCashRegister);
                ret.setAdded(oneCashRegister);
                tmpNum = 1;
            } else if (furnitureConfig.getType() == FurnitureType.SHELF) {
                // 货架
                JSONArray shelves = getShops().getShelves();
                JSONObject oneShelf = DataManager.createOneShelf(id, genNewShelfId());
                shelves.add(oneShelf);
                ret.setAdded(oneShelf);
                tmpNum = 1;
            } else if (furnitureConfig.getType() == FurnitureType.FLOWERPOT) {
                // 花盆
                JSONObject oneFlowPot = DataManager.createOneFlowPot(id, genNewFlowPotId());
                getFlowerPots().add(oneFlowPot);
                ret.setAdded(oneFlowPot);
                tmpNum = 1;
            } else {
                // 普通家具
                addOrdinalFurnitures(id + "", addNum);
                ret.setAdded(Data.createRewardObj(BagType.FURNITURE, id, addNum));
            }

//            LogEventManager.instance.fireLogEvent(LogFactory.createFurnitureLog(this.id, id, tmpNum, op));
            LogQuene.add(LogFactory.createFurnitureLog(this.id, id, tmpNum, op));
            break;
        case BagType.APPLE:
            setApple(getApple() + addNum);
            ret.setAdded(Data.createRewardObj(BagType.APPLE, id, addNum));
            LogQuene.add(LogFactory.createAppleLog(this.id, addNum, op));
            break;
        case BagType.STAMP:
            setStamp(getStamp() + addNum);
            ret.setAdded(Data.createRewardObj(BagType.STAMP, id, addNum));
            LogQuene.add(LogFactory.createStampLog(this.id, addNum, op));
            break;
        case BagType.FACEFRAGS:
            addFaceFrags(id + "", addNum, op);
            ret.setAdded(Data.createRewardObj(BagType.FACEFRAGS, id, addNum));
            break;
        case BagType.CRYSTAL:
            getPvp().addCrystal(addNum);
            ret.setAdded(Data.createRewardObj(BagType.CRYSTAL, id, addNum));
            LogQuene.add(LogFactory.createCrystalLog(this.id, addNum, op));
            break;
        case BagType.EXP:
            addExp(addNum);
            ret.setAdded(Data.createRewardObj(BagType.EXP, id, addNum));
            LogQuene.add(LogFactory.createExpLog(this.id, addNum, op));
            break;
        case BagType.NPC_CHARM:
            addNpcCharm((long) id, addNum);
            ret.setAdded(Data.createRewardObj(BagType.NPC_CHARM, id, addNum));
            //LogQuene.add(LogFactory.createExpLog(this.id, addNum, op));
            break;
        }

        // FIXME 参考Listener 弄成是 回调方式或者监听模式
        return ret;
    }

    /**
     * 扣背包
     *
     * @param type
     * @param id
     * @param decrNum 负数
     *
     * @return
     */
    public boolean decrBags(String op, int type, int id, int decrNum) {
        if (decrNum >= 0) {
            return false;
        }

        int hasnum;
        switch (type) {
        case BagType.ITEM:
            int itemNum = getItems().getIntValue(id + "");
            if (itemNum < Math.abs(decrNum)) {
                return false;
            }
            itemNum += decrNum;
            getItems().put(id + "", itemNum);
            LogQuene.add(LogFactory.createItemLog(this.id, id, decrNum, op));
            break;
        case BagType.EQUIP:
            hasnum = equips.getIntValue(id + "");
            if (hasnum < Math.abs(decrNum)) {
                return false;
            }
            boolean upRet = updateEquip(id + "", hasnum + decrNum);
            if (upRet) {
                LogQuene.add(LogFactory.createEquipLog(this.id, id, decrNum, op));
            }
            break;
        case BagType.BLUEPRINT:
            hasnum = getDraws().getIntValue(id + "");
            if (hasnum < Math.abs(decrNum)) {
                return false;
            }
            getDraws().put(id + "", hasnum + decrNum);
            if (hasnum+decrNum <= 0){
                getDraws().remove(id);
            }
            LogQuene.add(LogFactory.createDrawLog(this.id, id, decrNum, op));
            break;
        case BagType.FACE:
            // 脸暂时不能扣
            return false;
        case BagType.GOLD:
            if (gold.get() < Math.abs(decrNum)) {
                return false;
            }
            decrGold(op, -decrNum);
            break;
        case BagType.DIAMOND:
            return decrDiamond(op, -decrNum);
        case BagType.FURNITURE:
            boolean enough = bagEnough(BagType.FURNITURE, id, Math.abs(decrNum));
            if (!enough) {
                return false;
            }
            // FIXME 暂不提供扣家具的接口
            return false;
        case BagType.APPLE:
            return decrApple(op, -decrNum);
        case BagType.FACEFRAGS:
            hasnum = getFaceFrags().getIntValue(id + "");
            if (hasnum < Math.abs(decrNum)) {
                return false;
            }
            hasnum += decrNum;
            getFaceFrags().put(id + "", hasnum);
            LogQuene.add(LogFactory.createFaceFragLog(this.id, id, decrNum, op));
            break;
        case BagType.CRYSTAL:
            hasnum = getBagNum(BagType.CRYSTAL, 0).intValue();
            if (hasnum < Math.abs(decrNum)) {
                return false;
            }
            getPvp().addCrystal(decrNum);
            LogQuene.add(LogFactory.createCrystalLog(this.id, decrNum, op));
            break;
        case BagType.EXP:
            // 经验不能扣
            return false;
        }
        return true;
    }

    public boolean addBags(String op, List<PropItem> items) {
        for (PropItem item : items) {
            if (!addBags(op, item.getType(), item.getId(), item.getNum()).isSucc()) {
                return false;
            }
        }
        return true;
    }

    public boolean addBags(String op, JSONArray rewards) {
        for (int i = 0; i < rewards.size(); i++) {
            JSONObject reward = rewards.getJSONObject(i);
            BagAddResult bagAddResult =
                addBags(op, reward.getIntValue("type"), reward.getIntValue("id"), reward.getIntValue("num"));
            if (!bagAddResult.isSucc()) {
                return false;
            }

        }
        return true;
    }

    /**
     * 获取背包中物品数量
     *
     * @param type
     * @param id
     *
     * @return
     */
    public Number getBagNum(int type, int id) {
        if (type == BagType.ITEM) {
            return getItems().getIntValue(id + "");
        } else if (type == BagType.EQUIP) {
            return equips.getIntValue(id + "");
        } else if (type == BagType.BLUEPRINT) {
            return getDraws().getIntValue(id + "");
        } else if (type == BagType.FACE) {
            if (getFaces().containsKey(id + "")) {
                return 1;
            }
            return 0;
        } else if (type == BagType.GOLD) {
            return gold.get();
        } else if (type == BagType.DIAMOND) {
            return diamond.get();
        } else if (type == BagType.FURNITURE) {
            return getFurnitureNum(id);
        } else if (type == BagType.FACEFRAGS) {
            return getFaceFrags().getIntValue(id + "");
        } else if (type == BagType.CRYSTAL) {
            if (getPvp() == null) {
                return 0;
            }
            return getPvp().getCrystal();
        } else if (type == BagType.APPLE) {
            return apple;
        } else if (type == BagType.STAMP) {
            return stamp;
        }
        return 0;
    }

    public int getShopRubbishAddSecond() {
        return getShops().getRubbishs();
    }

    public int getCashRegisterDecrSecond() {
        //t_cashregister_level表decrsecond字段
        for (int i = 0; i < cashRegisters.size(); i++) {
            JSONObject cashRegister = cashRegisters.getJSONObject(i);
            if (cashRegister.getInteger("status") == 1) {
                int fid = cashRegister.getIntValue("fid");// t_cashregister_level->id
                int level = cashRegister.getIntValue("level");
                int levelId = fid * 100 + level;
                CashRegisterLevelItem item = CashRegisterLevelConfig.getInstance().getItem(levelId);
                if (item != null) {
                    return item.getDecrsecond() / 10;
                }
            }
        }
        return 0;
    }

    public int getDressRoomDecrSecond() {
        int dress_level = this.dressRoom.getInteger("level");
        DressRoomLevelItem dress_room = DressRoomLevelConfig.getInstance().getItem(dress_level);
        if (dress_room != null) {
            return dress_room.getBuff();
        }
        return 0;
    }

    public int getWindowsDecrSecond() {
        JSONArray windows = getWindows();
        int total_score = 0;
        for (int i = 0; i < windows.size(); i++) {
            JSONObject win = windows.getJSONObject(i);
            total_score += win.getInteger("score");
        }
        if (total_score > 0) {
            return total_score / 300;
        } else {
            return 0;
        }
    }

    // 计算雇员buff(结算周期加快的秒数)
    public int getEmployeesDecrSecond() {
        int decrSeconds = 0;
        // clone 是为了避免 : ConcurrentModificationException
        JSONObject employeesNew = new JSONObject();
        Set<String> ids = employees.keySet();
        for (String id : ids) {
            OfficeItem employeeConf = OfficeConfig.getInstance().getItem(Integer.parseInt(id));
            // 总可雇佣时长
            int totalTime = employeeConf.getTime();
            // 雇佣开始时间戳
            int beginTime = employees.getIntValue(id);
            int passedTime = TimeUtil.nowInt() - beginTime;
            if (passedTime > totalTime) {
                continue;
            }
            employeesNew.put(id, beginTime);
            if (employeeConf.getType() != Const.EMPLOYEE_TYPE_BAOJIE) {

                decrSeconds += employeeConf.getDecrsecond();
            }

        }
        employees = employeesNew;
        return decrSeconds / 10;
    }

    public JSONObject openBox(int itemid) {
        GoodsBoxItem goodsBoxItem = GoodsBoxItemConfig.getInstance().getItem(itemid);
        ArrayList<PropItem> rewards = new ArrayList<PropItem>();
        if (goodsBoxItem != null) {
            List<RandBucketItem<PropItem>> dropList1 = new ArrayList<RandBucketItem<PropItem>>();
            for (BoxItem item : goodsBoxItem.getDropItemsArray1()) {
                RandomUtil.addRandBucket(dropList1, item.getPropItem(), item.getRate());
            }
            rewards.add(RandomUtil.getRandBucket(dropList1));

            List<RandBucketItem<PropItem>> dropList2 = new ArrayList<RandBucketItem<PropItem>>();
            for (BoxItem item : goodsBoxItem.getDropItemsArray2()) {
                RandomUtil.addRandBucket(dropList2, item.getPropItem(), item.getRate());
            }
            rewards.add(RandomUtil.getRandBucket(dropList2));

            List<RandBucketItem<PropItem>> dropList3 = new ArrayList<RandBucketItem<PropItem>>();
            for (BoxItem item : goodsBoxItem.getDropItemsArray3()) {
                RandomUtil.addRandBucket(dropList3, item.getPropItem(), item.getRate());
            }
            rewards.add(RandomUtil.getRandBucket(dropList3));
        }
        addBags("openBox", rewards);

        JSONObject ret = new JSONObject();
        ret.put("rewards", rewards);
        return ret;
    }

    private int genNewCashRegisterId() {
        int size = getCashRegisters().size();
        int nid = 0;
        for (int i = 0; i < size; i++) {
            int id = getCashRegisters().getJSONObject(i).getIntValue("id");
            if (id >= nid) {
                nid = id + 1;
            }
        }
        return nid;
    }

    private int genNewShelfId() {
        JSONArray shelves = getShops().getShelves();
        int size = shelves.size();
        int nid = 0;
        for (int i = 0; i < size; i++) {
            int id = shelves.getJSONObject(i).getIntValue("id");
            if (id >= nid) {
                nid = id + 1;
            }
        }
        questEventListener.dispatchEvent(QuestEventId.ShelfNum, shelves.size() + 1);
        return nid;
    }

    private int genNewFlowPotId() {
        int size = getFlowerPots().size();
        int nid = 0;
        for (int i = 0; i < size; i++) {
            int id = getFlowerPots().getJSONObject(i).getIntValue("id");
            if (id >= nid) {
                nid = id + 1;
            }
        }
        return nid;
    }

    private int getFurnitureNum(int fid) {
        //JSONObject furnitureConf = DataManager.furnitures.getJSONObject(fid + "");
        FurnitureItem config = FurnitureConfig.getInstance().getItem(fid);
        //int ftype = furnitureConf.getIntValue("type");
        int hasNum = 0;
        if (config.getType() == FurnitureType.CASHREGISTER) {
            // 收银台
            for (int i = 0; i < getCashRegisters().size(); i++) {
                JSONObject cashObj = getCashRegisters().getJSONObject(i);
                if (cashObj.getIntValue("fid") == fid && cashObj.getIntValue("status") == 0) {
                    hasNum += 1;
                }
            }
        } else if (config.getType() == FurnitureType.SHELF) {
            // 货架
            JSONArray shelves = getShops().getShelves();
            for (int i = 0; i < shelves.size(); i++) {
                JSONObject shelfObj = shelves.getJSONObject(i);
                if (shelfObj.getIntValue("fid") == fid && shelfObj.getIntValue("status") == 0) {
                    hasNum += 1;
                }
            }
        } else if (config.getType() == FurnitureType.FLOWERPOT) {
            // 花盆
            for (int i = 0; i < getFlowerPots().size(); i++) {
                JSONObject flowerPobObj = getFlowerPots().getJSONObject(i);
                if (flowerPobObj.getIntValue("fid") == fid && flowerPobObj.getIntValue("status") == 0) {
                    hasNum += 1;
                }
            }
        } else {
            // 普通家具
            hasNum = getFurnitures().getIntValue(fid + "") - getDecorateFurnitures().getIntValue(fid + "");
        }
        return hasNum;
    }

    //刷新时间状态：0——在未来；1——已签到；2——未签；3——当前时间段
    public void RefreshCheckInStates() {
        //检查年月，不匹配则重置签到状态
        CheckYearAndMonth();
        //获取当前日期和时间点
        int day = TimeUtil.getDay();
        int hour = TimeUtil.getHour();
        int hourID;
        if (hour >= 6 && hour < 12) {
            hourID = 0;
        } else if (hour >= 12 && hour < 18) {
            hourID = 1;
        } else {
            hourID = 2;
        }
        //数组小标0开始，天数-1
        day -= 1;
        //修改时间在这之前的“未来状态”为“补签”
        int maxDay = TimeUtil.getMaxDayForTodayThisMonth();
        for (int i = 0; i < maxDay; i++) {
            JSONArray stateOneDay = getCheckIn().getJSONArray("states").getJSONObject(i).getJSONArray("times");
            //昨天之前
            if (i < day) {
                for (int j = 0; j < 3; j++) {
                    if (stateOneDay.getIntValue(j) == 0) {
                        stateOneDay.set(j, 2);
                    }
                }
            }
            //今天，三个时间段分别判断
            else if (i == day) {
                for (int j = 0; j < 3; j++) {
                    if (j < hourID && stateOneDay.getIntValue(j) == 0) {
                        stateOneDay.set(j, 2);
                    } else if (j == hourID && stateOneDay.getIntValue(j) == 0) {
                        stateOneDay.set(j, 3);
                    }
                }
            }
        }
        //刷新累积签到状态
        RefreshCheckInChest();
    }

    void CheckYearAndMonth() {
        int curYear = TimeUtil.getYear();
        int curMonth = TimeUtil.getMonth();
        int oldYear = getCheckIn().getIntValue("year");
        int oldMonth = getCheckIn().getIntValue("month");
        if (curYear != oldYear || curMonth != oldMonth) {
            setCheckIn(JSON.parseObject(SystemConfConfig.getInstance().getCfg().getPlayer_init_checkIn()));
            getCheckIn().put("year", curYear);
            getCheckIn().put("month", curMonth);
        }
    }

    //刷新累积签到状态：0——不可开启；1——已开启；2——未开启
    void RefreshCheckInChest() {
        //累积签到的箱子状态
        JSONArray chests = getCheckIn().getJSONArray("chests");
        int hasCheckedDays = getCheckIn().getIntValue("has_checked_days");
        //只判断原本不可用的箱子状态
        for (int i = 0; i < chests.size(); i++) {
            if (chests.getIntValue(i) == 0 &&
                hasCheckedDays >= CheckInChestConfig.getInstance().getItem(i).getNeed_days()) {
                chests.set(i, 2);
            }
        }
    }

    //修改签到信息
    public boolean CheckIn(int day, int hourID) {
        //刷新过去的时间
        RefreshCheckInStates();

        //拿到最新的状态列表
        JSONArray stateOneDay = getCheckIn().getJSONArray("states").getJSONObject(day).getJSONArray("times");
        int curHourState = stateOneDay.getIntValue(hourID);

        //已签到或者在未来
        if (curHourState == 1 || curHourState == 0) {
            return false;
        }
        //补签次数不足
        if (curHourState == 2 && getCheckIn().getIntValue("remain_recheck_times") == 0) {
            return true;
        }
        //判断是不是今日第一次签到
        boolean isFirstCheckThisDay = true;
        for (int i = 0; i < 3; i++) {
            if (stateOneDay.getIntValue(i) == 1) {
                isFirstCheckThisDay = false;
                break;
            }
        }
        //是，则已签天数加1
        if (isFirstCheckThisDay) {
            getCheckIn().put("has_checked_days", getCheckIn().getIntValue("has_checked_days") + 1);
        }
        //修改签到状态
        stateOneDay.set(hourID, 1);
        //修改补签次数
        if (curHourState == 2) {
            getCheckIn().put("remain_recheck_times", getCheckIn().getIntValue("remain_recheck_times") - 1);
        }
        //入库
        ArrayList<Integer> rewardlist = null;
        if (hourID == 0) {
            rewardlist = CheckInConfig.getInstance().getItem(day).getMorning();
        } else if (hourID == 1) {
            rewardlist = CheckInConfig.getInstance().getItem(day).getAfternoon();
        } else {
            rewardlist = CheckInConfig.getInstance().getItem(day).getEvening();
        }

        addBags("CheckIn", rewardlist.get(0), rewardlist.get(1), rewardlist.get(2));

        return true;
    }

    //修改累积签到状态
    public boolean OpenCheckInChest(int chestID) {
        //先刷新
        RefreshCheckInChest();
        if (getCheckIn().getJSONArray("chests").getIntValue(chestID) == 2) {
            //修改状态
            getCheckIn().getJSONArray("chests").set(chestID, 1);
            //入库
            CheckInChestItem config = CheckInChestConfig.getInstance().getItem(chestID);
            for (int i = 0; i < config.getItem_type().size(); i++) {
                addBags("OpenCheckInChest", config.getItem_type().get(i), config.getItem_id().get(i),
                        config.getItem_num().get(i));
            }

            return true;
        } else {
            return false;
        }
    }

    public byte getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(byte vipLevel) {
        questEventListener.dispatchEvent("viplevel", vipLevel);
        this.vipLevel = vipLevel;
    }

    public AtomicLong getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold.set(gold);
        questEventListener.dispatchEvent("gold", gold);
    }

    /**
     * 扣钻
     *
     * @param op 来源
     * @param decr 扣多少
     *
     * @return
     */
    public boolean decrDiamond(String op, int decr) {
        if (decr == 0) {
            return true;
        }
        if (decr < 0) {
            return false;
        }
        if (diamond.get() < decr) {
            return false;
        }
        diamond.addAndGet(-decr);
        LogQuene.add(LogFactory.createDiamondLog(id, -decr, op));
        getCountTagManager().addTag(Const.USER_COUNT_TAG_USE_DIAMOND,decr);
        return true;
    }

    public boolean decrGold(String op, int decr) {
        if (decr <= 0) {
            return true;
        }
        if (gold.get() < decr) {
            return false;
        }
        gold.getAndAdd(-decr);
        LogQuene.add(LogFactory.createGoldLog(id, -decr, op));
        return true;
    }

    public boolean decrStamp(String op, int decr) {
        if (decr <= 0) {
            return true;
        }
        if (getStamp() < decr) {
            return false;
        }
        setStamp(getStamp() - decr);
        LogQuene.add(LogFactory.createStampLog(id, -decr, op));
        return true;
    }

    public boolean decrApple(String op, int decr) {
        if (decr <= 0) {
            return true;
        }
        if (getApple() < decr) {
            return false;
        }
        setApple(getApple() - decr);
        LogQuene.add(LogFactory.createAppleLog(id, -decr, op));
        return true;
    }

    /**
     * 获得某种风格的妆容buff和 FIXME 暂时是临时计算的, 可以用空间换时间提高效率
     *
     * @param styleid
     *
     * @return
     */
    public double getFaceBuff(int styleid) {
        Set<String> fids = getFaces().keySet();
        long buffVal = 0L;
        for (String fid : fids) {
            JSONArray meta = getFaces().getJSONArray(fid);
            int flvel = meta.getIntValue(0);
            FaceItem faceConf = FaceConfig.getInstance().getItem(Integer.parseInt(fid));
            FaceBuffItem buff = faceConf.getBuff(styleid);
            if (buff != null) {
                buffVal += buff.getBuffAdd(flvel);
            }
        }
        double ret = buffVal * 1.0d / 10000;
        return ret;
    }
    public int getPlayerDataInTime(String eventName ,int startTime , int endTime){
        if (eventName.equals(QuestEventId.GetGold)) {
        return (int)(getCountTagManager().getTagCount(Const.USER_COUNT_TAG_TODAY_SELL,startTime,endTime));
    }
        if (eventName.equals(QuestEventId.PayTotal)) {
        return (int)(getCountTagManager().getTagCount(Const.USER_COUNT_TAG_PAY_TOTAL,startTime,endTime));
    }
        if (eventName.equals(QuestEventId.UseDiamond)) {
        return (int)(getCountTagManager().getTagCount(Const.USER_COUNT_TAG_USE_DIAMOND,startTime,endTime));
    }
        return 0 ;
}
    public int getPlayerData(String eventName) {
        if (eventName.equals(QuestEventId.Level)) {
            return level;
        }

        if (eventName.equals(QuestEventId.FriendNum)) {
            return friendList.size();
        }

        if (eventName.equals(QuestEventId.ShelfNum)) {
            return getShops().getShelves().size();
        }

        if (eventName.equals(QuestEventId.ShelfLevel)) {
            int maxLevel = 0 ;
            for (int i = 0 ;i < getShops().getShelves().size() ; i ++){
                JSONObject shelve = getShops().getShelves().getJSONObject(i);
                if (shelve.getInteger("level") > maxLevel) maxLevel = shelve.getInteger("level");
            }
            return maxLevel;
        }

        if (eventName.equals(QuestEventId.LogisticsNum)) {
            return logistics.size();
        }

        if (eventName.equals(QuestEventId.LogisticsLevel)) {
            int maxLevel = 0 ;
            for (int i = 0 ; i < logistics.size() ; i ++){
                JSONObject logistic = logistics.getJSONObject(i);
                if (logistic.getInteger("level") > maxLevel) maxLevel = logistic.getInteger("level");
            }
            return maxLevel;
        }

        if (eventName.equals(QuestEventId.FactorNum)) {
            return factorys.size();
        }

        if (eventName.equals(QuestEventId.FactorLevel)) {
            int maxLevel = 0 ;
            for (int i = 0 ; i < factorys.size() ; i ++){
                JSONObject factory = factorys.getJSONObject(i);
                if (factory.getInteger("level") > maxLevel) maxLevel = factory.getInteger("level");
            }
            return maxLevel;
        }

        if (eventName.equals(QuestEventId.ShopExpandLevel)) {
            return getShops().getLevel();
        }

        if (eventName.equals(QuestEventId.DressRoomLevel)) {
            return dressRoom.getInteger("level");
        }

        if (eventName.equals(QuestEventId.LoginDay)) {
            int loginDay = TimeUtil.getDiffOfDay(getRegTime(), getLoginTime()) + 1;
            return loginDay;
        }

        if (eventName.equals(QuestEventId.PayTotal)) {
            return getTotalPay();
        }

        if (eventName.equals(QuestEventId.PayFirst)) {
            return getTotalPay();
        }

        if (eventName.equals(QuestEventId.CashregisterLevel)){
            int maxLevel = 0 ;
            for (int i = 0 ; i < cashRegisters.size() ; i ++){
                JSONObject cashRegister = cashRegisters.getJSONObject(i);
                if (cashRegister.getInteger("level") > maxLevel) maxLevel = cashRegister.getInteger("level");
            }
            return maxLevel;
        }

        return -1;
    }

    public int getShopSellSpeed() {
        int shop_init_sell_spead = SystemConfConfig.getInstance().getCfg().getShop_init_sell_spead();

        int sell_speed = shop_init_sell_spead
                         - getEmployeesDecrSecond() - getWindowsDecrSecond()
                         - getDressRoomDecrSecond() - getCashRegisterDecrSecond()
                         + getShopRubbishAddSecond();
        int decrratio = 0 ;
        if (checkMonthCard()){
            decrratio = 5;
        }

        sell_speed = (int)( sell_speed / ((100 - decrratio) / 100.0) );
        if (sell_speed <= 0) {
            sell_speed = 10;
            System.err
                .println(
                    "sell_speed<=0," + getId() + "," + shop_init_sell_spead +
                    ", EmployeesDecr = " + getEmployeesDecrSecond() + ", WindowsDecr = " + getWindowsDecrSecond()
                    + ", DressRoomDecr = " + getDressRoomDecrSecond() + ", CashRegisterDecr = " +
                    getCashRegisterDecrSecond()
                    + ", ShopRubbishAdd =  " + getShopRubbishAddSecond());

        }
        return sell_speed;
    }

    public void AutoSellGoods() {
        int nowTime = TimeUtil.nowInt();
        int speed = getShopSellSpeed();
        if (settleTime == 0 ){
            AutoSellGoodsOneTimes();
            settleTime = nowTime;
        }else if(settleTime + speed < nowTime){
            AutoSellGoodsOneTimes();
            settleTime = settleTime + speed;
            if (settleTime < nowTime - 20){//矫正settleTime
                settleTime = nowTime;
            }
        }
    }
    private void AutoSellGoodsOneTimes(){
        Map<Integer, JSONArray> player_goods_places = DataManager.players_goods_places.get(getId());
        Set<Integer> sellingEquipTypes = player_goods_places.keySet();
        int sellType = -1;
        List<RandBucketItem<Integer>> sellTypeList = new ArrayList<RandBucketItem<Integer>>();
        for (int st : sellingEquipTypes) {
            if (player_goods_places.get(st).size() > 0) {
                RandomUtil.addRandBucket(sellTypeList, st, 1);
            }
        }
        if (sellTypeList.size() > 0){
            sellType = RandomUtil.getRandBucket(sellTypeList) ;
        }

        if (sellType == -1) {
            return;
        }
        JSONArray places = player_goods_places.get(sellType);
        if (places == null || places.size() == 0) {
            // 没有该类货物在售
//            log.debug(getId() + "," + sellType + ",no goods");
            return;
        }
        // 随1种该类别货物
        int place = RandomUtils.nextInt(0, places.size());
        // 货架编号,pos
        String meta = places.getString(place);
        String[] goodmeta = StrUtil.split(meta, ",");
        int shelfid = Integer.parseInt(goodmeta[0]);
        int pos = Integer.parseInt(goodmeta[1]);
        JSONObject shelf = getShops().getShelves().getJSONObject(shelfid);
        int status = shelf.getIntValue("status");
        if (status == 0) {
//            log.debug("undecro," + p.getId() + "," + shelfid);
            return;
        }
        JSONArray goods = shelf.getJSONArray("goods");
        String good = goods.getString(pos);
        String[] split = good.split("-");
        int equipid = Integer.parseInt(split[0]);
        int equipnum = Integer.parseInt(split[1]);
        int sell_num = 0 ;
        if (equipnum <= 5) {
            // 售罄
            sell_num = equipnum;
            good = "0";
            // 维护货物位置信息
            places.remove(meta);
        } else {
            // 扣5件
            equipnum -= 5;
            sell_num = 5;
            good = equipid + "-" + equipnum;
        }
        // 保存货架信息
        goods.set(pos, good);
        // 记录收益
        EquipItem equip = EquipConfig.getInstance().getItem(equipid);
        int price = equip.getGoldsellprice() ;
        long goldsellprice = price * sell_num;
        int sell_exp = equip.getSell_exp() * sell_num;
        settleResult.add(shelfid,goldsellprice);
        settleResult.addExp(sell_exp);
        addBags("settle", BagType.GOLD, 0, (int) goldsellprice); // FIXME 可能因丢失精度而少加
        addBags("settle", BagType.EXP, 0, sell_exp);

        countTagManager.addTag(Const.USER_COUNT_TAG_TODAY_SELL, goldsellprice);
    }

    public void addTodaySell(long gold) {
        countTagManager.addTag(Const.USER_COUNT_TAG_TODAY_SELL, gold);
    }

    public long getTodaySell() {
        return countTagManager.getTagCount(Const.USER_COUNT_TAG_TODAY_SELL, TimeUtil.startOfDay());
    }

    private void addFlowerRecord(int type,String name , int flowerId,int time){
        JSONObject object = new JSONObject();
//        object.put("record",msg);
        object.put("type",type);
        object.put("name",name);
        object.put("flowerId",flowerId);
        object.put("time",time);
        flowerRecord.add(object);
        while (flowerRecord.size() > 20){
            flowerRecord.remove(0);
        }
    }
    public void addGetFlowerRecord(Player p , int flowerID ,int time ){
        //String msg = p.getName() + "送给我一朵"+flowerName ;
        addFlowerRecord(0,p.getName(),flowerID,time);
    }
    public void addSendFlowerRecord(Player p , int flowerID , int time ){
        //String msg =  "我送给"+p.getName()+"我一朵" + flowerName ;
        addFlowerRecord(1,p.getName(),flowerID,time);
    }

    /**
     * 记录充值数额
     *
     * @param payMoney
     * @param addDiamond 本次充值给的钻石 已发至玩家邮箱
     * @param sendDiamond 本次充值额外赠送的钻石 已发至玩家邮箱
     *
     * @return
     */
    //private static final Logger log = LoggerFactory.getLogger(Player.class);
    public int payCallBack(final int payMoney, final int type ,final int addDiamond, final int sendDiamond) {
        totalPay += payMoney;
        System.out.println("payCallBack payMoney = " + payMoney + " addDiamond = " + addDiamond + " sendDiamond = " + sendDiamond);
        //log.debug("payCallBack payMoney = " + payMoney + " addDiamond = " + addDiamond + " sendDiamond = " + sendDiamond);
        questEventListener.dispatchEvent(QuestEventId.PayTotal, totalPay);
        questEventListener.dispatchEvent(QuestEventId.PayFirst, totalPay);
        getCountTagManager().addTag(Const.USER_COUNT_TAG_PAY_TOTAL,totalPay);
        if (type == 2 && payMoney == 30){
            payMonthCard();
        }
        return totalPay;
    }
    private void payMonthCard(){
        //购买月卡
        int time = 30 * Const.DAY_SECOND;
        if (checkMonthCard()){
            int endTime = monthCard.getInteger("endTime");
            monthCard.put("endTime",endTime + time);
        }
        else {
            monthCard.put("startTime",TimeUtil.startOfDay());
            monthCard.put("endTime",TimeUtil.startOfDay() +  time);
            sendMonthCardReward();
        }
    }
    public boolean checkMonthCard(){
        int time = TimeUtil.nowInt();
        if (time < monthCard.getInteger("endTime") && time >= monthCard.getInteger("startTime")){
            return true;
        }
        else return false;
    }
    public void sendMonthCardReward(){
        if (checkMonthCard()){
            ArrayList<PropItem> attached = new ArrayList<PropItem>();
            PropItem reward =  new PropItem(BagType.DIAMOND, 0, 100);
            attached.add(reward);
            MailManager.getInstance().insertMail(
                getId(), 0, "系统邮件", "月卡奖励", "月卡奖励", attached, Mail.MTYPE_SYSTEM);
        }

    }
    public boolean haveExchangeCode(int batchId){
        if (exchangeCodeBatchIds.equals("")) return false;
        String[] idList = exchangeCodeBatchIds.split(",");
        for (int i = 0 ;i < idList.length; i ++){
            int id = Integer.parseInt(idList[i]);
            if (id == batchId) return true;
        }
        return false;
    }
    public void setExchangeBatchIdCode(int batchId){
        if (exchangeCodeBatchIds.equals("")){
            exchangeCodeBatchIds = Integer.toString(batchId);
        }
        else {
            exchangeCodeBatchIds += "," + Integer.toString(batchId);
        }
    }
    private String exchangeCodeBatchIds;

    public JSONObject getMonthCard() {
        return monthCard;
    }

    public void setMonthCard(JSONObject monthCard) {
        this.monthCard = monthCard;
    }

    private JSONObject monthCard ;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public JSONArray getLogo() {
        return logo;
    }

    public void setLogo(JSONArray logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public AtomicLong getDiamond() {
        return diamond;
    }

    public void setDiamond(long diamond) {
        this.diamond.set(diamond);
    }

    public PlayerShops getShops() {
        return shops;
    }

    public void setShops(PlayerShops shops) {
        this.shops = shops;
    }

    public JSONObject getEquips() {
        return equips;
    }

    public void setEquips(JSONObject equips) {
        this.equips = equips;
    }

    public int getVipExp() {
        return vipExp;
    }

    public void setVipExp(int vipExp) {
        this.vipExp = vipExp;
    }

    public PlayerPvp getPvp() {
        return pvp;
    }

    public void setPvp(PlayerPvp pvp) {
        this.pvp = pvp;
    }

    public short getHealth() {
        return health;
    }

    public void setHealth(short health) {
        this.health = health;
    }

    public short getCharm() {
        return charm;
    }

    public void setCharm(short charm) {
        this.charm = charm;
    }

    public int getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(int totalPay) {
        this.totalPay = totalPay;
    }

    public int getRegTime() {
        return regTime;
    }

    public void setRegTime(int regTime) {
        this.regTime = regTime;
    }

    public int getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(int loginTime) {
        this.loginTime = loginTime;
    }

    public int getApple() {
        return apple;
    }

    public void setApple(int apple) {
        this.apple = apple;
    }

    public short getHead() {
        return head;
    }

    public void setHead(short head) {
        this.head = head;
    }

    public JSONObject getItems() {
        return items;
    }

    public void setItems(JSONObject items) {
        this.items = items;
    }

    public int getEquipsCapacity() {
        return equipsCapacity;
    }

    public void setEquipsCapacity(int equipsCapacity) {
        this.equipsCapacity = equipsCapacity;
    }

    public JSONObject getFurnitures() {
        return furnitures;
    }

    public void setFurnitures(JSONObject furnitures) {
        this.furnitures = furnitures;
    }

    public JSONObject getDecorateFurnitures() {
        return decorateFurnitures;
    }

    public void setDecorateFurnitures(JSONObject decorateFurnitures) {
        this.decorateFurnitures = decorateFurnitures;
    }

    public int getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(int settleTime) {
        this.settleTime = settleTime;
    }

    public byte getSettlePeriod() {
        return settlePeriod;
    }

    public void setSettlePeriod(byte settlePeriod) {
        this.settlePeriod = settlePeriod;
    }

    /*
    "logistics":[
        {
            "beginTime":1499918943,
            "decredTime":0,
            "equip":{
                "700487":20
            },
            "id":0,
            "level":1
        },
        {
            "beginTime":1499918920,
            "decredTime":0,
            "equip":{
                "700385":20
            },
            "id":1,
            "level":1
        }
    ],
     */
    public JSONArray getLogistics() {
        return logistics;
    }

    public void setLogistics(JSONArray logistics) {
        this.logistics = logistics;
    }

    public JSONObject getFaces() {
        return faces;
    }

    public void setFaces(JSONObject faces) {
        this.faces = faces;
    }

    public JSONObject getFaceFrags() {
        return faceFrags;
    }

    public void setFaceFrags(JSONObject faceFrags) {
        this.faceFrags = faceFrags;
    }

    public JSONArray getGotEquips() {
        return gotEquips;
    }

    public void setGotEquips(JSONArray gotEquips) {
        this.gotEquips = gotEquips;
    }

    public JSONObject getDressRoom() {
        return dressRoom;
    }

    public void setDressRoom(JSONObject dressRoom) {
        this.dressRoom = dressRoom;
    }

    public JSONArray getWindows() {
        return windows;
    }

    public void setWindows(JSONArray windows) {
        this.windows = windows;
    }

    public JSONArray getGarden() {
        return garden;
    }

    public void setGarden(JSONArray garden) {
        this.garden = garden;
    }

    public JSONObject getDraws() {
        return draws;
    }

    public void setDraws(JSONObject draws) {
        this.draws = draws;
    }

    public JSONArray getFlowerPots() {
        return flowerPots;
    }

    public void setFlowerPots(JSONArray flowerPots) {
        this.flowerPots = flowerPots;
    }

    public JSONArray getFactorys() {
        return factorys;
    }

    public void setFactorys(JSONArray factorys) {
        this.factorys = factorys;
    }

    public JSONArray getFactoryEquips() {
        return factoryEquips;
    }

    public void setFactoryEquips(JSONArray factoryEquips) {
        this.factoryEquips = factoryEquips;
    }

    public JSONArray getCashRegisters() {
        return cashRegisters;
    }

    public void setCashRegisters(JSONArray cashRegisters) {
        this.cashRegisters = cashRegisters;
    }

    public JSONObject getEmployees() {
        return employees;
    }

    public void setEmployees(JSONObject employees) {
        this.employees = employees;
    }

    public boolean isRenameChance() {
        return renameChance;
    }

    public void setRenameChance(boolean renameChance) {
        this.renameChance = renameChance;
    }

    public JSONObject getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(JSONObject checkIn) {
        this.checkIn = checkIn;
    }

    public List<Long> getFriendRequestList() {
        return friendRequestList;
    }

    public void setFriendRequestList(List<Long> friendRequestList) {
        this.friendRequestList = friendRequestList;
    }

    public List<Long> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Long> friendList) {
        this.friendList = friendList;
    }

    public JSONArray getNpcFriendList() {
        return npcFriendList;
    }

    public void setNpcFriendList(JSONArray npcFriendList) {
        this.npcFriendList = npcFriendList;
    }

    public int getMsgReadTime() {
        return msgReadTime;
    }

    public void setMsgReadTime(int msgReadTime) {
        this.msgReadTime = msgReadTime;
    }

    public void setCharmRank(int rank) {
        charmRank = rank;
    }

    public int getCharmRank() {
        return charmRank;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }
}
