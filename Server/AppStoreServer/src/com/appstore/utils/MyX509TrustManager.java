package com.appstore.utils;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager {
	
	public static void main(String[] args) throws Exception {
    	String urlString = "https://dev.openthos.org/appstore/AppStoreServer/App_store?operate=software&time=1&classify=6";
        //HttpsUtil.getHttpsURLConnection(urlString).getInputStream();
    	System.out.println(HttpsUtil.getHttpsURLConnection(urlString));
    }
		
}