package com.ppgames.demo.config.item;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/18.
 */
public class RobotItem {
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ArrayList<Integer> getEquips() {
        return equips;
    }

    public void setEquips(ArrayList<Integer> equips) {
        this.equips = equips;
    }

    public ArrayList<Integer> getLogo() {
        return logo;
    }

    public void setLogo(ArrayList<Integer> logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }

    public void setScore(ArrayList<Integer> score) {
        this.score = score;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    private int rank;
    private ArrayList<Integer> equips;
    private ArrayList<Integer> logo;
    private String name;
    private int level;
    private ArrayList<Integer> score;
    private int vip;
}
