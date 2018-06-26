package com.xxgames.demo.model.mail;

import com.xxgames.demo.config.item.PropItem;
import com.xxgames.demo.dao.AllDao;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MailManager {
    private static Logger logger = Logger.getLogger(MailManager.class);
    private HashMap<Long, MailList> _mailListManager;

    public MailManager() {
        _mailListManager = new HashMap<Long, MailList>();
    }

    public static MailManager getInstance() {
        return SingletonHolder.instance;
    }

    public MailList getMailList(long pid) {
        MailList maillist = _mailListManager.get(pid);
        if (maillist == null) {
            try {
                maillist = AllDao.md.getMailList(pid);
            } catch (SQLException e) {
                logger.error("getMailManager() id = " + pid + ", error = " + e.getMessage());
                maillist = new MailList();
            }
            _mailListManager.put(pid, maillist);
        }
        return maillist;
    }

    public void removeMailList(long pid) {
        _mailListManager.remove(pid);
    }

    /*
            {"tp":1, "id":2, "num":3}
     */
    public void insertMail(long playerid, long senderid, String sendername, String title,
                           String content, ArrayList<PropItem> attached, int mailtype) {
        Mail mail = new Mail(playerid, senderid, sendername, title, content, attached, mailtype);
        long mailid = 0;
        try {
            mailid = AllDao.md.insertMail(mail);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        mail.setMailId(mailid);
        MailList mailList = getMailList(playerid);
        if (mailList != null) {
            mailList.addMail(mail);
        }
    }
    static class SingletonHolder {
        static MailManager instance = new MailManager();
    }
}
