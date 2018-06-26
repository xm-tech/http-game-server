package com.xxgames.demo.rank;

import com.alibaba.fastjson.JSONArray;

/**
 * Created by PhonePadPC on 2017/7/10.
 */
public class RankData {
    private long pid;
    private String name;
    private int rank;
    private int level;
    private int vipLevel;
    private JSONArray equips;
    private JSONArray heents;
    private int rankScore;

    public RankData() {
    }
    public int getRankScore() {
        return rankScore;
    }

    public void setRankScore(int rankScore) {
        this.rankScore = rankScore;
    }
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public JSONArray getEquips() {
        return equips;
    }

    public void setEquips(JSONArray equips) {
        this.equips = equips;
    }

    public JSONArray getHeents() {
        return heents;
    }

    public void setHeents(JSONArray heents) {
        this.heents = heents;
    }
}
