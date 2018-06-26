package com.xxgames.demo.dao;

import com.xxgames.core.db.GameLogicDataSource;
import com.xxgames.demo.model.Passport;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by PhonePadPC on 2017/7/28.
 */
public class PassPortDao {
    public String fields_not_key ="`passPort`,`serverId`,`pid`,`isBinding`";
    public String fields ="`id`," + fields_not_key;

    public List<Map<String, Object>> getAllPassports() throws SQLException {
        String sql = "select * from t_passport";
        List<Map<String, Object>> query = GameLogicDataSource.instance.query(sql);
        return query;
    }
    public String getColumnsStr(Passport p) {
        String val =
            "(" + p.getId() + ",'" + p.getPassPort() + "'," + p.getServerId() + "," + p.getPid() +"," + p.getBinding() + ")";
        return val;
    }
}
