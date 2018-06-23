package com.ppgames.demo.config.item;


import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/13.
 */
public class Item {
    private int id;
    private String name;
    private int type;
    private String desc;
    private int overlapnum;
    private int rarity;
    //private int drop_num = 0;
    //private String extra;
    private int canuse;
    private int goldbuyprice;
    private int goldsellprice;
    private String iconPath;
    private int quality;
    /*[2,500896,1,99] 类型 ID 数量 概率*/
    private ArrayList<BoxItem> boxArray = new ArrayList<BoxItem>();

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getOverlapnum() {
        return overlapnum;
    }

    public void setOverlapnum(int overlapnum) {
        this.overlapnum = overlapnum;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

//    public int getDrop_num() {
//        return drop_num;
//    }
//
//    public void setDrop_num(int drop_num) {
//        this.drop_num = drop_num;
//    }
//
//    public String getExtra() {
//        return extra;
//    }
//
//    public void setExtra(String extra) {
//        ArrayList<ArrayList<Integer>> list = JSON.parseObject(extra, new TypeReference<ArrayList<ArrayList<Integer>>>(){});
//        for(ArrayList<Integer> item: list){
//            boxArray.add(new BoxItem(item.get(0), item.get(1), item.get(2), item.get(3)));
//        }
//    }

    public int getCanuse() {
        return canuse;
    }

    public void setCanuse(int canuse) {
        this.canuse = canuse;
    }

    public int getGoldbuyprice() {
        return goldbuyprice;
    }

    public void setGoldbuyprice(int goldbuyprice) {
        this.goldbuyprice = goldbuyprice;
    }

    public int getGoldsellprice() {
        return goldsellprice;
    }

    public void setGoldsellprice(int goldsellprice) {
        this.goldsellprice = goldsellprice;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public ArrayList<BoxItem> getBoxArray(){
        return boxArray;
    }
}
