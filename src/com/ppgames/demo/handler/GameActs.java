package com.ppgames.demo.handler;

import com.ppgames.core.GameAct;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameActs {

    // <cmd,GameAct>
    private Map<Integer, GameAct> all = new ConcurrentHashMap<>();

    private GameActs() {
        init();
    }

    private void init() {
        all.clear();
        register(MsgIds.Login.getVal(), new Login());
        register(MsgIds.SaveDecros.getVal(), new SaveDecros());
        register(MsgIds.GetShelfs.getVal(), new GetShelfs());
        register(MsgIds.AddGoods.getVal(), new AddGoods());
        register(MsgIds.BatchAddGoods.getVal(), new BatchAddGoods());
        register(MsgIds.ExpandShelf.getVal(), new ExpandShelf());
        register(MsgIds.UnlockShelfPos.getVal(), new UnlockShelfPos());
        register(MsgIds.HeartBeat.getVal(), new HeartBeat());
        register(MsgIds.Sell.getVal(), new Sell());
        register(MsgIds.BuyEquip.getVal(), new BuyEquip());
        register(MsgIds.ExpandEquipsCapacity.getVal(), new ExpandEquipsCapacity());
        register(MsgIds.GetLogistics.getVal(), new GetLogistics());
        register(MsgIds.AddLogistics.getVal(), new AddLogistics());
        register(MsgIds.UnloadLogistics.getVal(), new UnloadLogistics());
        register(MsgIds.UpgradeLogistics.getVal(), new UpgradeLogistics());
        register(MsgIds.AccelerateLogistics.getVal(), new AccelerateLogistics());
        register(MsgIds.RemoveGoods.getVal(), new RemoveGoods());
        register(MsgIds.CreateRole.getVal(), new CreateRole());
        register(MsgIds.FaceCombine.getVal(), new FaceCombine());
        register(MsgIds.FaceLevelUp.getVal(), new FaceLevelUp());
        register(MsgIds.FaceChange.getVal(), new FaceChange());
        register(MsgIds.ChangePlayerEquips.getVal(), new ChangePlayerEquips());
        register(MsgIds.UpgradeDressRoom.getVal(), new UpgradeDressRoom());
        register(MsgIds.UnlockWindow.getVal(), new UnlockWindow());
        register(MsgIds.ChangeWindowEquips.getVal(), new ChangeWindowEquips());
        register(MsgIds.GardenLowBuy.getVal(), new GardenLowBuy());
        register(MsgIds.GardenHighBuy.getVal(), new GardenHighBuy());
        register(MsgIds.GardenHighBuy10.getVal(), new GardenHighBuy10());
        register(MsgIds.GardenExchange.getVal(), new GardenExchange());
        register(MsgIds.GetGarden.getVal(), new GetGarden());
        register(MsgIds.SowSeed.getVal(), new SowSeed());
        register(MsgIds.OverSeed.getVal(), new OverSeed());
        register(MsgIds.UnlockFactoryEquip.getVal(), new UnlockFactoryEquip());
        register(MsgIds.GetFactorys.getVal(), new GetFactorys());
        register(MsgIds.AddFactory.getVal(), new AddFactory());
        register(MsgIds.UpgradeFactory.getVal(), new UpgradeFactory());
        register(75, new AccelerateFactory());
        register(77, new UnloadFactory());
        register(79, new FactoryMake());
        register(81, new CashRegisterLevelUp());
        register(83, new Employ());
        register(85, new OverEmploy());
        register(87, new ShopBuy());
        register(89, new Rename());
        register(91, new ChangePlayerHead());
        register(93, new CheckIn());
        register(95, new OpenCheckInChest());
        register(97, new ExpandShop());
        register(99, new MailList());
        register(101, new MailUpdate());
        register(103, new MailAttach());
        register(105, new MailDelAll());
        register(107, new MailAttachAll());
        register(109, new RequestAddFriend());
        register(111, new AgreeAddFriend());
        register(113, new RefuseAddFriend());
        register(115, new GetFriends());
        register(117, new GetFriendRequests());
        register(119, new GiveFriendFlower());
        register(121, new DeleteFriend());
        register(123, new GetCheckInStates());
        register(125, new RecommendFriends());
        register(127, new SearchFriend());
        register(129, new VisitFriendRoom());
        register(131, new PvpRefreshEnemys());
        register(135, new PvpBattle());
        register(137, new PvpBuyBattleNum());
        register(139, new CrystalBuy());
        register(141, new GetRanks());
        register(151, new GetMainLineQuest());
        register(153, new GetActiveValueQuest());
        register(155, new TakeActiveValueReward());
        register(157, new ActiveValueRaffle());
        register(159, new TakeMainLineReward());
        register(161, new GetCustomerQuests());
        register(163, new TakeCustomerQuestRewards());
        register(165, new GetNpcQuest());
        register(167, new TakeNpcQuestRewards());
        register(169, new SendChatMsg());
        register(171, new ClearnRubbishs());
        register(173, new StampExchange());
        register(175, new GetSaleQuest());
        register(177, new BeginSaleQuest());
        register(179, new TakeSaleQuestRewards());
        register(181, new BindingWXSuccess());
        register(183, new BuyFund());
        register(185, new GetActivityGroup());
        register(187, new GetActivityQuest());
        register(189, new GetActivityQuestReward());
        register(191, new AccelerateFlower());
        register(193, new GetFlowerRecode());
        register(195, new GetPlayerInfo());
        System.err.println("GameActs init succ");
    }

    private void register(final Integer msgid, GameAct gameAct) {
        all.put(msgid, gameAct);
    }

    public static GameAct get(final int msgid){
        return SingletonHolder.instance.all.get(msgid);
    }

    static class SingletonHolder {
        private static GameActs instance = new GameActs();
    }
}
