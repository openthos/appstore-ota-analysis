package com.appstore.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
	
	public static void main(String[] args) throws IOException {
		
		URL url =new URL("http://192.168.0.129:8080/AppStoreServer/comment?type=query&aid=10");
		HttpURLConnection conn=(HttpURLConnection)HttpsTest.getHttpsURLConnection(url);
		conn.setRequestMethod("GET");
//		conn.setRequestMethod("POST");
//		conn.setDoOutput(true);
//		conn.setDoInput(true);
//		conn.setUseCaches(false); 
//		
//        // 表单参数与get形式一样
//		StringBuffer params = new StringBuffer();
//		params.append("aid").append("=").append("10").append("&").append("comment").append("=").append("哈哈");
//		conn.getOutputStream().write(params.toString().getBytes("UTF-8"));
//        conn.getInputStream();
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
		}
		conn.disconnect();
		
	}
	
	public static Object getHttpsURLConnection(URL url) throws IOException {
		
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