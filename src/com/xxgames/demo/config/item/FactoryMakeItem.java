package com.xxgames.demo.config.item;

/**
 * Created by Administrator on 2017/7/15.
 */
public class FactoryMakeItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlueprintid() {
        return blueprintid;
    }

    public void setBlueprintid(int blueprintid) {
        this.blueprintid = blueprintid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int id;
    private int blueprintid;
    private int price;
}
