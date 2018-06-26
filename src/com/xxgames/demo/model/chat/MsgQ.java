package com.xxgames.demo.model.chat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.util.TimeUtil;

import java.util.LinkedList;

public class MsgQ {
    public static LinkedList<ChatMsg> chatQ = new LinkedList<>();

    public static void pushChat(ChatMsg msg) {
        synchronized (chatQ) {
            int qSize = chatQ.size();
            if (qSize > 50) {
                chatQ.remove();
            }
            System.err.println("chat msg put: " + msg);
            chatQ.add(msg);
        }
    }
    public static JSONArray getCharMsgLogin(){
        LinkedList<ChatMsg> chatList = new LinkedList<>();
        JSONArray arr = new JSONArray();
        int msg_count = 0 ;
        for (int i = chatQ.size()-1 ; i >= 0; i -- ){
            ChatMsg chatMsg = chatQ.get(i);
            chatList.addFirst(chatMsg);
            if (++msg_count == 20){
                break;
            }
        }
        for (ChatMsg chatMsg:chatList) {
            arr.add(chatMsg.toJSON());
            System.err.println("msg out: " + chatMsg.toJSON());
        }

        return arr;
    }
    public static JSONObject getChatMsg(int startTime,boolean isFirstHeartBeat) {
        JSONArray arr = new JSONArray();
        if (isFirstHeartBeat){
            arr = getCharMsgLogin();
        }
        int maxTime = 0;
        for (int i = 0; i < chatQ.size(); i++) {
            ChatMsg chatMsg = chatQ.get(i);
            if (chatMsg.time > startTime) {
                arr.add(chatQ.get(i).toJSON());
                System.err.println("msg out: " + chatQ.get(i).toJSON());
                maxTime = chatMsg.time;
            }
        }
        JSONObject ret = new JSONObject();
        ret.put("chatList", arr);
        if (maxTime == 0) {
            maxTime = TimeUtil.nowInt();
        }
        ret.put("maxTime", maxTime);
        return ret;
    }
}
