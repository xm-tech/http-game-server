package com.ppgames.demo.config.item;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;

/**
 * Created by PhonePadPC on 2017/7/17.
 */
public class GoodsBoxItem {
    public GoodsBoxItem(){
        id = 0 ;
        box_1 = "" ;
        box_2 = "" ;
        box_3 = "" ;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBox_1() {
        return box_1;
    }

    public void setBox_1(String box_1) {
        ArrayList<ArrayList<Integer>> list = JSON.parseObject(box_1, new TypeReference<ArrayList<ArrayList<Integer>>>(){});
        for(ArrayList<Integer> item: list){
            dropItemsArray1.add(new BoxItem(item.get(0), item.get(1), item.get(2), item.get(3)));
        }
    }

    public String getBox_2() {
        return box_2;
    }

    public void setBox_2(String box_2) {
        ArrayList<ArrayList<Integer>> list = JSON.parseObject(box_2, new TypeReference<ArrayList<ArrayList<Integer>>>(){});
        for(ArrayList<Integer> item: list){
            dropItemsArray2.add(new BoxItem(item.get(0), item.get(1), item.get(2), item.get(3)));
        }
    }

    public String getBox_3() {
        return box_3;
    }

    public void setBox_3(String box_3) {
        ArrayList<ArrayList<Integer>> list = JSON.parseObject(box_3, new TypeReference<ArrayList<ArrayList<Integer>>>(){});
        for(ArrayList<Integer> item: list){
            dropItemsArray3.add(new BoxItem(item.get(0), item.get(1), item.get(2), item.get(3)));
        }
    }

    private int id ;
    private String box_1;
    private String box_2;
    private String box_3;

    public ArrayList<BoxItem> getDropItemsArray1() {
        return dropItemsArray1;
    }

    public ArrayList<BoxItem> getDropItemsArray2() {
        return dropItemsArray2;
    }

    public ArrayList<BoxItem> getDropItemsArray3() {
        return dropItemsArray3;
    }

    private ArrayList<BoxItem> dropItemsArray1 = new ArrayList<BoxItem>();
    private ArrayList<BoxItem> dropItemsArray2 = new ArrayList<BoxItem>();
    private ArrayList<BoxItem> dropItemsArray3 = new ArrayList<BoxItem>();
}
