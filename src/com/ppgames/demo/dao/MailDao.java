package com.ppgames.demo.dao;

/**
 * Created by Administrator on 2017/6/6.
 */

import com.alibaba.fastjson.JSON;
import com.ppgames.core.db.GameLogicDataSource;
import com.ppgames.demo.model.mail.Mail;
import com.ppgames.demo.model.mail.MailList;
import com.ppgames.util.TimeUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MailDao {
    public MailList getMailList(long playerId) throws SQLException {
        MailList maillist = new MailList();
        try {
            int time = TimeUtil.startOfDay() - 30 * 24 * 60 * 60 ; //一个月前
            List<?> query = GameLogicDataSource.instance.query("select * from t_mail where playerid=? and createtime > ?", playerId,time);
            if (query != null) {
                for (int i = 0; i < query.size(); i++) {
                    Map<?, ?> record = (Map<?, ?>) query.get(i);
                    Mail mail = new Mail(record);
                    maillist.addMail(mail);
                }
            }

        } catch (SQLException e) {
            throw e;
        }
        return maillist;
    }

    public long insertMail(Mail mail) throws SQLException {
        String marks = "?,?, ?,?,?, ?,?,?, ?";
        String fields =
            "`playerid`,`senderid`,`sendername`,`title`,`detail`,`status`,`attached`, `createtime`, `mailtype`";
        String sql = "insert into t_mail(" + fields + ") values (" + marks + ")";
        long ret = -1;
        Object pidObj = GameLogicDataSource.instance
            .insertAndReturnKey(sql, mail.getPlayerid(), mail.getSenderid(), mail.getSendername(),
                                mail.getTitle(), mail.getContent(), mail.getStatus(),
                                JSON.toJSONString(mail.getAttached()), mail.getCreatetime(),
                                mail.getMailtype());
        System.err.println("savePlayer,returned:" + pidObj);
        ret = Long.parseLong(String.valueOf(pidObj));
        return ret;
    }

    public int updateMail(long mailId, int status) throws SQLException {
        int ret;
        try {
            ret = GameLogicDataSource.instance.update("update t_mail set status = ? where id = ?", status, mailId);
        } catch (SQLException e) {
            throw e;
        }
        return ret;
    }

    public int deleteMail(long mailId) throws SQLException {
        int ret;
        try {
            ret = GameLogicDataSource.instance.update("delete from t_mail  where id = ?", mailId);
        } catch (SQLException e) {
            throw e;
        }
        return ret;
    }

    public int deleteAllMail() throws SQLException {
        int ret;
        try {
            ret = GameLogicDataSource.instance.update("delete from t_mail  where id in ()");
        } catch (SQLException e) {
            throw e;
        }
        return ret;
    }
}
