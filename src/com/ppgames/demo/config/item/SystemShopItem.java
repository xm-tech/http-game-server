package com.ppgames.demo.config.item;

/**
 * Created by Administrator on 2017/7/15.
 */
public class SystemShopItem {
    private int id;
    private int itemid;
    private int type;
    private int goldbuyprice;
    private int diamondbuyprice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGoldbuyprice() {
        return goldbuyprice;
    }

    public void setGoldbuyprice(int goldbuyprice) {
        this.goldbuyprice = goldbuyprice;
    }

    public int getDiamondbuyprice() {
        return diamondbuyprice;
    }

    public void setDiamondbuyprice(int diamondbuyprice) {
        this.diamondbuyprice = diamondbuyprice;
    }

    public int getNeed_level() {
        return need_level;
    }

    public void setNeed_level(int need_level) {
        this.need_level = need_level;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private int need_level;
    private int num;
}
