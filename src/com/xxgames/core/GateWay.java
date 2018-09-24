package com.xxgames.core;

import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.handler.GameActs;
import com.xxgames.demo.handler.MsgIds;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// TODO 解偶
public class GateWay extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            long beginTime = System.currentTimeMillis();
            GameReq gameReq = new GameReq(req);

            Loggers.gateWay.debug(gameReq.toString());
            if (gameReq.isOk()) {
                int msgid = gameReq.data.getIntValue("msgid");
                GameResp gameResp = new GameResp(msgid, resp);
                if (GameContextListener.gameStatus == GameStatus.MAINTAIN) {
                    gameResp.send(ErrCode.SYS_MAINTINING);
                    return;
                }

                if (msgid == MsgIds.Login.getVal()) {
                    GameAct gameAct = GameActs.get(msgid);
                    gameAct.exec(gameReq, gameResp);
                } else {
                    process(gameReq, gameResp);
                }
                long endTime = System.currentTimeMillis();
                Loggers.gateWay.debug("exec time[" + (endTime - beginTime) + " ms]");
                Loggers.gateWay.debug(gameResp.toString());

            } else {
                Loggers.gateWay.error(gameReq.toString());
                sysErr(resp);
            }
        } catch (Exception e) {
            Loggers.gateWay.error(e.getMessage(), e);
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

    private void process(GameReq gameReq, GameResp gameResp) {
        int msgid = gameReq.data.getIntValue("msgid");
        long token = gameReq.data.getLongValue("token");
        long pid = gameReq.data.getLongValue("pid");
        Long rightToken = DataManager.tokens.get(pid);
        if (rightToken == null || rightToken != token) {
            gameResp.send(ErrCode.UNKONW_ERR, "token已失效,请重新登陆");
            return;
        }

        GameAct gameAct = GameActs.get(msgid);
        gameAct.exec(gameReq, gameResp);
    }
}
