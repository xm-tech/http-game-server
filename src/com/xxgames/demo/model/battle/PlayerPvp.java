package com.xxgames.demo.model.battle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.RobotConfig;
import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.config.item.RobotItem;
import com.xxgames.demo.model.Player;
import com.xxgames.util.EDressStyle;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 玩家pvp信息抽象
 */
@JSONType(ignores = { "buyBattleNumDiamond", "refreshDiamond", "canBattleNum" })
public class PlayerPvp {

    // 当日已购买挑战的次数(购买1次+5次)
    int buybattlenum;
    int buybattletime;
    int battlednum;
    int battletime;
    // 当日已刷新对手次数
    int refreshednum;
    int refreshedtime;
    int crystal;
    JSONArray record;
    int styleid;
    JSONObject stylebest;
    int rankawardtime;
    AtomicInteger rank;
    JSONArray enemyranks;
    // 上次对手列表刷新的时间戳
    int enemyrefreshtime;
    // 上次自己名次变化的时间戳
    int rankchangetime;

    transient boolean isNew;


    public PlayerPvp() {
        buybattlenum = 0;
        buybattletime = 0;
        battlednum = 0;
        battletime = 0;
        refreshednum = 0;
        refreshedtime = 0;
        crystal = 0;
        record = new JSONArray();
        styleid = 0; // 随机
        stylebest = new JSONObject();
        rankawardtime = 0;
        rank = new AtomicInteger(PvpRanks.max_rank + 1); // 默认都在榜单外
        enemyranks = new JSONArray();
        enemyrefreshtime = 0;
        rankchangetime = 0;
        isNew = true;
    }

    public PlayerPvp(JSONObject pvp) {
        this();
        if (pvp == null) {
            return;
        }
        buybattlenum = pvp.getIntValue("buybattlenum");
        buybattletime = pvp.getIntValue("buybattletime");
        battlednum = pvp.getIntValue("battlednum");
        battletime = pvp.getIntValue("battletime");
        buybattlenum = pvp.getIntValue("buybattlenum");
        refreshednum = pvp.getIntValue("refreshednum");
        refreshedtime = pvp.getIntValue("refreshedtime");
        crystal = pvp.getIntValue("crystal");
        record = pvp.getJSONArray("record");
        styleid = pvp.getIntValue("styleid");
        stylebest = pvp.getJSONObject("stylebest");
        rank = new AtomicInteger(pvp.getIntValue("rank"));
        enemyranks = pvp.getJSONArray("enemyranks");
        enemyrefreshtime = pvp.getIntValue("enemyrefreshtime");
        rankchangetime = pvp.getIntValue("rankchangetime");
        isNew = false;
    }

    /**
     * 重新生成1组对手名次
     */
    public void refreshEnemyRanks() {
        ArrayList<Integer> enemyRanks = PvpRankRule.getEnemyRanks(rank.get());
        enemyranks = new JSONArray();
        for (int enemyRank : enemyRanks) {
            enemyranks.add(enemyRank);
        }
        styleid = EDressStyle.randomOneStyle();
    }

    /**
     * 根据对手名次生成对手数据
     *
     * @return
     */
    public JSONArray genEnemys() {
        JSONArray enemys = new JSONArray();
        for (int i = 0; i < enemyranks.size(); i++) {
            int enemyRank = enemyranks.getIntValue(i);
            if (enemyRank > 1000) {
                continue;
            }
            long enemyPid = PvpRanks.getPidByRank(enemyRank);
            JSONObject enemy = new JSONObject();
            enemy.put("pid", enemyPid);
            enemy.put("rank", enemyRank);
            if (enemyPid == RobotConfig.robot_id) {
                // npc
                RobotItem config = RobotConfig.getInstance().getItem(enemyRank);
                enemy.put("name", config.getName());
                enemy.put("level", config.getLevel());
                enemy.put("heents", config.getLogo());
                enemy.put("equips", config.getEquips());
            } else {
                // player
                Player enemyPlayer = Cache.players.get(enemyPid);
                enemy.put("name", enemyPlayer.getName());
                enemy.put("level", enemyPlayer.getLevel());
                enemy.put("heents", enemyPlayer.getLogo());
                if (enemyPlayer.getPvp().stylebest.containsKey(styleid + "")) {
                    enemy.put("equips", enemyPlayer.getPvp().stylebest.getJSONArray(styleid + ""));
                } else {
                    // 对手未曾pk过该风格,简单拿对手试衣间数据
                    JSONArray equips = enemyPlayer.getDressRoom().getJSONArray("equips");
                    enemy.put("equips", equips);
                    enemyPlayer.getPvp().stylebest.put("styleid", equips);
                }
            }
            enemys.add(enemy);
        }
        return enemys;
    }

