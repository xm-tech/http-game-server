package com.ppgames.demo.model.mail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ppgames.core.ErrCode;
import com.ppgames.demo.config.item.PropItem;
import com.ppgames.demo.dao.AllDao;
import com.ppgames.util.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/6.
 */
public class Mail {
    public static final int MTYPE_SYSTEM = 0;
    public static final int MTYPE_PERSONAL = 1;
    public static final int STATUS_UNREAD = 0;
    public static final int STATUS_READ = 1;
    public static final int STATUS_READ_GET = 2;
    public static final int STATUS_DEL = 3;
    private static Logger logger = Logger.getLogger(Mail.class);
    private long _mailid = 0;
    private long _playerid = 0;
    private long _senderid = 0;
    private String _sendername = "";
    private String _title = "";
    private String _content = "";
    private int _status = 0;
    private ArrayList<PropItem> _attached;
    private long _createtime = 0;
    private int _mailtype = 0;

    public Mail(Map m) {
        _mailid = Long.parseLong(m.get("id").toString());
        _playerid = Long.parseLong(m.get("playerid").toString());
        _senderid = Long.parseLong(m.get("senderid").toString());
        _sendername = m.get("sendername").toString();

        _title = m.get("title").toString();
        _content = m.get("detail").toString();
        _status = Integer.parseInt(m.get("status").toString());
        if (m.get("attached") != null) {
            _attached = new ArrayList<PropItem>();
            String strAttach = m.get("attached").toString();
            if (strAttach.length() > 0) {
                JSONArray ary = JSON.parseArray(strAttach);
                for (int i = 0; i < ary.size(); i++) {
                    JSONObject object = ary.getJSONObject(i);
                    _attached.add(new PropItem(object));
                }
            }
        }
        _mailtype = Integer.parseInt(m.get("mailtype").toString());
    }

    public Mail(long playerid, long senderid, String sendername, String title,
                String content, ArrayList<PropItem> attached, int mailtype) {
        _playerid = playerid;
        _senderid = senderid;
        _sendername = sendername;
        _title = title;
        _content = content;
        _status = STATUS_UNREAD;
        _attached = attached;
        _createtime = TimeUtil.nowInt();
        _mailtype = mailtype;
    }

    public Object[] insertVals() {
        return new Object[] {
            getPlayerid(), getSenderid(), getSendername(),
            getTitle(), getContent(), getStatus(),
            JSON.toJSONString(getAttached()), getCreatetime(),
            getMailtype()
        };
    }

    public long getMailId() {
        return _mailid;
    }

    public void setMailId(long mailid) {
        _mailid = mailid;
    }

    public long getPlayerid() {
        return _playerid;
    }

    public void setPlayerid(long playerid) {
        _playerid = playerid;
    }

    public long getSenderid() {
        return _senderid;
    }

    public void setSenderid(long senderid) {
        _senderid = senderid;
    }

    public String getSendername() {
        return _sendername;
    }

    public void setSendername(String sendername) {
        _sendername = sendername;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public String getContent() {
        return _content;
    }

    public void setContent(String content) {
        _content = content;
    }

    public int attachAvailable() {
        if (_status == STATUS_READ_GET) {
            return ErrCode.MAIL_EMPTY;
        }

        if (_attached.size() <= 0) {
            return ErrCode.MAIL_EMPTY;
        }

        return ErrCode.SUCC;
    }

    public boolean delAble() {
        if (_status != STATUS_READ_GET && _attached.size() > 0) {
            return false;
        }
        return true;
    }

    public int getStatus() {
        return _status;
    }

    public void setStatus(int status) {
        if (status > _status) { //邮件状态可以修改
            _status = status;
            try {
                AllDao.md.updateMail(_mailid, status);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.warn("setStatus set failed from status = " + _status + " to " + status);
        }
    }

    public ArrayList<PropItem> getAttached() {
        return _attached;
    }

    public void setAttached(ArrayList<PropItem> attached) {
        _attached = attached;
    }

    public long getCreatetime() {
        return _createtime;
    }

    public void setCreatetime(long createTime) {
        _createtime = createTime;
    }

    public int getMailtype() {
        return _mailtype;
    }

    public void setMailtype(int mailType) {
        _mailtype = mailType;
    }
}
