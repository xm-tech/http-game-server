package com.xxgames.demo.dao;

import com.alibaba.fastjson.JSON;
import com.xxgames.core.db.GameLogicDataSource;
import com.xxgames.demo.model.quest.QuestList;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Tony on 2017/6/12.
 */
public class QuestDao {
    public String fields = "`pid`,`mainline`,`activeValue`, `customer`, `npc`,`sale`,`activity`";

    public QuestList getQuestList(long playerId) throws SQLException {
        try {
            List<?> query = GameLogicDataSource.instance.query("select * from t_quest where pid=?", playerId);
            if (query != null) {
                for (int i = 0; i < query.size(); i++) {
                    Map<?, ?> record = (Map<?, ?>) query.get(i);
                    return new QuestList(record);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    public void saveQuests(QuestList q) throws SQLException {
        String marks = "?,?,?,?,?,?,?";
        String sql = "insert into t_quest(" + fields + ") values (" + marks + ")";
        Object pidObj =
            GameLogicDataSource.instance.insertAndReturnKey(sql, q.getPid(), JSON.toJSONString(q.getMainLineDataForDb()),
                                                            JSON.toJSONString(q.getActiveValueDataForDb()),
                                                            JSON.toJSONString((q.getCustomerQuestDataForDb())),
                                                            JSON.toJSONString(q.getNpcQuestDataForDb()),
                                                            JSON.toJSONString(q.getSaleQuestDataForDb()),
                                                            JSON.toJSONString(q.getActivityQuestDataForDb()));
        System.err.println("saveQuest,returned:" + pidObj);
    }

    public String getColumnsStr(QuestList q) {
        String val = "(" + q.getPid() + ",'" + JSON.toJSONString(q.getMainLineDataForDb()) + "','" +
                     JSON.toJSONString(q.getActiveValueDataForDb()) + "','" +
                     JSON.toJSONString(q.getCustomerQuestDataForDb()) + "','" +
                     JSON.toJSONString(q.getNpcQuestDataForDb()) + "','" +
                     JSON.toJSONString(q.getNpcQuestDataForDb()) + "','" +
                     JSON.toJSONString(q.getActivityQuestDataForDb()) + "'" + ")";
        return val;
    }

    public List<Map<String, Object>> getAllPlayersQuests() throws SQLException {
        String sql = "select * from t_quest where 1";
        List<Map<String, Object>> query = GameLogicDataSource.instance.query(sql);
        return query;
    }
}
