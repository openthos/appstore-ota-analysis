package com.example.android_app_stroe;

import android.app.Activity;
import android.os.Bundle;

import com.example.sql.Sql_Lite;

public class test_sql extends Activity {

	@Override
	public void onCreate(Bundle o ){
		super.onCreate(o);
		
		Sql_Lite db = new Sql_Lite(this);
		db.delete_all_database();

	}
	
}
