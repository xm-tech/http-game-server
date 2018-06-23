package com.ppgames.demo.model.mail;

import com.ppgames.core.ErrCode;
import com.ppgames.demo.cache.Cache;
import com.ppgames.demo.config.item.PropItem;
import com.ppgames.demo.dao.AllDao;
import com.ppgames.demo.model.Player;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/6.
 */
public class MailList {
    private static Logger logger = Logger.getLogger(MailList.class);
    private HashMap<Long, Mail> _mailList;

    public MailList() {
        _mailList = new HashMap<Long, Mail>();
    }

    public void addMail(Mail mail) {
        _mailList.put(mail.getMailId(), mail);
    }

    public Mail getMail(long id) {
        return _mailList.get(id);
    }

    public ArrayList<Mail> getArrayList() {
        ArrayList<Mail> ret = new ArrayList<Mail>();
        for (Map.Entry<Long, Mail> entry : _mailList.entrySet()) {
            ret.add(entry.getValue());
        }
        return ret;
    }

    public synchronized int getMailAttach(long mailId) {
        Mail mail = _mailList.get(mailId);
        if (mail == null) {
            return ErrCode.MAIL_NOT_EXIST;
        }
        int attachAvail = mail.attachAvailable();
        if (attachAvail != ErrCode.SUCC) {
            return mail.attachAvailable();
        }
        ArrayList<PropItem> ary = mail.getAttached();
        Player p = Cache.players.get(mail.getPlayerid());
        if (p == null) {
            return ErrCode.MAIL_NOT_EXIST;
        }
        for (PropItem item : ary) {
            if (p.exceedBagLimit(item.getType(), item.getId(), item.getNum())) {
                return ErrCode.EXCEED_BAG_LIMIT;
            }
        }
        for (PropItem item : ary) {
            p.addBags("getMailAttach." + mailId, item.getType(), item.getId(), item.getNum());
        }
        mail.setStatus(Mail.STATUS_READ_GET);
        return ErrCode.SUCC;
    }

    public synchronized ArrayList<Long> getAllMailAttach() {
        ArrayList<Long> mailList = new ArrayList<Long>();

        for (Map.Entry<Long, Mail> entry : _mailList.entrySet()) {
            if (getMailAttach(entry.getKey()) == ErrCode.SUCC) {
                mailList.add(entry.getKey());
            }
        }
        return mailList;
    }

    public boolean deleteMail(Mail mail) {
        if (mail.delAble()) {
            try {
                AllDao.md.deleteMail(mail.getMailId());
            } catch (SQLException e) {
                logger.error("mail delete failed. Mail id = " + mail.getMailId() + e.getMessage());
            }
            _mailList.remove(mail.getMailId());
            return true;
        }

        return false;
    }

    public boolean updateMailStatus(long mailId, int status) {
        Mail mail = _mailList.get(mailId);
        if (mail != null) {
            if (status == Mail.STATUS_DEL) {
                return deleteMail(mail);
            } else {
                mail.setStatus(status);
                return true;
            }
        } else {
            logger.error("updateMailStatus mail not found mailId = " + mailId);
        }
        return false;
    }

    public ArrayList<Long> deleteAllMail() {
        ArrayList<Long> ret = new ArrayList<Long>();

        Iterator<Map.Entry<Long, Mail>> iterator = _mailList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Mail> elem = iterator.next();
            Mail mail = elem.getValue();
            if (mail.delAble()) {
                ret.add(mail.getMailId());
            }
        }

        for (long mailId : ret) {
            Mail mail = _mailList.get(mailId);
            if (mail != null) {
                deleteMail(mail);
            }
        }
        return ret;
    }
}
