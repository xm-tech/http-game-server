package com.xxgames.demo.config.item;

import java.util.List;
/**
 * Created by Administrator on 2017/7/13.
 */
public class EquipItem {
    private int id;
    private int type;
    private String iconPath;
    private String name;
    private int quality;
    private int goldbuyprice;
    private int diamondbuyprice;
    private int goldsellprice;
    private int sell_exp;
    private List<Integer> score;
    private String desc;
    private int overlapnum;
    private int styleid;
    private int colorid;
    private List<Integer> affixids;
    private int unitid;
    private String gettips;
    private int rarity;
    private List<Integer> footfall;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
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

    public int getGoldsellprice() {
        return goldsellprice;
    }

    public void setGoldsellprice(int goldsellprice) {
        this.goldsellprice = goldsellprice;
    }

    public int getSell_exp() {
        return sell_exp;
    }

    public void setSell_exp(int sell_exp) {
        this.sell_exp = sell_exp;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
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

    public int getStyleid() {
        return styleid;
    }

    public void setStyleid(int styleid) {
        this.styleid = styleid;
    }

    public int getColorid() {
        return colorid;
    }

    public void setColorid(int colorid) {
        this.colorid = colorid;
    }

    public List<Integer> getAffixids() {
        return affixids;
    }

    public void setAffixids(List<Integer> affixids) {
        this.affixids = affixids;
    }

    public int getUnitid() {
        return unitid;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

    public String getGettips() {
        return gettips;
    }

    public void setGettips(String gettips) {
        this.gettips = gettips;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public List<Integer> getFootfall() {
        return footfall;
    }

    public void setFootfall(List<Integer> footfall) {
        this.footfall = footfall;
    }
}
