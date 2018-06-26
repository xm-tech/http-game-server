<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="com.xxgames.core.ErrCode" %>
<%@ page import="com.xxgames.core.GameReq" %>
<%@ page import="com.xxgames.core.GameResp" %>
<%@ page import="com.xxgames.demo.cache.Cache" %>
<%@ page import="com.xxgames.demo.config.item.PropItem" %>
<%@ page import="com.xxgames.demo.model.Player" %>
<%@ page import="com.xxgames.demo.model.mail.Mail" %>
<%@ page import="com.xxgames.demo.model.mail.MailManager" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Set" %>


<%
    GM gm = new GM(request, response);
    gm.exec();
%>

<%!
    public class GM {
        GameReq req;
        GameResp resp;
        int msgid;

        public GM(HttpServletRequest httpReq, HttpServletResponse httpResp) throws IOException {
            httpResp.setHeader("Pragma", "no-cache");
            httpResp.setHeader("Cache-Control", "no-cache");
            httpResp.setDateHeader("Expires", 0);

            req = new GameReq(httpReq);
            msgid = req.data.getIntValue("msgid");
            resp = new GameResp(msgid, httpResp);
        }

        public void exec() {
            System.out.println("msgid:" + msgid);
            switch (msgid) {
            case 10001:
                getPlayerInfo();
                break;
            case 10005:
                sendMail();
                break;
            case 10007:
                String pids = req.data.getString("pids");
                JSONArray pidArr = JSON.parseArray(pids);
                batchSendMail(pidArr);
                break;
            case 10009:
                // all players
                batchSendMail(Cache.players.keySet());
                break;
            }
        }

        // msgid=10001
        public void getPlayerInfo() {
            System.out.println("getPlayerInfo:" + msgid);
            long pid = req.data.getLongValue("pid");
            Player p = Cache.players.get(pid);
            if (p == null) {
                resp.send(-1, "玩家不存在");
                return;
            }
            resp.data.put("player", p);
            resp.send(ErrCode.SUCC);
        }

        public void sendMail() {
            // 给玩家发邮件
            ArrayList<PropItem> attached = getAttached();
            long pid = req.data.getLongValue("pid");
            String title = req.data.getString("title");
            String msg = req.data.getString("msg");
            MailManager.getInstance().insertMail(pid, 0, "gm", title, msg, attached, Mail.MTYPE_SYSTEM);
            resp.send(ErrCode.SUCC);
        }

        public void batchSendMail(final JSONArray pidArr) {
            String title = req.data.getString("title");
            String msg = req.data.getString("msg");

            ArrayList<PropItem> attached = getAttached();
            for (int i = 0; i < pidArr.size(); i++) {
                long pid = pidArr.getLongValue(i);
                MailManager.getInstance().insertMail(pid, 0, "gm", title, msg, attached, Mail.MTYPE_SYSTEM);
            }
        }

        public void batchSendMail(final Set<Long> pids) {
            String title = req.data.getString("title");
            String msg = req.data.getString("msg");

            ArrayList<PropItem> attached = getAttached();
            for (long pid : pids) {
                MailManager.getInstance().insertMail(pid, 0, "gm", title, msg, attached, Mail.MTYPE_SYSTEM);
            }
        }

        public ArrayList<PropItem> getAttached() {
            ArrayList<PropItem> attached = new ArrayList<PropItem>();
            JSONArray awards = JSON.parseArray(req.data.getString("attached"));
            for (int i = 0; i < awards.size(); i++) {
                JSONArray ar = awards.getJSONArray(i);
                JSONObject award = new JSONObject();
                award.put("type", ar.getIntValue(0));
                award.put("id", ar.getIntValue(1));
                award.put("num", ar.getIntValue(2));
                PropItem propItem = new PropItem(award);
                attached.add(propItem);
            }
            return attached;
        }
    }
%>
