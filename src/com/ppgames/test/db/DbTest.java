package com.ppgames.test.db;

import com.ppgames.core.db.GameLogicDataSource;

import java.util.List;

public class DbTest {

    @SuppressWarnings("rawtypes")
    public static void go() {
        String sql = "select * from t_player where id=? limit 1";
        try {
            List query = GameLogicDataSource.instance.query(sql, 1);
            Object obj = query.get(0);
            System.err.println(obj);

//			sql = "INSERT into t_player values();";
//			GameLogicDataSource.update(sql);

        } catch (Exception ignored) {
        }
    }
}
