package com.xxgames.util;

/**
 * Created by PhonePadPC on 2017/7/17.
 */
public class RandBucketItem<T> {
    public RandBucketItem(T param , int weight){
        this.param = param;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public T getParam() {
        return param;
    }

    private int weight;
    private T param;


}
