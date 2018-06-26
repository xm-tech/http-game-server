package com.xxgames.demo.handler;

import com.xxgames.core.GameAct;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameActs {

    // <msgid,GameAct>
    private Map<Integer, GameAct> all = new ConcurrentHashMap<>();

    private GameActs() {
    }

    private synchronized void register() {
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
        register(MsgIds.AccelerateFactory.getVal(), new AccelerateFactory());
        register(MsgIds.UnloadFactory.getVal(), new UnloadFactory());
        register(MsgIds.FactoryMake.getVal(), new FactoryMake());
        register(MsgIds.CashRegisterLevelUp.getVal(), new CashRegisterLevelUp());
        register(MsgIds.Employ.getVal(), new Employ());
        register(MsgIds.OverEmploy.getVal(), new OverEmploy());
        register(MsgIds.ShopBuy.getVal(), new ShopBuy());
        register(MsgIds.Rename.getVal(), new Rename());
        register(MsgIds.ChangePlayerHead.getVal(), new ChangePlayerHead());
        register(MsgIds.CheckIn.getVal(), new CheckIn());
        register(MsgIds.OpenCheckInChest.getVal(), new OpenCheckInChest());
        register(MsgIds.ExpandShop.getVal(), new ExpandShop());
        register(MsgIds.MailList.getVal(), new MailList());
        register(MsgIds.MailUpdate.getVal(), new MailUpdate());
        register(MsgIds.MailAttach.getVal(), new MailAttach());
        register(MsgIds.MailDelAll.getVal(), new MailDelAll());
        register(MsgIds.MailAttachAll.getVal(), new MailAttachAll());
        register(MsgIds.RequestAddFriend.getVal(), new RequestAddFriend());
        register(MsgIds.AgreeAddFriend.getVal(), new AgreeAddFriend());
        register(MsgIds.RefuseAddFriend.getVal(), new RefuseAddFriend());
        register(MsgIds.GetFriends.getVal(), new GetFriends());
        register(MsgIds.GetFriendRequests.getVal(), new GetFriendRequests());
        register(MsgIds.GiveFriendFlower.getVal(), new GiveFriendFlower());
        register(MsgIds.DeleteFriend.getVal(), new DeleteFriend());
        register(MsgIds.GetCheckInStates.getVal(), new GetCheckInStates());
        register(MsgIds.RecommendFriends.getVal(), new RecommendFriends());
        register(MsgIds.SearchFriend.getVal(), new SearchFriend());
        register(MsgIds.VisitFriendRoom.getVal(), new VisitFriendRoom());
        register(MsgIds.PvpRefreshEnemys.getVal(), new PvpRefreshEnemys());
        register(MsgIds.PvpBattle.getVal(), new PvpBattle());
        register(MsgIds.PvpBuyBattleNum.getVal(), new PvpBuyBattleNum());
        register(MsgIds.CrystalBuy.getVal(), new CrystalBuy());
        register(MsgIds.GetRanks.getVal(), new GetRanks());
        register(MsgIds.GetMainLineQuest.getVal(), new GetMainLineQuest());
        register(MsgIds.GetActiveValueQuest.getVal(), new GetActiveValueQuest());
        register(MsgIds.TakeActiveValueReward.getVal(), new TakeActiveValueReward());
        register(MsgIds.ActiveValueRaffle.getVal(), new ActiveValueRaffle());
        register(MsgIds.TakeMainLineReward.getVal(), new TakeMainLineReward());
        register(MsgIds.GetCustomerQuests.getVal(), new GetCustomerQuests());
        register(MsgIds.TakeCustomerQuestRewards.getVal(), new TakeCustomerQuestRewards());
        register(MsgIds.GetNpcQuest.getVal(), new GetNpcQuest());
        register(MsgIds.TakeNpcQuestRewards.getVal(), new TakeNpcQuestRewards());
        register(MsgIds.SendChatMsg.getVal(), new SendChatMsg());
        register(MsgIds.ClearnRubbishs.getVal(), new ClearnRubbishs());
        register(MsgIds.StampExchange.getVal(), new StampExchange());
        register(MsgIds.GetSaleQuest.getVal(), new GetSaleQuest());
        register(MsgIds.BeginSaleQuest.getVal(), new BeginSaleQuest());
        register(MsgIds.TakeSaleQuestRewards.getVal(), new TakeSaleQuestRewards());
        register(MsgIds.BindingWXSuccess.getVal(), new BindingWXSuccess());
        register(MsgIds.BuyFund.getVal(), new BuyFund());
        register(MsgIds.GetActivityGroup.getVal(), new GetActivityGroup());
        register(MsgIds.GetActivityQuest.getVal(), new GetActivityQuest());
        register(MsgIds.GetActivityQuestReward.getVal(), new GetActivityQuestReward());
        register(MsgIds.AccelerateFlower.getVal(), new AccelerateFlower());
        register(MsgIds.GetFlowerRecode.getVal(), new GetFlowerRecode());
        register(MsgIds.GetPlayerInfo.getVal(), new GetPlayerInfo());
        System.err.println("GameActs init succ");
    }

    private void register(final Integer msgid, GameAct gameAct) {
        all.put(msgid, gameAct);
    }

    public static GameAct get(final int msgid) {
        return SingletonHolder.instance.all.get(msgid);
    }

    public static boolean contains(final int msgid) {
        return SingletonHolder.instance.all.containsKey(msgid);
    }


    private static class SingletonHolder {
        private static GameActs instance = new GameActs();
    }


    public static void init() {
        SingletonHolder.instance.register();
    }
}
