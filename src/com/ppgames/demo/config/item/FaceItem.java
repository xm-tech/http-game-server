package com.ppgames.demo.config.item;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class FaceItem {
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

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getCombine_frag() {
        return combine_frag;
    }

    public void setCombine_frag(int combine_frag) {
        this.combine_frag = combine_frag;
    }

    public ArrayList<Integer> getExp() {
        return exp;
    }

    public void setExp(ArrayList<Integer> exp) {
        this.exp = exp;
    }

    public int getAdd_exp() {
        return add_exp;
    }

    public void setAdd_exp(int add_exp) {
        this.add_exp = add_exp;
    }

    public String getBuff() {
        return buff;
    }

    public void setBuff(String buff) {
        this.buff = buff;
        ArrayList<ArrayList<Integer>> list = JSON.parseObject(buff, new TypeReference<ArrayList<ArrayList<Integer>>>(){});
        for(ArrayList<Integer> item: list){
            buffs.add(new FaceBuffItem(item.get(0), item.get(1), item.get(2), item.get(3)));
        }
    }

    public FaceBuffItem getBuff(int styleid){
        for(FaceBuffItem item: buffs){
            if(item.getStyleId() == styleid)
                return item;
        }
        return null;
    }

    private int id;
    private String name;
    private int type;
    private int quality;
    private int combine_frag;
    private ArrayList<Integer> exp;
    private int add_exp;
    private String buff;
    private ArrayList<FaceBuffItem> buffs = new ArrayList<FaceBuffItem>();
}
