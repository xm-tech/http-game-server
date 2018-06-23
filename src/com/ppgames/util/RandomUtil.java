package com.ppgames.util;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtil {

    private static final Random random = new Random();

    public static int nextInt(int min, int max) {
        int r = random.nextInt(max);
        while (r <= min) {
            r = random.nextInt(max);
        }
        return r;
    }

    public static int nextInt(int n) {
        return random.nextInt(n);
    }

    public static int getRandomFromRange(String range) {
        String[] array = range.split("~");
        return nextInt(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
    }

    /**
     * getRandom(5)取到的所有随机数可能为：0,1,2,3,4
     */
    public static int getRandom(int scale) {
        return (random.nextInt() << 1 >>> 1) % scale;
    }

    /**
     * getRandom(5,10)取到的所有随机数可能为：5,6,7,8,9,10
     * <br>limit : from <= to
     */
    public static int getRandom(int from, int to) {
        if (from > to) {
            throw new InvalidParameterException("to must large than from");
        }
        if (from == to) {
            return from;
        }
        return from + getRandom(to - from + 1);
    }

    /**
     * getRandom(5,10)取到的所有随机数可能为：5,6,7,8,9
     * <br>limit : from <= to
     */
    public static int getRandomNoTo(final int from, final int to) {
        if (from > to) {
            throw new InvalidParameterException("from must large than to");
        }
        if (to == from) {
            throw new InvalidParameterException(from + "," + to);
        }
        return from + getRandom(to - from);
    }
    public static <T> void addRandBucket(List<RandBucketItem<T>> list, T param , int weight ){
        list.add(new RandBucketItem(param,weight));
    }
    public static <T> T getRandBucket(List<RandBucketItem<T>> list){
        int max_weight = 0 ;
        for (RandBucketItem item : list){
            max_weight += item.getWeight();
        }
        int rand_num = getRandom(max_weight);
        int cur_weight = 0 ;
        for (RandBucketItem<T> item : list){
            cur_weight += item.getWeight();
            if  (rand_num < cur_weight ){
                return item.getParam();
            }
        }
        return null;
    }
    public static void main(String[] args) {
        int i = 0;
        while (i < 20) {
            i++;
            int randomNoTo = getRandomNoTo(5, 10);
            System.out.println(randomNoTo);
        }
    }
}
