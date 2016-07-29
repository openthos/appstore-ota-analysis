/*
package com.example.android_app_stroe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class amusement extends Activity {
   LinearLayout mp3,mp4,jpg,txt;
   private ImageView search ;
	@Override
	public void onCreate(Bundle o){
		super.onCreate(o);
		setContentView(R.layout.amusement);
		mp3 = (LinearLayout)findViewById(R.id.ll_mp3);
		jpg = (LinearLayout)findViewById(R.id.ll_jpg);
	    mp4 = (LinearLayout)findViewById(R.id.ll_mp4);
		txt = (LinearLayout)findViewById(R.id.ll_txt);
		search = (ImageView)findViewById(R.id.daohang_search);
		click Click = new click();
		search.setOnClickListener(new click_());
		mp3.setOnClickListener(Click);
		jpg.setOnClickListener(Click);
		mp4.setOnClickListener(Click);
		txt.setOnClickListener(Click);
		
	}
	
	class click_ implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		  Intent intent = new Intent(amusement.this,Search.class);
		  startActivity(intent);
		}
		
	}
	class click implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(amusement.this,List_Software.class);
			if(arg0 == mp3){
				intent.putExtra("operate", "amusement");
				intent.putExtra("classify", "mp3");
			}
			else if(arg0 == mp4){
				intent.putExtra("operate", "amusement");
				intent.putExtra("classify", "mp4");
			}
			else if(arg0 == txt){
				intent.putExtra("operate", "amusement");
				intent.putExtra("classify", "txt");	
			}
			else if(arg0 == jpg){
				intent.putExtra("operate", "amusement");
				intent.putExtra("classify", "jpg");
			}
			startActivity(intent);
		}
		
	}
}
*/
