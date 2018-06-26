package com.xxgames.demo.config.item;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgames.util.MathUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/17.
 */
public class GardenItem {

    public class GardenEquipRate{
        public int Id;
        public int Rate;
        public GardenEquipRate(int id, int rate){
            Id = id; Rate = rate;
        }
    }

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

    public int getApple() {
        return apple;
    }

    public void setApple(int apple) {
        this.apple = apple;
    }

    public String getEquips() {
        return equips;
    }

    public void setEquips(String equips) {
        //this.equips = equips;
        ArrayList<ArrayList<Integer>> list = JSON.parseObject(equips, new TypeReference<ArrayList<ArrayList<Integer>>>(){});
        for(int i=0; i<list.get(0).size(); i++){
            //boxArray.add(new BoxItem(item.get(0), item.get(1), item.get(2), item.get(3)));
            gardenEquipRates.add(new GardenEquipRate(list.get(0).get(i),
                list.get(1).get(i)));
        }
    }

    public ArrayList<Integer> getSeeds() {
        return seeds;
    }

    public void setSeeds(ArrayList<Integer> seeds) {
        this.seeds = seeds;
    }

    public ArrayList<Integer> getSeedawardrate() {
        return seedawardrate;
    }

    public void setSeedawardrate(ArrayList<Integer> seedawardrate) {
        this.seedawardrate = seedawardrate;
    }

    public ArrayList<Integer> getExtra() {
        return extra;
    }

    public void setExtra(ArrayList<Integer> extra) {
        this.extra = extra;
    }

    public ArrayList<GardenEquipRate> getGardenEquipRate(){
        return gardenEquipRates;
    }

    public int getBuyTime(){
        if(extra == null) return 0;
        return extra.get(0);
    }

    public int getBuyCD(){
        if(extra == null) return 0;
        return extra.get(1);
    }

    public int getRandomGardenSeed(){
        int rand = (int)(MathUtil.getRamdomDouble()*100);
        int rate = 0;
        int itemid = 1;
        for(int i=0; i<seedawardrate.size(); i++){
            rate += seedawardrate.get(i);
            if(rate > rand) {
                itemid = seeds.get(i);
                break;
            }
        }
        return itemid;
    }

    public int getRandomGardenEquip(){
        int rand = (int)(MathUtil.getRamdomDouble()*10000); //概率总和为10000
        int rate = 0;
        int itemid = 0;
        for(GardenEquipRate item : gardenEquipRates){
            rate += item.Rate;
            if(rate > rand){
                itemid = item.Id;
                break;
            }
        }
        return itemid;
    }

    private int id;
    private String name;
    private int gold;
    private int diamond;
    private int apple;
    private String equips;
    private ArrayList<Integer> seeds;
    private ArrayList<Integer> seedawardrate;
    private ArrayList<Integer> extra;           //[num, time]每天购买次数，CD时间

    private ArrayList<GardenEquipRate> gardenEquipRates= new ArrayList<GardenEquipRate>();
}
