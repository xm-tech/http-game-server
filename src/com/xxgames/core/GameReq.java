package com.xxgames.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.handler.GameActs;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class GameReq {

    private final HttpServletRequest req;

    public JSONObject data;

    public GameReq(HttpServletRequest req) throws UnsupportedEncodingException {
        this.req = req;
        this.req.setCharacterEncoding("UTF-8");
        String dataStr = this.req.getParameter("data");
        String decodeData = URLDecoder.decode(dataStr, "UTF-8");
        data = JSON.parseObject(decodeData);
    }

    boolean isOk() {
        // TODO other checks
        if (data == null) {
            return false;
        }
        int msgid = data.getIntValue("msgid");
        return GameActs.contains(msgid);
    }

    private String getIp() {
        String ip;
        if (req.getHeader("x-forwarded-for") == null) {
            ip = req.getRemoteAddr();
        } else {
            ip = req.getHeader("x-forwarded-for");
        }
        if (ip.contains(",")) {
            String[] array = ip.split(",");
            ip = array[0];
        }
        return ip;
    }

    @Override
    public String toString() {
        return "GameReq [data=" + data + "], ip=" + getIp();
    }
}
