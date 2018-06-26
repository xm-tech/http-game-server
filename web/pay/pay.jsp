<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="com.xxgames.core.GameReq" %>
<%@ page import="com.xxgames.core.GameResp" %>
<%@ page import="com.xxgames.core.db.GameLogDataSource" %>
<%@ page import="com.xxgames.demo.cache.Cache" %>
<%@ page import="com.xxgames.demo.config.item.PropItem" %>
<%@ page import="com.xxgames.demo.model.Player" %>
<%@ page import="com.xxgames.demo.model.mail.Mail" %>
<%@ page import="com.xxgames.demo.model.mail.MailManager" %>
<%@ page import="com.xxgames.util.BagType" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%
    try {
        Pay pay = new Pay(request, response);
        pay.doPay();
    } catch (Exception e) {
        e.printStackTrace();
        response.getWriter().println(ioErr(e));
    }
%>

<%!
    public class Pay {
        GameReq req;
        GameResp resp;

        int msgid;

        private long pid;
        private int money;
        private String oid;
        private String ch;
        private int paytime;
        // 固定添加钻石: money * 10
        private int addDiamond;
        // 额外奖励钻石
        private int sendDiamond;
        private int productid;
        private int type;

        public Pay(HttpServletRequest httpReq, HttpServletResponse httpResp) throws IOException {
            httpResp.setHeader("Pragma", "no-cache");
            httpResp.setHeader("Cache-Control", "no-cache");
            httpResp.setDateHeader("Expires", 0);

            req = new GameReq(httpReq);
            msgid = req.data.getIntValue("msgid");
            resp = new GameResp(msgid, httpResp);
            init();
        }

        // {"msgid":20001,"pid":1000,"oid":"test_1500631243","money":648,"ch":"GM","paytime":1500631243}
        private void init() {
            setPid(req.data.getLongValue("pid"));
            setMoney(req.data.getIntValue("money"));
            setOid(req.data.getString("oid"));
            setCh(req.data.getString("ch"));
            setPaytime(req.data.getIntValue("paytime"));
            setAddDiamond(money * 10);
            setSendDiamond(0);
            setProductid(req.data.getIntValue("productid"));
            setType(req.data.getIntValue("type"));
            System.out.println("init," +
                               pid + "," + money + "," + oid + "," + ch + "," + addDiamond + "," + sendDiamond + "," +
                               paytime + "," + productid);
        }

        public void doPay() {
            PayRet payRet = new PayRet();
            PayLog payLog = new PayLog(this);
            if (!isOk()) {
                payRet.setErrCode(ErrCode.UNKOWN);
                payRet.setMsg("非法订单");
            } else if (payLog.repeat()) {
                payRet.setErrCode(ErrCode.REPEAT_ORDER);
                payRet.setMsg("重复订单");
            } else {
                // 同步记录充值日志
                payLog.log();
                // 异步发奖
                new Thread(new RewardTask(this)).start();
            }
            resp.send(payRet.getErrCode(), payRet.getMsg());
        }

        public boolean isOk() {
            // 字段合法性校验
            return true;
        }

        class RewardTask implements Runnable {
            int addDiamond;
            int sendDiamond;
            int payMoney;
            Player p;

            RewardTask(final Pay pay) {
                addDiamond = pay.getAddDiamond();
                sendDiamond = pay.getSendDiamond();
                payMoney = pay.getMoney();
                p = Cache.players.get(pay.getPid());
            }

            public void reward() {
                p.payCallBack(payMoney,type, addDiamond, sendDiamond);

                if (type == 1){
                    ArrayList<PropItem> attached = new ArrayList<PropItem>();
                    attached.add(new PropItem(BagType.DIAMOND, 0, addDiamond));
                    if (sendDiamond > 0) {
                        attached.add(new PropItem(BagType.DIAMOND, 0, sendDiamond));
                    }
                    MailManager.getInstance().insertMail(getPid(), 0, "pay", "系统邮件", "系统邮件", attached, Mail.MTYPE_SYSTEM);
                }

            }

            @Override
            public void run() {
                reward();
            }
        }

        class PayLog {
            long id;
            long pid;
            String oid;
            String ch;
            int money;
            int paytime;
            int addDiamond;
            int sendDiamond;
            int productid;
            int type ;

            PayLog(Pay pay) {
                pid = pay.getPid();
                oid = pay.getOid();
                ch = pay.getCh();
                money = pay.getMoney();
                paytime = pay.getPaytime();
                addDiamond = pay.getAddDiamond();
                sendDiamond = pay.getSendDiamond();
                productid = pay.getProductid();
                type = pay.getType();
            }

            boolean repeat() {
                String sql = "select * from t_log_pay where oid=? and ch=?";
                try {
                    // FIXME 应该单独获取1 db conn ,操作完后 close conn
                    List<Map<String, Object>> query = GameLogDataSource.instance.query(sql, oid, ch);
                    if (query.size() > 0) {
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return true;
                }
                return false;
            }

            boolean log() {
                String marks = "?,?,?,?,?,?,?,?";
                String fields = "`pid`,`oid`,`ch`,`money`,`paytime`,`adddiamond`,`senddiamond`,`productid`";
                String sql = "insert into t_log_pay(" + fields + ") values (" + marks + ")";
                try {
                    // FIXME 应该单独获取1 db conn ,操作完后 close conn
                    GameLogDataSource.instance.insertAndReturnKey(sql, pid, oid, ch, money, paytime, addDiamond,
                                                                  sendDiamond, productid);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        }

        class PayRet {
            int errCode;
            String msg;

            PayRet() {
                errCode = ErrCode.SUCC;
                msg = "充值成功";
            }

            public int getErrCode() {
                return errCode;
            }

            public void setErrCode(int errCode) {
                this.errCode = errCode;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }
        }

        class ErrCode {
            static final int UNKOWN = -1;
            static final int SUCC = 0;
            static final int REPEAT_ORDER = 1;
        }

        public void setAddDiamond(int addDiamond) {
            this.addDiamond = addDiamond;
        }

        public void setSendDiamond(int sendDiamond) {
            this.sendDiamond = sendDiamond;
        }

        public long getPid() {
            return pid;
        }

        public void setPid(long pid) {
            this.pid = pid;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getCh() {
            return ch;
        }

        public void setCh(String ch) {
            this.ch = ch;
        }

        public int getPaytime() {
            return paytime;
        }

        public void setPaytime(int paytime) {
            this.paytime = paytime;
        }

        public int getAddDiamond() {
            return addDiamond;
        }

        public int getSendDiamond() {
            return sendDiamond;
        }

        public int getProductid() {
            return productid;
        }

        public void setProductid(int productid) {
            this.productid = productid;
        }
        public int getType(){
            return type;
        }
        public void setType(int type){
            this.type = type;
        }
    }

    private JSONObject ioErr(Exception e) {
        JSONObject data = new JSONObject();
        data.put("msgid", 20002);
        data.put("errcode", -1);
        data.put("errmsg", "充值失败");
        return data;
    }
%>