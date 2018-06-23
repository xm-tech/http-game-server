package com.ppgames.util;

/**
 * Created by Tony on 2017/6/23.
 */
public enum EDressType {
    Null(0),
    Suit(1),
    Hair(2),
    Dress(3),
    Shirt(4),
    Trousers(5),
    Coat(6),
    Shoes(7),
    Hat(8),
    Bag(9),
    Ornaments(10),
    Belt(11),
    Socks(12),
    Special(13), //特殊饰品
    Skin(40);

    private int state = 0;

    EDressType(int value) {
        state = value;
    }

    public static EDressType valueOf(int value) {    //    手写的从int到enum的转换函数
        if (value == 40) {
            return Skin;
        } else {
            return values()[value];
        }

    }

    public int value() {
        return this.state;
    }
}
