package org.ruanwei.demo.util;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class Sms {
    private static final String subuser = "07020200";
    private static final DateFormat timeFmt = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final String KEY = "DW0702TTQkJEM5UnM4TY";
    private static final String host = "sms.duowan.com";
    private static final String path = "/send/smssending_emay.jsp";

    public static void reportException(String phone, String str, Exception e) {
        String ip = "unknowen";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }

        String msg = str;

        msg += "ip " + ip + ", " + e.toString();

        send(phone, msg);
    }

    /**
     * @param phone 手机号
     * @param msg 信息
     * @return true成功,false失败
     */
    public static boolean send(String phone, String msg, String duowanpassport) {
        HttpClient httpClient = new DefaultHttpClient();
        URI uri;
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("userid", duowanpassport));
        qparams.add(new BasicNameValuePair("phone", phone));
        qparams.add(new BasicNameValuePair("subuser", subuser));
        String time = timeFmt.format(new Date());
        qparams.add(new BasicNameValuePair("time", time));
        qparams.add(new BasicNameValuePair("mac", DigestUtils.md5Hex(duowanpassport + phone + subuser + time + KEY)));
        qparams.add(new BasicNameValuePair("content", msg));
        String query = URLEncodedUtils.format(qparams, "utf8");
        try {
            uri = URIUtils.createURI("http", host, 80, path, query, null);
            HttpGet get = new HttpGet(uri);

            String response = httpClient.execute(get, new BasicResponseHandler());
            if (response.trim().equals("1"))
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }

    public static boolean send(String phone, String msg, String subuser, String key) {
        return send(phone, msg, "anonymous");
    }

    private static boolean send(String phone, String msg) {
        return send(phone, msg, "anonymous");
    }
}
