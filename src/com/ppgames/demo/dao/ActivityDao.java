package com.ppgames.demo.dao;

import com.ppgames.core.db.GameLogicDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by PhonePadPC on 2017/7/31.
 */
public class ActivityDao {

    /*新手活动*/
    public List<Map<String, Object>> getAllNewPlayerGroups() throws SQLException {
        String sql = "select * from act_new_player_group";
        List<Map<String, Object>> query = GameLogicDataSource.instance.query(sql);
        return query;
    }
    public List<Map<String, Object>> getAllNewPlayerActivityTasks() throws SQLException {
        String sql = "select * from act_new_player_task";
        List<Map<String, Object>> query = GameLogicDataSource.instance.query(sql);
        return query;
    }
    /*常规活动*/
    public List<Map<String, Object>> getAllNormalGroups() throws SQLException {
        String sql = "select * from act_normal_group";
        List<Map<String, Object>> query = GameLogicDataSource.instance.query(sql);
        return query;
    }
    public List<Map<String, Object>> getAllNormalActivity() throws SQLException {
        String sql = "select * from act_normal_task";
        List<Map<String, Object>> query = GameLogicDataSource.instance.query(sql);
        return query;
    }
    /*兑换活动*/
    public List<Map<String, Object>> getAllExchangeGroups() throws SQLException {
        String sql = "select * from act_exchange_group";
        List<Map<String, Object>> query = GameLogicDataSource.instance.query(sql);
        return query;
    }
    public List<Map<String, Object>> getAllExchangeActivity() throws SQLException {
        String sql = "select * from act_exchange_task";
        List<Map<String, Object>> query = GameLogicDataSource.instance.query(sql);
        return query;
    }
}
