package com.example.android_app_stroe;


import com.example.Download_File.download_file_queue;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TabHost;

import java.io.IOException;

public class Main_face extends ActivityGroup {
	TabHost tabHost;
	RadioButton paihang,software,game,download ,update;
	public static Handler handler;
	@Override
	public void onCreate(Bundle o){
		super.onCreate(o);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_tabhost);

		paihang = (RadioButton) findViewById(R.id.paihang);
		software = (RadioButton) findViewById(R.id.software);
		// gexing = (RadioButton) findViewById(R.id.yule);
		game = (RadioButton) findViewById(R.id.game);
		download = (RadioButton) findViewById(R.id.doweload);
		update = (RadioButton) findViewById(R.id.update);

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.setup(getLocalActivityManager());

		tabHost.addTab(tabHost.newTabSpec("first").setIndicator("First").setContent(new Intent(this,MainActivity.class)));
		// tabHost.addTab(tabHost.newTabSpec("second").setIndicator("Second").setContent(new Intent(this,amusement.class)));
		tabHost.addTab(tabHost.newTabSpec("third").setIndicator("Third").setContent(new Intent(this,Game.class)));
		tabHost.addTab(tabHost.newTabSpec("fourth").setIndicator("Fourth").setContent(new Intent(this,SoftWare.class)));
		tabHost.addTab(tabHost.newTabSpec("fifth").setIndicator("Fifth").setContent(new Intent(this,DownLoading.class)));
		tabHost.addTab(tabHost.newTabSpec("six").setIndicator("Six").setContent(new Intent(this,Update.class)));
		tabHost.addTab(tabHost.newTabSpec("six").setIndicator("Six").setContent(new Intent(this,Update.class)));
		RadioButtonclick click = new RadioButtonclick();

		paihang.setOnClickListener(click);
		software.setOnClickListener(click);
		// gexing.setOnClickListener(click);
		game.setOnClickListener(click);
		download.setOnClickListener(click);
		update.setOnClickListener(click);

		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Log.d("update", "dialog出现了吗");
				AlertDialog.Builder builder = new AlertDialog.Builder(Main_face.this);
				builder.setMessage("确认重启吗？");

				builder.setTitle("提示");

				builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						try {
							Runtime.getRuntime().exec("su");
							Runtime.getRuntime().exec("reboot");
						} catch (IOException e) {
							e.printStackTrace();
						}
						Log.d("REBOOT", "重启了");

					}
				});

				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

				builder.create().show();
			}
		}; //7.22接受消息,Sql_Lite的消息，然后弹出对话框
//
	}
	class RadioButtonclick implements OnClickListener{

		@Override
		public void onClick(View who) {
			// TODO Auto-generated method stub
			if(who == paihang){
				tabHost.setCurrentTabByTag("first");
			}
			else if(who == software ){
				tabHost.setCurrentTabByTag("fourth");
			}
		/*	else if(who == gexing){
				tabHost.setCurrentTabByTag("second");
			}*/
			else if(who == game){
				tabHost.setCurrentTabByTag("third");
			}
			else if(who == download){
				tabHost.setCurrentTabByTag("fifth");
			}
			else if(who == update){
				tabHost.setCurrentTabByTag("six");
			}

		}

	}

}










