package com.example.android_app_stroe;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Download_File.download_file;
import com.example.Tool.HttpsTest;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import sql.Sql_Lite;

import com.example.Download_File.download_file_queue;

public class Why_Hate extends Activity {


	TextView user_comment;
	Button btn_comment;
	//private int file_id ;
	String comment_detail;
	@Override
	public void onCreate(Bundle o){
		super.onCreate(o);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.why_hate);
		user_comment = (TextView)findViewById(R.id.why_so_hate);
		btn_comment = (Button)findViewById(R.id.sbumit);
		btn_comment.setOnClickListener(new click());
	}
	class click implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			comment_detail = user_comment.getText().toString();
			Toast.makeText(Why_Hate.this,comment_detail,Toast.LENGTH_SHORT).show();
			String urlpath = "";
			urlpath ="https://dev.openthos.org/appstore/AppStoreServer/comment";
			System.out.println(urlpath);
			try {
				HttpsURLConnection conn = (HttpsURLConnection) HttpsTest.getHttpsURLConnection(urlpath);
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				//conn.setDoInput(true);
				conn.setUseCaches(false);
				StringBuffer params = new StringBuffer();
				// 表单参数与get形式一样
				String id = "";

				Intent intent1 = getIntent();

				id = intent1.getStringExtra("id");

				params.append("aid").append("=").append(id).append("&").append("comment").append("=").append(comment_detail).append("&").append("type=add");
				System.out.println(comment_detail);
				System.out.println(id);
				byte[] bypes = params.toString().getBytes();
				conn.getOutputStream().write(bypes);// 输入
				conn.getInputStream();
			}catch (Exception e){
				e.printStackTrace();
			}

			finish();

			//startActivity(intent1);
		}



	}

}
