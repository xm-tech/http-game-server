package com.xxgames.core.db;

import org.slf4j.Logger;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDataSource {
    protected Logger log;

    protected DataSource dataSource;

    public Connection getConn() throws SQLException {
        return dataSource.getConnection();
    }

    public abstract void init() throws NamingException;

    public abstract void close();

    public abstract String getName();

    public void free(Connection conn, Statement stat, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stat != null) {
            stat.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    @SuppressWarnings("rawtypes")
    public List<Map<String, Object>> query(String sql) throws SQLException {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            pstat = conn.prepareStatement(sql);
            rs = pstat.executeQuery();
            return ResultToListMap(rs);
        } finally {
            free(conn, pstat, rs);
        }
    }

    @SuppressWarnings("rawtypes")
    public List<Map<String, Object>> query(String sql, Object... params) throws SQLException {
        ResultSet rs = null;
        PreparedStatement pstat = null;
        Connection conn = null;
        try {
            conn = getConn();
            pstat = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstat.setObject(i + 1, params[i]);
            }
            rs = pstat.executeQuery();
            return ResultToListMap(rs);
        } finally {
            free(conn, pstat, rs);
        }
    }

    public Object getSingle(String sql) throws SQLException {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            pstat = conn.prepareStatement(sql);
            rs = pstat.executeQuery();
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } finally {
            free(conn, pstat, rs);
        }
    }

    public Object getSingle(String sql, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            pstat = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstat.setObject(i + 1, params[i]);
            }
            rs = pstat.executeQuery();
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } finally {
            free(conn, pstat, rs);
        }
    }

    public int update(String sql) throws SQLException {
        Connection conn = null;
        PreparedStatement pstat = null;
        try {
            conn = getConn();
            pstat = conn.prepareStatement(sql);
            return pstat.executeUpdate();
        } finally {
            free(conn, pstat, null);
        }
    }

    public int update(String sql, Object... params) throws SQLException {
        PreparedStatement pstat = null;
        Connection conn = null;
        try {
            conn = getConn();
            pstat = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstat.setObject(i + 1, params[i]);
            }
            return pstat.executeUpdate();
        } finally {
            free(conn, pstat, null);
        }
    }

    public Object insertAndReturnKey(String sql) throws SQLException {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            pstat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstat.execute();
            rs = pstat.getGeneratedKeys();
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } finally {
            free(conn, pstat, rs);
        }
    }

    public Object insertAndReturnKey(String sql, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            pstat = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                pstat.setObject(i + 1, params[i]);
            }
            pstat.execute();
            rs = pstat.getGeneratedKeys();
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } finally {
            free(conn, pstat, rs);
        }
    }

    @SuppressWarnings("rawtypes")
    public List<Map<String, Object>> callableQuery(String procedureSql) throws SQLException {
        Connection conn = getConn();
        CallableStatement cstat = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            cstat = conn.prepareCall(procedureSql);
            rs = cstat.executeQuery();
            return ResultToListMap(rs);
        } finally {
            free(conn, cstat, rs);
        }
    }

    @SuppressWarnings("rawtypes")
    public List<Map<String, Object>> callableQuery(String procedureSql, Object... params) throws SQLException {
        Connection conn = null;
        CallableStatement cstat = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            cstat = conn.prepareCall(procedureSql);
            for (int i = 0; i < params.length; i++) {
                cstat.setObject(i + 1, params[i]);
            }
            rs = cstat.executeQuery();
            return ResultToListMap(rs);
        } finally {
            free(conn, cstat, rs);
        }
    }

    public Object callableGetSingle(String procedureSql) throws SQLException {
        Connection conn = null;
        CallableStatement cstat = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            cstat = conn.prepareCall(procedureSql);
            rs = cstat.executeQuery();
            while (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } finally {
            free(conn, cstat, rs);
        }
    }

    public Object callableGetSingle(String procedureSql, Object... params) throws SQLException {
        CallableStatement cstat = null;
        Connection conn = getConn();
        ResultSet rs = null;
        try {
            conn = getConn();
            cstat = conn.prepareCall(procedureSql);
            for (int i = 0; i < params.length; i++) {
                cstat.setObject(i + 1, params[i]);
            }
            rs = cstat.executeQuery();
            while (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } finally {
            free(conn, cstat, rs);
        }
    }

    public Object callableWithParamters(String procedureSql) throws SQLException {
        Connection conn = null;
        CallableStatement cstat = null;
        try {
            conn = getConn();
            cstat = conn.prepareCall(procedureSql);
            cstat.registerOutParameter(0, Types.OTHER);
            cstat.execute();
            return cstat.getObject(0);
        } finally {
            free(conn, cstat, null);
        }

    }

    public int callableUpdate(String procedureSql) throws SQLException {
        Connection conn = null;
        CallableStatement cstat = null;
        try {
            conn = getConn();
            cstat = conn.prepareCall(procedureSql);
            return cstat.executeUpdate();
        } finally {
            free(conn, cstat, null);
        }
    }

    public int callableUpdate(String procedureSql, Object... params) throws SQLException {
        Connection conn = null;
        CallableStatement cstat = null;
        try {
            conn = getConn();
            cstat = conn.prepareCall(procedureSql);
            for (int i = 0; i < params.length; i++) {
                cstat.setObject(i + 1, params[i]);
            }
            return cstat.executeUpdate();
        } finally {
            free(conn, cstat, null);
        }
    }

    public int[] batchUpdate(List<String> sqls) throws SQLException {
        int[] result = {};
        Statement stat = null;
        Connection conn = null;
        try {
            conn = getConn();
            conn.setAutoCommit(false);
            stat = conn.createStatement();
            for (String sql : sqls) {
                stat.addBatch(sql);
            }
            result = stat.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new ExceptionInInitializerError(e1);
            }
            throw new ExceptionInInitializerError(e);
        } finally {
            free(conn, stat, null);
        }
        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<Map<String, Object>> ResultToListMap(ResultSet rs) throws SQLException {
        List list = new ArrayList();
        while (rs.next()) {
            ResultSetMetaData md = rs.getMetaData();
            Map map = new HashMap();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                map.put(md.getColumnLabel(i), rs.getObject(i));
            }
            list.add(map);
        }
        return list;
    }
}
