package com.xxgames.util;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MathUtil {
    public static final Random random = new Random(System.currentTimeMillis());

    /**
     * 跟据输入的概率数组指定的概率，返回随机到的数组下标
     *
     * @param lists 概率数组，数组之和必须为1，否则大于1的部分不会被随机到
     *
     * @return 随机到的数组下标
     */
    public static int getRandomRewardIndex(ArrayList<Double> lists) {
        Double rand = getRamdomDouble();
        Double temp = 0D;
        for (int i = 0; i < lists.size(); i++) {
            if (rand >= temp && rand <= temp + lists.get(i)) {
                return i;
            }
            temp = temp + lists.get(i);
        }
        return -1;
    }

    /**
     * 跟据输入的概率数组指定的概率，返回随机到的数组下标
     *
     * @param arr，数组之和必须<=100，否则大于100的部分不会被随机到
     *
     * @return 随机到的数组下标
     */
    public static int getRandomIndex(JSONArray arr) {
        int ran = (int) (Math.random() * 100);
        int temp = 0;
        int num = 0;
        int count = 0;
        int last = arr.getIntValue(0);
        for (int i = 0; i < arr.size(); i++) {
            if (ran >= temp && ran < temp + arr.getIntValue(i)) {
                return i;
            }
            temp += arr.getIntValue(i);
            if (last <= arr.getIntValue(i)) {
                last = arr.getIntValue(i);
                num = i; // 记录概率最大的一个
            }
            count++;
        }
        if (count == arr.size()) {
            return num;
        }
        return 0;
    }

    /**
     * 跟据输入的权重数组指定的权重，返回随机到的数组下标
     *
     * @param weights 权重数组
     *
     * @return 随机到的数组下标
     */
    public static int getRandomIndex(ArrayList<Integer> weights) {
        int totalWeight = 0;
        for (Integer weight : weights) {
            totalWeight += weight;
        }
        ArrayList<Double> probs = new ArrayList<>();
        for (Integer weight : weights) {
            probs.add((double) weight / totalWeight);
        }
        return getRandomRewardIndex(probs);
    }

    public static int getRandomIndex(int[] weights) {
        int totalWeight = 0;
        for (Integer weight : weights) {
            totalWeight += weight;
        }
        ArrayList<Double> probs = new ArrayList<>();
        for (Integer weight : weights) {
            probs.add((double) weight / totalWeight);
        }
        return getRandomRewardIndex(probs);
    }

    public static Set<Integer> getRandomIndexSet(int[] weights, int needNum) {
        Set<Integer> rSet = new HashSet<>();
        int totalWeight = 0;
        for (Integer weight : weights) {
            totalWeight += weight;
        }
        ArrayList<Double> probs = new ArrayList<>();
        for (Integer weight : weights) {
            probs.add((double) weight / totalWeight);
        }

        while (rSet.size() < needNum) {
            rSet.add(getRandomRewardIndex(probs));
        }
        return rSet;
    }

    public static Set<Integer> getRandomIndexSet(JSONArray weights, int needNum) {
        Set<Integer> rSet = new HashSet<>();
        int totalWeight = 0;
        for (int i = 0; i < weights.size(); i++) {
            totalWeight += weights.getIntValue(i);
        }
        ArrayList<Double> probs = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            probs.add(weights.getDoubleValue(i) / totalWeight);
        }

        while (rSet.size() < needNum) {
            rSet.add(getRandomRewardIndex(probs));
        }
        return rSet;
    }

    public static ArrayList<Integer> getRandomIndexs(JSONArray weights, int needNum) {
        ArrayList<Integer> rSet = new ArrayList<>();
        int totalWeight = 0;
        for (int i = 0; i < weights.size(); i++) {
            totalWeight += weights.getIntValue(i);
        }
        ArrayList<Double> probs = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++) {
            probs.add(weights.getDoubleValue(i) / totalWeight);
        }

        while (rSet.size() < needNum) {
            rSet.add(getRandomRewardIndex(probs));
        }
        return rSet;
    }


    public static double getRamdomDouble() {
        return random.nextDouble();
    }


    /**
     * 根据给定的百分比，计算百分比所占总长的长度
     *
     * @param percent 大于零，小于100的整数
     * @param length 总长度
     *
     * @return
     */
    public static int getNumForPercent(int percent, int length) {
        if (percent >= 100) {
            return length;
        }
        if (percent <= 0) {
            return 0;
        }
        return (int) Math.floor((double) percent / 100 * length);
    }

    public static int randUnsigned() {
        return random.nextInt(Integer.MAX_VALUE);
    }

    public static int randUnsigned(int max) {
        return random.nextInt(max);
    }

    public static long randLong() {
        return random.nextLong();
    }

    /**
     * 根据概率计算结果
     *
     * @param pro
     *
     * @return
     */
    public static boolean isSuccess(double pro) {
        double temp = Math.random();
        return temp <= pro;
    }

    /**
     * 随集合中不包含的索引
     *
     * @param max exclusive
     * @param set
     *
     * @return
     */
    public static int randomIndex(int max, Set<Integer> set) {
        int r = randUnsigned(max);
        while (true) {
            if (!set.contains(r)) {
                break;
            }
            r = randUnsigned(max);
        }
        return r;
    }

    public static Set<Integer> randomIndexs(int max, int num) {
        Set<Integer> ret = new HashSet<>(num);
        while (ret.size() < num) {
            ret.add(randUnsigned(max));
        }
        return ret;
    }


    public static boolean between(int num, int from, int to) {
        return num >= from && num < to;
    }

    public static long sumOf(final JSONArray nums) {
        long ret = 0L;
        for (int i = 0; i < nums.size(); i++) {
            ret += nums.getIntValue(i);
        }
        return ret;
    }


    public static void main(String[] args) {
        JSONArray weights = new JSONArray();
        weights.add(30);
        weights.add(30);
        weights.add(30);
        weights.add(10);

        ArrayList<Integer> randomIndexs = getRandomIndexs(weights, 3);

        for (int j : randomIndexs) {
            System.out.println(j);
        }

        int decresecond = 13;
        int i = decresecond / 10;

        System.out.println(i);

    }

}
