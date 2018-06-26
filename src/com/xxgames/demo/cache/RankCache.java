package com.xxgames.demo.cache;

import com.alibaba.fastjson.JSONArray;
import com.xxgames.demo.config.config.RobotConfig;
import com.xxgames.demo.config.item.RobotItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.battle.PvpRanks;
import com.xxgames.demo.rank.RankData;
import com.xxgames.demo.utils.Const;

public class RankCache {
    public static JSONArray pvpranks = new JSONArray();

    public static synchronized JSONArray getPvpranks() {
        if (pvpranks.size() == 0) {
            refreshPvpRanks();
        }
        return pvpranks;
    }

    public static synchronized void refreshPvpRanks() {
        pvpranks.clear();
        for (int i = 0; i < Const.MAX_RANK_PEOPLE; i++) {
            long pid = PvpRanks.getPidByRank(i);
            RankData rank = new RankData();
            rank.setPid(pid);
            rank.setRank(i);
            if (pid == RobotConfig.robot_id) {
                //Robot robot = Robots.getRobot(i);
                RobotItem config = RobotConfig.getInstance().getItem(i);
                rank.setName(config.getName());
                rank.setLevel(config.getLevel());
                JSONArray equip = new JSONArray();
                equip.addAll(config.getEquips());
                rank.setEquips(equip);
                JSONArray logo = new JSONArray();
                logo.addAll(config.getLogo());
                rank.setHeents(logo);
                rank.setVipLevel(config.getVip());
                rank.setRankScore(config.getLevel());
            } else {
                Player p = Cache.players.get(pid);
                rank.setName(p.getName());
                rank.setLevel(p.getLevel());
                rank.setEquips(p.getDressRoom().getJSONArray("equips"));
                rank.setHeents(p.getLogo());
                rank.setVipLevel(p.getVipLevel());
                rank.setRankScore(p.getLevel());
            }
            pvpranks.add(i, rank);
        }
    }

}
