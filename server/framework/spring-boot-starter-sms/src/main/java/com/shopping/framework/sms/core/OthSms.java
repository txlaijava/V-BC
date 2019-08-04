package com.shopping.framework.sms.core;

import org.nutz.json.Json;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 其他短信接口
 */
public class OthSms {
    private String url;
    private String id;
    private String pwd;

    public OthSms(String url, String id, String pwd) {
        this.url = url;
        this.id = id;
        this.pwd = pwd;
    }

    public int SendSmsNew(String mobile, String content) throws UnsupportedEncodingException {
        //Integer x_ac = Integer.valueOf(10);
        HttpURLConnection httpconn = null;
        int resultCode = 0;
        // String result = "-20";
        // String memo = content.length() < 70 ? content.trim() : content.trim()
        // .substring(0, 70);

        //usr=http://dx.ipyy.net/sms.aspx

        StringBuilder sb = new StringBuilder();
        sb.append(this.url);
        sb.append("?Uid=").append(this.id);
        sb.append("&Key=").append(this.pwd);
        sb.append("&smsMob=").append(mobile);
        sb.append("&smsText=").append(URLEncoder.encode(content, "GBK"));
        try {
            URL url = new URL(sb.toString());
            httpconn = (HttpURLConnection) url.openConnection();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(httpconn.getInputStream()));
            String result = rd.readLine();
            resultCode = Integer.parseInt(result);
            rd.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpconn != null) {
                httpconn.disconnect();
                httpconn = null;
            }
        }

        return resultCode;
    }

    public int sendSms(String mobile, String content) throws UnsupportedEncodingException {
        //Integer x_ac = Integer.valueOf(10);
        HttpURLConnection httpconn = null;
        int resultCode = 0;
        // http://dx.ipyy.net/sms.aspx?action=send&userid=&account=账号&password=密码&mobile=15023239810,13527576163&content=内容&sendTime=&extno=
        StringBuilder sb = new StringBuilder();
        sb.append(this.url);
        sb.append("?action=send");
        sb.append("&userid=");

        sb.append("&account=").append(this.id);
        sb.append("&password=").append(this.pwd);
        sb.append("&mobile=").append(mobile);
        sb.append("&content=").append(URLEncoder.encode("【红商城】" + content, "UTF-8"));
        sb.append("&sendTime=");
        sb.append("&extno=");
        try {
            URL url = new URL(sb.toString());
            httpconn = (HttpURLConnection) url.openConnection();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(httpconn.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = rd.readLine()) != null) {
                buffer.append(line);
            }
            // resultCode=Integer.parseInt(result);
            rd.close();
            System.out.println(buffer.toString());
            Map m = Json.fromJson(HashMap.class, buffer.toString());

            if ("Success".equalsIgnoreCase(m.get("returnstatus").toString())) {
                resultCode = Integer.parseInt(m.get("successCounts").toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpconn != null) {
                httpconn.disconnect();
                httpconn = null;
            }
        }

        return resultCode;
    }


    public static void main(String[] agrs) throws UnsupportedEncodingException {
        OthSms sb = new OthSms("http://dx.ipyy.net/smsJson.aspx", "xm000075", "xm00007546");
        sb.sendSms("13528822599", "您的上网验证码为:898230");
    }
}