    public void addCrystal(int crystalAdd) {
        crystal += crystalAdd;
    }

    public void decrCrystal(int decr) {
        crystal += decr;
    }

    public void addBattleNum(int addNum, int time) {
        battlednum += addNum;
        setBattletime(time);
    }

    public void addBuyBattleNum(int addNum, int time) {
        buybattlenum += addNum;
        setBuybattletime(time);
    }

    public void addRefreshedNum(int addNum, int time) {
        refreshednum += addNum;
        setRefreshedtime(time);
    }

    public int getBuyBattleNumDiamond() {
        int needDiamond = 0;
        int buybattlenum = getBuybattlenum() + 1;
        int battlenum = getBattlednum();
        if (battlenum >= SystemConfConfig.getInstance().getCfg().getPvp_battle_freee_num()){
            needDiamond = 10 * buybattlenum;
        }
        int maxDiamond = SystemConfConfig.getInstance().getCfg().getPvp_buybattlenum_max_diamond();
        return needDiamond > maxDiamond? maxDiamond : needDiamond;
    }


    public int getRefreshDiamond() {
        if (getRefreshednum() >= SystemConfConfig.getInstance().getCfg().getPvp_refresh_free_num()){
            // 每日前3次免费,之后每次5钻
            return SystemConfConfig.getInstance().getCfg().getPvp_refresh_diamond();
        }
        return 0;
    }

    public int getBuybattlenum() {
        return buybattlenum;
    }

    public void setBuybattlenum(int buybattlenum) {
        this.buybattlenum = buybattlenum;
    }

    public int getBuybattletime() {
        return buybattletime;
    }

    public void setBuybattletime(int buybattletime) {
        this.buybattletime = buybattletime;
    }

    public int getBattlednum() {
        return battlednum;
    }

    public void setBattlednum(int battlednum) {
        this.battlednum = battlednum;
    }

    public int getBattletime() {
        return battletime;
    }

    public void setBattletime(int battletime) {
        this.battletime = battletime;
    }

    public int getRefreshednum() {
        return refreshednum;
    }

    public void setRefreshednum(int refreshednum) {
        this.refreshednum = refreshednum;
    }

    public int getRefreshedtime() {
        return refreshedtime;
    }

    public void setRefreshedtime(int refreshedtime) {
        this.refreshedtime = refreshedtime;
    }

    public int getCrystal() {
        return crystal;
    }

    public void setCrystal(int crystal) {
        this.crystal = crystal;
    }

    public JSONArray getRecord() {
        return record;
    }

    public void setRecord(JSONArray record) {
        this.record = record;
    }

    public int getStyleid() {
        return styleid;
    }

    public void setStyleid(int styleid) {
        this.styleid = styleid;
    }

    public JSONObject getStylebest() {
        return stylebest;
    }

    public void setStylebest(JSONObject stylebest) {
        this.stylebest = stylebest;
    }

    public int getRankawardtime() {
        return rankawardtime;
    }

    public void setRankawardtime(int rankawardtime) {
        this.rankawardtime = rankawardtime;
    }

    public AtomicInteger getRank() {
        return rank;
    }

    public void setRank(AtomicInteger rank) {
        this.rank = rank;
    }

    public JSONArray getEnemyranks() {
        return enemyranks;
    }

    public void setEnemyranks(JSONArray enemyranks) {
        this.enemyranks = enemyranks;
    }

    public int getEnemyrefreshtime() {
        return enemyrefreshtime;
    }

    public void setEnemyrefreshtime(int enemyrefreshtime) {
        this.enemyrefreshtime = enemyrefreshtime;
    }

    public int getRankchangetime() {
        return rankchangetime;
    }

    public void setRankchangetime(int rankchangetime) {
        this.rankchangetime = rankchangetime;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean noEnemys() {
        return enemyranks.size() == 0;
    }

    public int canBattleNum() {
        // 每日可免费挑战次数
        int pvp_battle_freee_num = SystemConfConfig.getInstance().getCfg().getPvp_battle_freee_num();
        return pvp_battle_freee_num + buybattlenum * 5 - battlednum;
    }

}
