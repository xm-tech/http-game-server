package com.xxgames.demo.config.item;

/**
 * Created by Administrator on 2017/7/15.
 */
public class WholeSaleItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNeed_level() {
        return need_level;
    }

    public void setNeed_level(int need_level) {
        this.need_level = need_level;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int id;
    private int need_level;
    private int price;
}
