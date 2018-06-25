package com.ppgames.demo.handler;

public enum MsgIds {
    Login(1),
    SaveDecros(3),
    GetShelfs(5),
    AddGoods(7),
    BatchAddGoods(9),
    ExpandShelf(11),
    UnlockShelfPos(13),
    HeartBeat(15),
    Sell(27),
    BuyEquip(19),
    ExpandEquipsCapacity(29),
    GetLogistics(21),
    AddLogistics(23),
    UnloadLogistics(25),
    UpgradeLogistics(31),
    AccelerateLogistics(33),
    RemoveGoods(35),
    CreateRole(37),
    FaceCombine(39),
    FaceLevelUp(41),
    FaceChange(43),
    ChangePlayerEquips(45),
    UpgradeDressRoom(47),
    UnlockWindow(49),
    ChangeWindowEquips(51),
    GardenLowBuy(53),
    GardenHighBuy(55),
    GardenHighBuy10(57),
    GardenExchange(59),
    GetGarden(61),
    SowSeed(63),
    OverSeed(65),
    UnlockFactoryEquip(67),
    GetFactorys(69),
    AddFactory(71),
    UpgradeFactory(73),
    ;

    int val;

    MsgIds(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
