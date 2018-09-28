package com.xxgames.demo.dao;

import com.xxgames.core.db.GameLogicDataSource;
import com.xxgames.demo.tag.TimeCount;
import com.xxgames.demo.utils.Const;
import com.xxgames.util.TimeUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by PhonePadPC on 2017/7/14.
 */
public class TagDao {

    public void insertCountTag(long pid , int tag_id ,long count ,int time ) throws SQLException {
        String marks = "?,?,?,?";
        String fields =
            "`player_id`,`tag_id`,`count`,`time`";
        String sql = "insert into t_player_count_tag(" + fields + ") values (" + marks + ")";
        //long ret = -1;
        Object Obj = GameLogicDataSource.instance
            .insertAndReturnKey(sql, pid, tag_id, count,time);
        System.err.println("insertCountTag,returned:" + Obj);
        //ret = Long.parseLong(String.valueOf(Obj));
        //return ret;
    }
    public ArrayList<TimeCount> getCountTag(long playerId)throws SQLException{
        ArrayList<TimeCount> tag_list = new ArrayList<TimeCount>();
        try {
            List<?> query = GameLogicDataSource.instance.query("select * from t_player_count_tag where player_id=?", playerId);
            if (query != null) {
                for (int i = 0; i < query.size(); i++) {
                    Map<?, ?> record = (Map<?, ?>) query.get(i);
                    TimeCount tag = new TimeCount(record);
                    tag_list.add(tag);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return tag_list;
    }

    public void insertTimeTag(long pid , int tag_id  ,int time ) throws SQLException {
        String marks = "?,?,?";
        String fields =
            "`player_id`,`tag_id`,`time`";
        String sql = "insert into t_player_time_tag(" + fields + ") values (" + marks + ")";

        Object Obj = GameLogicDataSource.instance
            .insertAndReturnKey(sql, pid, tag_id,time);
        System.err.println("insertCountTag,returned:" + Obj);
    }
    public List<Map<String, Object>>  loadAllPlayerTimeTag() throws SQLException{
        int time = TimeUtil.nowInt() - 7 * Const.DAY_SECOND;
        String sql = "select * from t_player_time_tag where time >= " + time ;
        List<Map<String, Object>> query = GameLogicDataSource.instance.query(sql);
        return query;
    }
    public List<Map<String, Object>>  loadAllPlayerCountTag() throws SQLException{
        int time = TimeUtil.nowInt() - 7 * Const.DAY_SECOND;
        String sql = "select * from t_player_count_tag where time >= " + time;
        List<Map<String, Object>> query = GameLogicDataSource.instance.query(sql);
        return query;
    }

    private TagDao() {
        //no instance
    }

    public static TagDao getInstance() {
        return SingletonHolder.tagDao;
    }

    private static class SingletonHolder{
        private static TagDao tagDao = new TagDao();
    }
}
