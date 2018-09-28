package com.xxgames.demo.handler;

import com.xxgames.core.GameAct;

// TODO 当有新接口时，该enum还是要修改，即存在耦合
public enum MsgIds {
    LOGIN(Login.getInstance().getMsgId(), Login.getInstance()),
    SAVEDECROS(SaveDecros.getInstance().getMsgId(), SaveDecros.getInstance());
//    GetShelfs(5, new GetShelfs()),
//    AddGoods(7, new AddGoods()),
//    BatchAddGoods(9, new BatchAddGoods()),
//    ExpandShelf(11, new ExpandShelf()),
//    UnlockShelfPos(13, new UnlockShelfPos()),
//    HeartBeat(15, new HeartBeat()),
//    Sell(27, new Sell()),
//    BuyEquip(19, new BuyEquip()),
//    ExpandEquipsCapacity(29, new ExpandEquipsCapacity()),
//    GetLogistics(21, new GetLogistics()),
//    AddLogistics(23, new AddLogistics()),
//    UnloadLogistics(25, new UnloadLogistics()),
//    UpgradeLogistics(31, new UpgradeLogistics()),
//    AccelerateLogistics(33, new AccelerateLogistics()),
//    RemoveGoods(35, new RemoveGoods()),
//    CreateRole(37, new CreateRole()),
//    FaceCombine(39, new FaceCombine()),
//    FaceLevelUp(41, new FaceLevelUp()),
//    FaceChange(43, new FaceChange()),
//    ChangePlayerEquips(45, new ChangePlayerEquips()),
//    UpgradeDressRoom(47, new UpgradeDressRoom()),
//    UnlockWindow(49, new UnlockWindow()),
//    ChangeWindowEquips(51, new ChangeWindowEquips()),
//    GardenLowBuy(53, new GardenLowBuy()),
//    GardenHighBuy(55, new GardenHighBuy()),
//    GardenHighBuy10(57, new GardenHighBuy10()),
//    GardenExchange(59, new GardenExchange()),
//    GetGarden(61, new GetGarden()),
//    SowSeed(63, new SowSeed()),
//    OverSeed(65, new OverSeed()),
//    UnlockFactoryEquip(67, new UnlockFactoryEquip()),
//    GetFactorys(69, new GetFactorys()),
//    AddFactory(71, new AddFactory()),
//    UpgradeFactory(73, new UpgradeFactory()),
//    AccelerateFactory(75, new AccelerateFactory()),
//    UnloadFactory(77),
//    FactoryMake(79),
//    CashRegisterLevelUp(81),
//    Employ(83),
//    OverEmploy(85),
//    ShopBuy(87),
//    Rename(89),
//    ChangePlayerHead(91),
//    CheckIn(93),
//    OpenCheckInChest(95),
//    ExpandShop(97),
//    MailList(99),
//    MailUpdate(101),
//    MailAttach(103),
//    MailDelAll(105),
//    MailAttachAll(107),
//    RequestAddFriend(109),
//    AgreeAddFriend(111),
//    RefuseAddFriend(113),
//    GetFriends(115),
//    GetFriendRequests(117),
//    GiveFriendFlower(119),
//    DeleteFriend(121),
//    GetCheckInStates(123),
//    RecommendFriends(125),
//    SearchFriend(127),
//    VisitFriendRoom(129),
//    PvpRefreshEnemys(131),
//    PvpBattle(135),
//    PvpBuyBattleNum(137),
//    CrystalBuy(139),
//    GetRanks(141),
//    GetMainLineQuest(151),
//    GetActiveValueQuest(153),
//    TakeActiveValueReward(155),
//    ActiveValueRaffle(157),
//    TakeMainLineReward(159),
//    GetCustomerQuests(161),
//    TakeCustomerQuestRewards(163),
//    GetNpcQuest(165),
//    TakeNpcQuestRewards(167),
//    SendChatMsg(169),
//    ClearnRubbishs(171),
//    StampExchange(173),
//    GetSaleQuest(175),
//    BeginSaleQuest(177),
//    TakeSaleQuestRewards(179),
//    BindingWXSuccess(181),
//    BuyFund(183),
//    GetActivityGroup(185),
//    GetActivityQuest(187),
//    GetActivityQuestReward(189),
//    AccelerateFlower(191),
//    GetFlowerRecode(193),
//    GetPlayerInfo(195);

    int val;
    GameAct gameAct;

    MsgIds(int val, GameAct gameAct) {
        this.val = val;
        this.gameAct = gameAct;
    }

    public int getVal() {
        return val;
    }

    public GameAct getGameAct() {
        return gameAct;
    }

}
