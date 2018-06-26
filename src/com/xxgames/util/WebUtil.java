package com.xxgames.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebUtil {

    private static final Logger logger = Logger.getLogger(WebUtil.class);

    public static String postData(String url, int time) throws Exception {
        if (url == null || url.length() == 0) {
            return "";
        }
        URL urls = new URL(url);

        // 获取返回数据
        try {
            HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
            connection.setConnectTimeout(time);
            connection.setReadTimeout(time);
            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();

            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("")) {
                    continue;
                }
                sb.append(line);
            }
            br.close();
            connection.disconnect();
            return sb.toString();
        } catch (Exception e) {
            throw e;
//			return "";
        }

    }

    public static BufferedReader getUrlReader(String url) throws Exception {
        if (url == null || url.length() == 0) {
            return null;
        }
        URL urls = new URL(url);
        // 获取返回数据
        BufferedReader in = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (Exception e) {
            logger.error("error:" + e.getMessage() + "url=" + url);
        }
        return in;
    }

    public static void main(String[] args) throws Exception {
//		String url = "http://121.199.21.216/redtower/uc/VerifySessionClient.php?sid="+ "ssh1gameeafcec01c14e46b89f814d8849f9e1bf152210";
//		String postData = postData(url, 5000);
//		System.err.println("postData="+postData);
        String ppid = "xxxx";
        String postData = WebUtil.postData("http://121.199.21.216/redtower/vip.php?ppid=" + ppid + "&type=0", 5000);
        System.err.println(postData);
    }
}
