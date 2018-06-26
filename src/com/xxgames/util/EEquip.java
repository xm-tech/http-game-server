package com.xxgames.util;

/**
 * Created by Tony on 2017/6/21.
 */
public enum EEquip {
    NONE(0),
    //   SUIT(1), //NOT USE
    //  HAIR(2), //NOT USE
    DRESS(3),
    SHIRT(4),
    TROUSERS(5),
    COAT(6),
    SHOES(7),
    HAT(8),
    BAG(9),
    ORNAMENTS(10),
    // BELT(11),  //NOT USE
    SOCKS(12),
    SPECIAL(13), //特殊饰品
    SKIN(40);

    private int state = 0;

    private EEquip(int value) {
        state = value;
    }

    public int value() {
        return this.state;
    }
}

