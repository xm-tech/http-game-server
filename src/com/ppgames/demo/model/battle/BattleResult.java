package com.ppgames.demo.model.battle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.util.TimeUtil;

public class BattleResult {
    protected static int winner_me = 0;
    protected static int winner_other = 1;

    public int winner;
    // 新的styleid
    public int styleid;
    JSONArray score0;
    JSONArray score1;
    // 水晶奖励
    int crystal;
    int battleTime;
    // 本次挑战的对手信息
    JSONObject enemy;
    // 挑战者战后名次
    int rank;
    // 下批对手
    JSONArray enemys;


    public BattleResult() {
        battleTime = TimeUtil.nowInt();
    }

    public static void main(String[] args) {
        BattleResult resut = new BattleResult();
        resut.score0 = new JSONArray();
        resut.score1 = new JSONArray();
        resut.crystal = 10;
        resut.winner = winner_me;

        JSONObject retObj = (JSONObject) JSON.toJSON(resut);
        System.out.println(retObj);
    }

    /**
     * 攻方胜 ?
     *
     * @return
     */
    public boolean win() {
        return winner == winner_me;
    }

    public JSONArray getScore0() {
        return score0;
    }

    public void setScore0(JSONArray score0) {
        this.score0 = score0;
    }

    public JSONArray getScore1() {
        return score1;
    }

    public void setScore1(JSONArray score1) {
        this.score1 = score1;
    }

    public int getCrystal() {
        return crystal;
    }

    public void setCrystal(int crystal) {
        this.crystal = crystal;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getBattleTime() {
        return battleTime;
    }

    public void setBattleTime(int battleTime) {
        this.battleTime = battleTime;
    }

    public JSONObject getEnemy() {
        return enemy;
    }

    public void setEnemy(JSONObject enemy) {
        this.enemy = enemy;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public JSONArray getEnemys() {
        return enemys;
    }

    public void setEnemys(JSONArray enemys) {
        this.enemys = enemys;
    }

    public int getStyleid() {
        return styleid;
    }

    public void setStyleid(int styleid) {
        this.styleid = styleid;
    }

    public JSONObject toJSON() {
        return (JSONObject) JSON.toJSON(this);
    }
}
