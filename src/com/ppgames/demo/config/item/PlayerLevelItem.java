package com.ppgames.demo.config.item;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class PlayerLevelItem {
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public ArrayList<Integer> getAwards() {
        return awards;
    }

    public void setAwards(ArrayList<Integer> awards) {
        this.awards = awards;
    }

    public int getTalk() {
        return talk;
    }

    public void setTalk(int talk) {
        this.talk = talk;
    }

    public ArrayList<Integer> getFuncs() {
        return funcs;
    }

    public void setFuncs(ArrayList<Integer> funcs) {
        this.funcs = funcs;
    }

    private int level;
    private int exp;
    private ArrayList<Integer> awards;
    private int talk;
    private ArrayList<Integer> funcs;
}
