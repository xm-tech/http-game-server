package com.ppgames.core;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.demo.DataManager;
import com.ppgames.demo.handler.GameActs;
import com.ppgames.demo.handler.MsgIds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public class GateWay extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(GateWay.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long beginTime = System.currentTimeMillis();
            GameReq gameReq = new GameReq(req);

            log.debug(gameReq.toString());
            if (gameReq.isOk()) {
                int msgid = gameReq.data.getIntValue("msgid");
                GameResp gameResp = new GameResp(msgid, resp);
                if (msgid == MsgIds.Login.getVal()) {
                    //login
                    loginPost(gameReq, gameResp);
                } else {
                    activePost(gameReq, gameResp);
                }
                long endTime = System.currentTimeMillis();
                log.debug(gameReq + "," + (endTime - beginTime) + " ms");
                log.debug(gameResp.toString());

            } else {
                log.error(gameReq.toString());
                sysErr(resp);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            sysErr(resp);
        }
    }

    private void sysErr(HttpServletResponse resp) throws IOException {
        JSONObject data = new JSONObject();
        data.put("errcode", ErrCode.UNKONW_ERR);
        data.put("errmsg", "系统异常");
        data.put("msgid", 0);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println(data);
    }

    private void loginPost(GameReq gameReq, GameResp gameResp) {
        if (GameContextListener.gameStatus == GameStatus.MAINTAIN) {
            gameResp.send(ErrCode.SYS_MAINTINING);
        } else {
            GameAct gameAct = GameActs.get(MsgIds.Login.getVal());
            gameAct.exec(gameReq, gameResp);
        }

    }

    private void activePost(GameReq gameReq, GameResp gameResp) {
        int msgid = gameReq.data.getIntValue("msgid");
        long token = gameReq.data.getLongValue("token");
        long pid = gameReq.data.getLongValue("pid");
        Long rightToken = DataManager.tokens.get(pid);
        if (rightToken == null || rightToken != token) {
            gameResp.send(ErrCode.UNKONW_ERR, "token已失效,请重新登陆");
            return;
        }

        if (GameContextListener.gameStatus == GameStatus.MAINTAIN) {
            gameResp.send(ErrCode.SYS_MAINTINING);
        } else {
            GameAct gameAct = GameActs.get(msgid);
            gameAct.exec(gameReq, gameResp);
        }
    }
}
