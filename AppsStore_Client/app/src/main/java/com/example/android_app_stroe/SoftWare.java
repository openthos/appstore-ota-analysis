package com.example.android_app_stroe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SoftWare extends Activity {

	/*TextView soft_for_man ,soft_for_woman;*/
	LinearLayout soft_for_sys,soft_for_chat,soft_for_movie,soft_for_read,soft_for_map,soft_for_theame
			,soft_for_out,soft_for_tool,soft_for_office,soft_for_children;
	private ImageView search;
	@Override
	public void onCreate(Bundle o){
		super.onCreate(o);
		setContentView(R.layout.software);
		click_soft click_for_all = new click_soft();
	   /* soft_for_man = (TextView) findViewById(R.id.soft_for_man);
	    soft_for_woman =  (TextView) findViewById(R.id.soft_for_woman);	*/
		soft_for_movie =  (LinearLayout) findViewById(R.id.soft_for_movie);
		soft_for_sys =  (LinearLayout) findViewById(R.id.soft_for_sys);
		soft_for_chat = (LinearLayout) findViewById(R.id.soft_for_chat);
		soft_for_read = (LinearLayout) findViewById(R.id.soft_for_read);
		soft_for_map = (LinearLayout) findViewById(R.id.soft_for_map);
		soft_for_theame = (LinearLayout) findViewById(R.id.soft_for_theame);
		soft_for_out = (LinearLayout) findViewById(R.id.soft_for_out);
		soft_for_tool = (LinearLayout) findViewById(R.id.soft_for_tool);
		soft_for_office = (LinearLayout) findViewById(R.id.soft_for_office);
		soft_for_children = (LinearLayout) findViewById(R.id.soft_for_children);
		search = (ImageView)findViewById(R.id.daohang_search);
		search.setOnClickListener(new click_());
		soft_for_sys.setOnClickListener(click_for_all);
		/*soft_for_man.setOnClickListener(click_for_all);
		soft_for_woman.setOnClickListener(click_for_all);*/
		soft_for_chat.setOnClickListener(click_for_all);
		soft_for_movie.setOnClickListener(click_for_all);
		soft_for_read.setOnClickListener(click_for_all);
		soft_for_map.setOnClickListener(click_for_all);
		soft_for_theame.setOnClickListener(click_for_all);
		soft_for_out.setOnClickListener(click_for_all);
		soft_for_tool.setOnClickListener(click_for_all);
		soft_for_office.setOnClickListener(click_for_all);
		soft_for_children.setOnClickListener(click_for_all);

	}
	class click_ implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(SoftWare.this,Search.class);
			startActivity(intent);
		}

	}
	class click_soft implements OnClickListener{
		String operate = "get_software";
		Intent intent = new Intent(SoftWare.this,List_Software.class);

		@Override
		public void onClick(View view) {
			intent.putExtra("operate", operate);
			// TODO Auto-generated method stub
            /* if(view == soft_for_man ){

            	 intent.putExtra("request", "男性专属");
            	 intent.putExtra("classify", 0);
             }
             else if(view == soft_for_woman){
            	 intent.putExtra("request", "女性专属");
            	 intent.putExtra("classify", 1);
             }
             else */
			if(view == soft_for_sys){
				intent.putExtra("request", "系统输入");
				intent.putExtra("classify", 1);
			}
			else if(view == soft_for_chat){
				intent.putExtra("request", "聊天通讯");
				intent.putExtra("classify", 2);
			}
			else if(view == soft_for_movie){
				intent.putExtra("request", "影音图像");
				intent.putExtra("classify", 3);
			}

			else if(view ==soft_for_read ){
				intent.putExtra("request", "阅读学习");
				intent.putExtra("classify", 4);
			}

			else if(view == soft_for_map){
				intent.putExtra("request", "生活地图");
				intent.putExtra("classify", 5);
			}

			else if(view == soft_for_theame){
				intent.putExtra("request", "壁纸主题");
				intent.putExtra("classify", 6);
			}
			else if(view == soft_for_out){
				intent.putExtra("request", "出行必用");
				intent.putExtra("classify", 7);
			}
			else if(view == soft_for_tool){
				intent.putExtra("request", "实用工具");
				intent.putExtra("classify", 8);
			}
			else if(view == soft_for_office){
				intent.putExtra("request", "办公商务");
				intent.putExtra("classify", 9);
			}
			else if(view == soft_for_children){
				intent.putExtra("request", "儿童亲子");
				intent.putExtra("classify", 10);
			}







			startActivity(intent);
		}

	}
}













