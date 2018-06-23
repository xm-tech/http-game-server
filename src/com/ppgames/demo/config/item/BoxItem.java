package com.ppgames.demo.config.item;

/**
 * Created by Administrator on 2017/7/15.
 */
/*[2,500896,1,99] 类型 ID 数量 概率*/
public class BoxItem {
    private int type;
    private int id;
    private int num;
    private int rate;

    public BoxItem(int type, int id, int num, int rate){
        this.type = type;
        this.id = id;
        this.num = num;
        this.rate = rate;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public int getNum() {
        return num;
    }

    public int getRate() {
        return rate;
    }

    public PropItem getPropItem(){
        return new PropItem(type , id , num);
    }
}
