package com.xxgames.core;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GameResp {

    public JSONObject data;

    private PrintWriter writer;

    public GameResp(int msgid, HttpServletResponse resp) throws IOException {
        // avoid cross domain
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("UTF-8");
        data = new JSONObject();
        data.put("msgid", msgid + 1);
        writer = resp.getWriter();
    }

    public void send(int errCode) {
        send(errCode, "");
    }

    public void send(int errCode, String errMsg) {
        data.put("errcode", errCode);
        data.put("errmsg", errMsg);
        writer.println(data);
    }

    @Override
    public String toString() {
        return "GameResp [data=" + data + "]";
    }

}