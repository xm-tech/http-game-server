package com.ppgames.demo.dao;

import com.alibaba.fastjson.JSON;
import com.ppgames.core.db.GameLogicDataSource;
import com.ppgames.demo.model.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PlayerDao {
    public String fields_not_key ="`logo`,`name`,`level`,`exp`,`gold`,`diamond`,`health`,`charm`,`totalpay`,`viplevel`," +
        "`vipexp`,`regtime`,`logintime`,`shops`,`equips`,`equipscapacity`,`items`,`furnitures`,`decoratefurnitures`,`settletime`," +
        "`settleperiod`,`logistics`,`faces`,`facefrags`,`gotequips`,`dressroom`,`windows`,`garden`,`apple`,`draws`,`flowerpots`," +
        "`factorys`,`factoryequips`,`cashregisters`,`employees`,`renamechance`,`head`,`checkIn`,`friendlist`,`friendrequestlist`," +
        "`pvp`,`msgreadtime`,`npcfriendlist`,`stamp`,`flowerrecord`,`monthCard`";
    public String fields ="`id`," + fields_not_key;
    public Player getPlayerById(long pid) throws SQLException {
        try {
            List<?> query = GameLogicDataSource.instance.query("select * from t_player where id=?", pid);
            if (query != null && query.size() > 0) {
                return new Player((Map<?, ?>) query.get(0));
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }
    public long createPlayer(Player p) throws SQLException{
        String marks = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
        String sql = "insert into t_player(" + fields_not_key + ") values (" + marks + ")";
        Object pidObj = GameLogicDataSource
            .instance
            .insertAndReturnKey(sql,  p.getLogo().toJSONString(), p.getName(), p.getLevel(), p.getExp(),
                p.getGold().get(),
                p.getDiamond().get(),
                p.getHealth(), p.getCharm(),
                p.getTotalPay(), p.getVipLevel(), p.getVipExp(), p.getRegTime(), p.getLoginTime(),
                p.getShops().toJSONString(),
                p.getEquips().toJSONString(), p.getEquipsCapacity(), p.getItems().toJSONString(),
                p.getFurnitures().toJSONString(), p.getDecorateFurnitures().toJSONString(),
                p.getSettleTime(),
                p.getSettlePeriod(), p.getLogistics().toJSONString(), p.getFaces().toJSONString(),
                p.getFaceFrags().toJSONString(), p.getGotEquips().toJSONString(),
                p.getDressRoom().toJSONString(),
                p.getWindows().toJSONString(), p.getGarden().toJSONString(), p.getApple(),
                p.getDraws().toJSONString(),
                p.getFlowerPots().toJSONString(), p.getFactorys().toJSONString(),
                p.getFactoryEquips().toJSONString(),
                p.getCashRegisters().toJSONString(), p.getEmployees().toJSONString(),
                p.isRenameChance(), p.getHead(), p.getCheckIn().toJSONString(), JSON.toJSONString(p.getFriendList()),
                JSON.toJSONString(p.getFriendRequestList()), JSON.toJSONString(p.getPvp()),
                p.getMsgReadTime(), JSON.toJSONString(p.getNpcFriendList()),p.getStamp(),
                JSON.toJSONString(p.getFlowerRecord()),
                JSON.toJSONString(p.getMonthCard()));
        System.err.println("createPlayer,returned:" + pidObj);
        return Long.parseLong(pidObj==null?"":pidObj.toString());
    }
    //暂时不用了。用的时候再说。修改表结构维护的东西太多。
//    public void savePlayer(Player p) throws SQLException {
//        String marks = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
//        String sql = "insert into t_player(" + fields + ") values (" + marks + ")";
//        Object pidObj = GameLogicDataSource
//            .instance
//            .insertAndReturnKey(sql, p.getId(), p.getLogo().toJSONString(), p.getName(), p.getLevel(), p.getExp(),
//                                p.getGold().get(),
//                                p.getDiamond().get(),
//                                p.getHealth(), p.getCharm(),
//                                p.getTotalPay(), p.getVipLevel(), p.getVipExp(), p.getRegTime(), p.getLoginTime(),
//                                p.getShops().toJSONString(),
//                                p.getEquips().toJSONString(), p.getEquipsCapacity(), p.getItems().toJSONString(),
//                                p.getFurnitures().toJSONString(), p.getDecorateFurnitures().toJSONString(),
//                                p.getSettleTime(),
//                                p.getSettlePeriod(), p.getLogistics().toJSONString(), p.getFaces().toJSONString(),
//                                p.getFaceFrags().toJSONString(), p.getGotEquips().toJSONString(),
//                                p.getDressRoom().toJSONString(),
//                                p.getWindows().toJSONString(), p.getGarden().toJSONString(), p.getApple(),
//                                p.getDraws().toJSONString(),
//                                p.getFlowerPots().toJSONString(), p.getFactorys().toJSONString(),
//                                p.getFactoryEquips().toJSONString(),
//                                p.getCashRegisters().toJSONString(), p.getEmployees().toJSONString(),
//                                p.isRenameChance(), p.getHead(), p.getCheckIn().toJSONString(), JSON.toJSONString(p.getFriendList()),
//                                JSON.toJSONString(p.getFriendRequestList()), JSON.toJSONString(p.getPvp()),
//                                p.getMsgReadTime(), JSON.toJSONString(p.getNpcFriendList()),p.getStamp());
//        System.err.println("savePlayer,returned:" + pidObj);
//    }

    public String getColumnsStr(Player p) {
        String val =
            "(" + p.getId() + ",'" + p.getLogo().toJSONString() + "','" + p.getName() + "'," + p.getLevel() + "," +
            p.getExp() + "," +
            p.getGold().get() + "," + p.getDiamond().get() + ","
            + p.getHealth() + "," + p.getCharm() + "," + p.getTotalPay() + "," + p.getVipLevel() + "," + p.getVipExp() +
            "," +
            p.getRegTime() + "," + p.getLoginTime()
            + ",'" + p.getShops().toJSONString() + "','" + p.getEquips().toJSONString() + "'," + p.getEquipsCapacity() +
            ",'" + p.getItems().toJSONString() + "','"
            + p.getFurnitures().toJSONString() + "','" + p.getDecorateFurnitures().toJSONString() + "'," +
            p.getSettleTime() +
            "," + p.getSettlePeriod() + ",'" + p.getLogistics().toJSONString() + "','" + p.getFaces().toJSONString() +
            "','" +
            p.getFaceFrags().toJSONString() + "','"
            + p.getGotEquips().toJSONString() + "','" + p.getDressRoom().toJSONString() + "','" +
            p.getWindows().toJSONString() + "','" + p.getGarden().toJSONString() + "'," + p.getApple() + ",'" +
            p.getDraws().toJSONString() + "','" + p.getFlowerPots().toJSONString() + "','"
            + p.getFactorys().toJSONString() + "','" + p.getFactoryEquips().toJSONString() + "','" +
            p.getCashRegisters().toJSONString() + "','" + p.getEmployees().toJSONString() + "'," + p.isRenameChance() +
            "," +
            p.getHead() + ",'" + p.getCheckIn().toJSONString() + "'"
            + ",'" + JSON.toJSONString(p.getFriendList()) + "'" + ",'" + JSON.toJSONString(p.getFriendRequestList()) +
            "'" + ",'" + JSON.toJSONString(p.getPvp()) + "'," + p.getMsgReadTime() + ",'" + JSON.toJSONString(p.getNpcFriendList()) + "'," +
                + p.getStamp()+ ",'" + JSON.toJSONString(p.getFlowerRecord()) + "'"+",'" + JSON.toJSONString(p.getMonthCard()) + "'"+")";
        return val;
    }

    public List<String> getAllNames() throws SQLException {
        String sql = "SELECT `name` FROM `t_player` where 1";
        List names = GameLogicDataSource.instance.query(sql);
        return names;
    }

    public List<Map<String, Object>> getAllPlayers() throws SQLException {
        String sql = "select * from t_player where 1";
        List<Map<String, Object>> query = GameLogicDataSource.instance.query(sql);
        return query;
    }
}
