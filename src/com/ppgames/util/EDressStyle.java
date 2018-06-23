package com.ppgames.util;

/**
 * Created by Tony on 2017/6/21.
 */
public enum EDressStyle {
    Sennv(1),
    Yinglun(3),
    Fugu(4),
    Bailing(5),
    Xiha(6),
    Gete(7),
    Yaogun(8),
    Boximiye(9),
    Xiuxian(10),
    Lifu(11),
    Xiaoqingxin(12);

    private int state;

    EDressStyle(int value) {
        state = value;
    }

    public static int randomOneStyle() {
        EDressStyle[] values = EDressStyle.values();
        return values[RandomUtil.nextInt(values.length)].state;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int style = randomOneStyle();
            System.out.println(style);
        }
    }

    public int value() {
        return state;
    }
}
