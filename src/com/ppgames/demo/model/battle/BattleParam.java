package com.ppgames.demo.model.battle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.demo.DataManager;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.config.RobotConfig;
import com.ppgames.demo.config.item.RobotItem;
import com.ppgames.demo.model.Player;

// 对手的配置为该风格最好穿搭
public class BattleParam {

    JSONArray equips;
    JSONArray logo;
    String name;

    JSONArray score;

    long pid;

    public BattleParam() {
        score = new JSONArray();
        for (int i = 0; i < 10; i++) {
            score.add(0);
        }
    }

    // 进攻方构造器
    public BattleParam(Player p, JSONArray equips) {
        this();
        this.equips = equips;
        logo = p.getLogo();
        name = p.getName();
        pid = p.getId();
        int styleid = p.getPvp().styleid;
        computeAndSetScore(p,styleid, p.getFaceBuff(styleid), equips);
    }

    // 防守方构造器
    public BattleParam(int rank, int styleid) {
        this();
        long pid = PvpRanks.getPidByRank(rank);
        if (pid == RobotConfig.robot_id) {
            // npc
            RobotItem config = RobotConfig.getInstance().getItem(rank);
            equips = new JSONArray();
            equips.addAll(config.getEquips());
            logo = new JSONArray();
            logo.addAll(config.getLogo());
            name = config.getName();
            score = new JSONArray();
            score.addAll(config.getScore());
            this.pid = RobotConfig.robot_id;
        } else {
            // player
            Player p = Cache.players.get(pid);
            JSONObject stylebest = p.getPvp().stylebest;
            JSONArray styleBestEquips = stylebest.getJSONArray(String.valueOf(styleid));
            if (styleBestEquips == null) {
                // 对手未曾pk过该风格,简单拿对手试衣间数据 FIXME 从图鉴里面随机1套 : (头,上装,下装,外套,鞋子,饰品) 各1件
                styleBestEquips = p.getDressRoom().getJSONArray("equips");
                p.getPvp().stylebest.put("styleid", styleBestEquips);
            }
            equips = styleBestEquips;
            // 五官显示和buff都按当前最新的五官
            logo = p.getLogo();
            name = p.getName();
            this.pid = p.getId();
            computeAndSetScore(p,styleid, p.getFaceBuff(styleid), equips);
        }
    }

    public void computeAndSetScore(Player player,int styleid, double styleBuff, JSONArray equips) {
        for (int i = 0; i < equips.size(); i++) {
            int equipid = equips.getIntValue(i);
            int equipStyle = DataManager.getEquipStyle(equipid);
            int equipType = DataManager.getEquipType(equipid);
            int baseScore = DataManager.getEquipScore(equipid).get(styleid == equipStyle ? 1 : 0);
            int scoreIndex = DataManager.pvp_part_index.get(equipType);
            // 该部位得分
            int indexScore = score.getIntValue(scoreIndex);
            indexScore += baseScore * (1 + styleBuff) * player.getLevel();
            score.set(scoreIndex, indexScore);
        }
    }

    public JSONArray getEquips() {
        return equips;
    }

    public void setEquips(JSONArray equips) {
        this.equips = equips;
    }

    public JSONArray getLogo() {
        return logo;
    }

    public void setLogo(JSONArray logo) {
        this.logo = logo;
    }

    public JSONArray getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRobt() {
        return pid == RobotConfig.robot_id;
    }

    public long getPid() {
        return pid;
    }
}
