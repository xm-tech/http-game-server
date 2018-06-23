package com.ppgames.demo.config.item;

/**
 * Created by PhonePadPC on 2017/8/11.
 */
public class BlueprintItem {
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

    public int getGoldsellprice() {
        return goldsellprice;
    }

    public void setGoldsellprice(int goldsellprice) {
        this.goldsellprice = goldsellprice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getEquip_id() {
        return equip_id;
    }

    public void setEquip_id(int equip_id) {
        this.equip_id = equip_id;
    }

    private int id  ;
    private String name ;
    private int goldsellprice;
    private String desc;
    private int equip_id;
}
