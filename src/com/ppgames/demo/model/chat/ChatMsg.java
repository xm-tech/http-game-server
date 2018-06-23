package com.ppgames.demo.model.chat;

import com.alibaba.fastjson.JSONObject;
import com.ppgames.util.TimeUtil;

public class ChatMsg {
    long pid;
    String name;
    int vip;
    String msg;
    int time;

    public ChatMsg(long pid, String uname, int vip, String msg) {
        this.pid = pid;
        name = uname;
        this.vip = vip;
        this.msg = msg;
        time = TimeUtil.nowInt();
    }

    public JSONObject toJSON() {
        JSONObject out = new JSONObject();
        out.put("name", name);
        out.put("pid", pid);
        out.put("vip", vip);
        out.put("msg", msg);
        out.put("time", time);
        return out;
    }
}
