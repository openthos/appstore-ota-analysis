package com.example.Tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsTest {

    public String get_comment(String id) throws IOException {
        HttpsURLConnection conn=(HttpsURLConnection)HttpsTest.getHttpsURLConnection("https://dev.openthos.org/appstore/AppStoreServer/comment");

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        StringBuffer params = new StringBuffer();
        // 表单参数与get形式一样
        params.append("aid").append("=").append(id).append("&").append("type").append("=").append("query");
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数

        // 设置属性

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) { // 请求成功
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            System.out.println(result);

            inputStream.close();
            return result.toString();
        }

        conn.disconnect();
        return "";

    }

    public static Object getHttpsURLConnection(String path) throws IOException {

        URL url = new URL(path);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        //urlConnection.setRequestMethod("GET");

        // 设置SSLSocketFoactory，这里有两种：1.需要安全证书 2.不需要安全证书；看官且往下看
        if (urlConnection instanceof HttpsURLConnection) {
            SSLContext sslContext =HttpsTest.getSSLContext();
            if (sslContext != null) {
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                ((HttpsURLConnection) urlConnection).setSSLSocketFactory(sslSocketFactory);
                ((HttpsURLConnection) urlConnection).setHostnameVerifier(HttpsTest.hostnameVerifier);

                return urlConnection;
            }
        }
        else{
            return urlConnection;
        }
        return null;

    }

    public static SSLContext getSSLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)  {}

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {}

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    private static HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
}
