package com.ppgames.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Data {

    public static JSONObject getDataObj(String fname) throws Exception {
        InputStream fstream = null;
        BufferedReader input = null;
        try {
            fstream = Data.class.getResourceAsStream("/" + fname);
            input = new BufferedReader(new InputStreamReader(fstream, "utf-8"));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = input.readLine()) != null) {
                content.append(line);
            }
            JSONObject data = JSON.parseObject(content.toString());
            System.out.println(fname + " init succ");
            return data;
        } finally {
            if (fstream != null) {
                fstream.close();
            }
            if (input != null) {
                input.close();
            }
        }
    }

    public static JSONArray getDataArr(String fname) throws Exception {
        InputStream fstream = null;
        BufferedReader input = null;
        try {
            fstream = Data.class.getResourceAsStream("/" + fname);
            input = new BufferedReader(new InputStreamReader(fstream, "utf-8"));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = input.readLine()) != null) {
                content.append(line);
            }
            JSONArray data = JSON.parseArray(content.toString());
            System.err.println(fname + " init succ");
            return data;
        } finally {
            if (fstream != null) {
                fstream.close();
            }
            if (input != null) {
                input.close();
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
        BufferedReader input = null;
        try {
            fstream = Data.class.getResourceAsStream("/" + fname);
            input = new BufferedReader(new InputStreamReader(fstream, "utf-8"));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = input.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } finally {
            if (fstream != null) {
                fstream.close();
            }
            if (input != null) {
                input.close();
            }
        }
    }
    public static <T> void getDataGeneric(String fname, T list) throws  Exception{
        InputStream fstream = null;
        BufferedReader input = null;
        try {
            fstream = Data.class.getResourceAsStream("/" + fname);
            input = new BufferedReader(new InputStreamReader(fstream, "utf-8"));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = input.readLine()) != null) {
                content.append(line);
            }
            list = JSON.parseObject(content.toString(), new TypeReference<T>(){});
            //System.err.println(fname + " init succ");
        } finally {
            if (fstream != null) {
                fstream.close();
            }
            if (input != null) {
                input.close();
            }
        }
    }



}
