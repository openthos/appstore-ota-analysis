package com.example.android_app_stroe;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Search extends Activity {

	EditText ed ;
	String keywords ;
	Button search ;
	@Override
	public void onCreate(Bundle o){
		super.onCreate(o);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //û�б���
		setContentView(R.layout.search);
		ed = (EditText)findViewById(R.id.ed);
		search = (Button)findViewById(R.id.search);

		search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				keywords = ed.getText().toString();
				keywords = keywords.trim();
				try {
					keywords =new String( keywords.getBytes("UTF-8"),"ISO8859-1");
					System.out.println(keywords);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!keywords.equals("")){
					Intent intent = new Intent(Search.this,List_Software.class);
					intent.putExtra("keywords", keywords);
					intent.putExtra("operate", "search");
					startActivity(intent);
				}
				else {
					ed.setHint("你还没有输入关键词");
				}
			}

		});

	}
}