package com.ppgames.demo.activity;

import java.util.Map;

/**
 * Created by PhonePadPC on 2017/9/1.
 */
public class ActivityBuy extends ActivityBase {
    public ActivityBuy(Map m){
        super(m);
        price = Integer.parseInt(m.get("price").toString());

    }

    public int getPrice() {
        return price;
    }

    private int price ;

}
