package com.xxgames.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class Data {

    public static JSONObject getDataObj(String fname) throws Exception {
        InputStream fstream = null;
        try {
            fstream = Data.class.getResourceAsStream("/" + fname);
            JSONObject data = JSON.parseObject(IOUtils.toString(fstream, "utf8"));
            System.out.println(fname + " init succ");
            return data;
        } finally {
            if (fstream != null) {
                fstream.close();
            }
        }
    }

    public static JSONArray getDataArr(String fname) throws Exception {
        InputStream fstream = null;
        try {
            fstream = Data.class.getResourceAsStream("/" + fname);
            JSONArray data = JSON.parseArray(IOUtils.toString(fstream, "utf8"));
            System.out.println(fname + " init succ");
            return data;
        } finally {
            if (fstream != null) {
                fstream.close();
            }
        }
    }

    public static JSONObject createRewardObj(int type, int id, int num) {
        JSONObject reward = new JSONObject();
        reward.put("type", type);
        reward.put("id", id);
        reward.put("num", num);
        return reward;
    }

    public static String getFileData(String fname) throws Exception {
        InputStream fstream = null;
        try {
            fstream = Data.class.getResourceAsStream("/" + fname);
            return IOUtils.toString(fstream, "utf8");
        } finally {
            if (fstream != null) {
                fstream.close();
            }
        }
    }


}
