package com.appstore.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {
	public static void main(String[] args) {
		File file=new File("/home/entity/android_x86_64.iso");
		long time1 = System.currentTimeMillis();
		System.out.println("MD5为：" + MD5.getFileMD5(file));  
		long time2 = System.currentTimeMillis();   
		System.out.println("耗时 " + (time2 - time1) / 1000.00 + " 秒"); 
	}
	
	// 计算文件的 MD5 值
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[8192];
		int len;
		try

		{
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer)) != -1) {
				digest.update(buffer, 0, len);
			}
			BigInteger bigInt = new BigInteger(1, digest.digest());
			return bigInt.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
