package com.ppgames.util;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StrUtil {
    private static final Logger logger = Logger.getLogger(StrUtil.class);

    public static Charset UTF8 = Charset.forName("UTF-8");

    public static boolean isEmpty(String input) {
        if (input == null || input.length() == 0 || input.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String[] input) {
        if (input == null) {
            return true;
        }
        return input.length == 0;
    }

    public static boolean isNull(String input) {
        return input == null;
    }

    public static String newStr(byte[] bytes) {
        return newStr(bytes, UTF8);
    }

    public static String newStr(byte[] bytes, Charset c) {
        if (bytes == null) {
            return null;
        }
        return new String(bytes, c);
    }

    public static byte[] getBytes(String str) {
        return getBytes(str, UTF8);
    }

    public static byte[] getBytes(String str, Charset c) {
        if (str == null) {
            return null;
        }
        return str.getBytes(c);
    }

    public static boolean contains(String src, String desc) {
        if (isNull(desc)) {
            return false;
        }
        int indexOf = src.indexOf(desc);
        if (indexOf == -1) {
            return false;
        }
        return true;
    }

    public static boolean contains(String src, String[] dests) {
        for (String d : dests) {
            if (contains(src, d)) {
                return true;
            }
        }
        return false;
    }

    public static String[] split(String src, String regex) {
        if (isEmpty(regex)) {
            return null;
        }
        if (isEmpty(src)) {
            return null;
        }
        return src.split(regex);
    }

    public static String replaceAll(String msg, String regex, String replace) {
        return msg.replaceAll(regex, replace);
    }

    public static String format(String s, Object p[]) {
        int n = p != null? p.length : 0;
        int i = 0;
        String t = (new StringBuilder()).append("{").append(i).append("}").toString();
        for (int j = s.indexOf(t); j >= 0; j = s.indexOf(t)) {
            if (i < n) {
                s = s.replace(t, p[i].toString());
            }
            i++;
            t = (new StringBuilder()).append("{").append(i).append("}").toString();
        }

        return s;
    }

    public static final String md5sum(String input) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte data[] = md.digest(input.getBytes());
            int b = 0;
            for (int i = 0; i < data.length; i++) {
                b = data[i];
                if (b < 0) {
                    b += 256;
                }
                if (b < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(b));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return buf.toString();
    }

    public static String toStr(int[] data, String split) {
        StringBuilder ret = new StringBuilder();
        for (int e : data) {
            ret.append(e).append(split);
        }
        return ret.substring(0, ret.length() - 1);
    }

    public static String toStr(Set<? extends Object> data, String split) {
        if (data == null || data.size() == 0) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        for (Object e : data) {
            ret.append(e).append("").append(split);
        }
        return ret.substring(0, ret.length() - 1);
    }

    public static String toStr(List<Integer> data, String split) {
        if (data == null || data.size() == 0) {
            return "";
        }
        StringBuilder ret = new StringBuilder();
        for (int e : data) {
            ret.append(e).append(split);
        }
        return ret.substring(0, ret.length() - 1);
    }

    public static String toStr(String[] data, String split) {
        StringBuilder ret = new StringBuilder();
        for (String e : data) {
            ret.append(e).append(split);
        }
        return ret.substring(0, ret.length() - 1);
    }

    public static int[] strArrToIntArr(String str[]) {
        int[] result = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            result[i] = Integer.parseInt(str[i]);
        }
        return result;
    }

    public static String toStr(JSONArray jsonArr, String split) {
        String[] strArr = new String[jsonArr.size()];
        jsonArr.toArray(strArr);
        return toStr(strArr, split);
    }

    public static JSONArray toJSONArr(String str, String split) {
        String[] splits = str.split(split);
        JSONArray jsonArr = new JSONArray();
        Collections.addAll(jsonArr, splits);
        return jsonArr;
    }

    public static String encode(String msg) {
        return encode(msg, "utf-8");
    }

    /**
     * 去掉最末的逗号
     *
     * @param input
     *
     * @return
     */
    public static String dropTheLastDot(String input) {
        if (isEmpty(input)) {
            return input;
        }
        if (!input.endsWith(",")) {
            return input;
        }
        input = input.substring(0, input.length() - 1);
        return input;
    }

    public static String encode(String msg, String charset) {
        try {
            msg = URLEncoder.encode(msg, charset);
        } catch (UnsupportedEncodingException e) {
        }
        return msg;
    }

    public static JSONArray toJSONArr(HashSet<?> set) {
        if (set == null || set.size() == 0) {
            return new JSONArray();
        }
        JSONArray arr = new JSONArray();
        for (Object i : set) {
            arr.add(i);
        }
        return arr;
    }

    public static void main(String[] args) {
        // String f = "{0}{1}{2}{3}";
        // String[] p = new String[] { "pay", "201406181427", "aaa", "bbb" };
        // String format = format(f, p);
        // PrintUtil.println(format);
//		String moneyStr = "6.00";
//		Float moneyd = Float.valueOf(moneyStr);
//
//		int intValue = moneyd.intValue();
//		PrintUtil.println(intValue);
//
//		String replaceAll = StrUtil.replaceAll(moneyStr, ".00", "");
//		PrintUtil.println(replaceAll);
//		Integer valueOf = Integer.valueOf(replaceAll);
//		PrintUtil.println(valueOf);
//
//		byte[] bytes = StrUtil.getBytes("系统管理员");
//		System.out.println(bytes.length);
//
//		int[] data = { 0, 0, 0, 0, 0, 0 };
//		String str = StrUtil.toStr(data, ",");
//		System.out.println(str);
//
//		String md5sum = md5sum("mxmgc2014");
//		System.err.println(md5sum);
//
//		String desc = "完成{x}巡回赛奖励";
//		System.err.println(desc);
//		String succ = desc.replace("{x}", "北京");
//		System.err.println(desc);
//		System.err.println(succ);
//
//		String t = "0";
//		System.err.println(StrUtil.isEmpty(t));
//		
//		str = "1,2,3,4";
//		JSONArray jsonArr = toJSONArr(str, ",");
//		System.err.println("toJSONArr(\""+str+"\","+"\",\") : ");
//		for(int i=0;i<jsonArr.size();i++){
//			System.err.println(jsonArr.getIntValue(i));
//		}
//
//        HashSet<Integer> aids = new HashSet<>();
//        aids.add(3001);
//        aids.add(3002);
//        aids.add(3003);
//        JSONArray jsonArr = toJSONArr(aids);
//        System.err.println(jsonArr);

        String[] names = { "I", "love", "golang" };
        String join = StringUtils.join(names, ",");
        System.out.println(join);

        ArrayList<String> arrs = new ArrayList<>();
        arrs.add("i");
        arrs.add("love");
        arrs.add("c");
        String join1 = StringUtils.join(arrs, ":");
        System.out.println(join1);
        String joinWith = StringUtils.joinWith("|", arrs);
        System.out.println(joinWith);
    }

}
