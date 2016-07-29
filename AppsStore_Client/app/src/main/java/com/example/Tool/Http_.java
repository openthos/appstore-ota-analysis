package com.example.Tool;

public class Http_ {

	public static int ok = 200;
	public static int Partial_Content = 206;
	public static int Not_Found = 404;

	public static int  check(int check){
		switch(check){
			case 200:
				return 200;
			case 206:
				return 206;
			case 404:
				throw new RuntimeException("没有找到文件");
		}
		return -1;
	}
}