package com.xxgames.demo.log;

public class LogFactory {

    public static Log createItemLog(long pid, int itemid, int num, String source) {
        return new ItemLog(pid, itemid, num, source);
    }

    public static Log createEquipLog(long pid, int itemid, int num, String source) {
        return new EquipLog(pid, itemid, num, source);
    }

    public static Log createDiamondLog(long pid, int num, String source) {
        return new DiamondLog(pid, num, source);
    }

    public static Log createGoldLog(long pid, int num, String source) {
        return new GoldLog(pid, num, source);
    }

    public static Log createAppleLog(long pid, int num, String source) {
        return new AppleLog(pid, num, source);
    }

    public static Log createCrystalLog(long pid, int num, String source) {
        return new CrystalLog(pid, num, source);
    }

    public static Log createExpLog(long pid, int num, String source) {
        return new ExpLog(pid, num, source);
    }

    public static Log createDrawLog(long pid, int itemid, int num, String source) {
        return new DrawLog(pid, itemid, num, source);
    }

    public static Log createFaceLog(long pid, int itemid, int num, String source) {
        return new FaceLog(pid, itemid, num, source);
    }

    public static Log createFurnitureLog(long pid, int itemid, int num, String source) {
        return new FurnitureLog(pid, itemid, num, source);
    }

    public static Log createFaceFragLog(long pid, int itemid, int num, String source) {
        return new FaceFragLog(pid, itemid, num, source);
    }

    public static Log createStampLog(long pid, int num, String source) {
        return new StampLog(pid, num, source);
    }

}
