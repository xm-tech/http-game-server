package com.xxgames.demo.config;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
public class ShelfLevel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public List<Integer> getPosition_num() {
        return position_num;
    }

    public void setPosition_num(List<Integer> position_num) {
        this.position_num = position_num;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Integer> getSize() {
        return size;
    }

    public void setSize(List<Integer> size) {
        this.size = size;
    }

    private int id;
    private int level;
    private int gold;
    private List<Integer> position_num;
    private String icon;
    private List<Integer> size;
}
