package com.xxgames.demo.config.item;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class WindowItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuylevel() {
        return buylevel;
    }

    public void setBuylevel(int buylevel) {
        this.buylevel = buylevel;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public ArrayList<Integer> getFace() {
        return face;
    }

    public void setFace(ArrayList<Integer> face) {
        this.face = face;
    }

    public ArrayList<Integer> getEquips() {
        return equips;
    }

    public void setEquips(ArrayList<Integer> equips) {
        this.equips = equips;
    }

    private int id;
   private String name;
   private int buylevel;
   private int gold;
   private int diamond;
   private ArrayList<Integer> face;
   private ArrayList<Integer> equips;

}
