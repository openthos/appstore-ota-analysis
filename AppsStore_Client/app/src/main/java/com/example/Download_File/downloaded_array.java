package com.example.Download_File;

import java.util.ArrayList;

import android.content.Context;

import com.example.sql.Sql_Lite;

public class downloaded_array {

	private static ArrayList<String[]> downloaded = new ArrayList<String[]>();
	private static Sql_Lite sql_lite ;
	private static Context context ;
	public static void init_array(Context context_){
		context = context_;
		sql_lite = new Sql_Lite(context);
		downloaded = sql_lite.get_done_record();
	}


	public static  ArrayList<String[]> get_downloaded(){
		return downloaded ;
	}
	public static void delete_all(){
		downloaded.clear();
		sql_lite.delete_all_database();
	}

}