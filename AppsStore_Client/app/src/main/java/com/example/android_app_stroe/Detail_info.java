package com.example.android_app_stroe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.Download_File.download_file;
import com.example.Download_File.download_file_queue;
import com.example.JSON.JSON;
import com.example.JSON.Software;
import com.example.JSON.game;
import com.example.Tool.Constant;
import com.example.Tool.HttpsTest;
import com.example.loadImage.load_jiemian;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Detail_info extends Activity {

	private List<Why_Hate_item> commentList = new ArrayList<Why_Hate_item>();

	private Drawable[] drawable;
	ListView user_report;
	//report_BaseAdapter reportBaseAdapter;
	TextView tv_soft_name, tv_private_name, soft_private_daxiao,
			tv_soft_version, tv_soft_download_count, tv_soft_language, tv_update, tv_auther, tv_introduce;
	Button report, app_download;
	String table_name;
	ScrollView sc;
	ProgressBar pb, pb_load;
	String id;
	String dev_id;
	ArrayList<String> array;
	RelativeLayout rl_pb;
	Gallery softgallery;
	private ImageView search, app_img;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	//private GoogleApiClient client;

	@Override
	public void onCreate(Bundle o) {
		super.onCreate(o);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_info);
		tv_soft_name = (TextView) findViewById(R.id.detail_soft_name);
		tv_private_name = (TextView) findViewById(R.id.soft_private_name);
		soft_private_daxiao = (TextView) findViewById(R.id.soft_private_daxiao);
		tv_soft_version = (TextView) findViewById(R.id.tv_soft_version);
		tv_soft_download_count = (TextView) findViewById(R.id.tv_soft_download_count);
		tv_soft_language = (TextView) findViewById(R.id.tv_soft_language);
		rl_pb = (RelativeLayout) findViewById(R.id.rl_pb);
		pb_load = (ProgressBar) findViewById(R.id.pb_load);
		pb = (ProgressBar) findViewById(R.id.pb);
		tv_update = (TextView) findViewById(R.id.tv_update);
		tv_auther = (TextView) findViewById(R.id.tv_auther);
		tv_introduce = (TextView) findViewById(R.id.tv_introduce);
		//other_product = (Button) findViewById(R.id.bt_other_product);
		sc = (ScrollView) findViewById(R.id.just_sc);


		commentAdapter adapter = new commentAdapter(this,R.layout.detail_info_comment_item,commentList);
		user_report = (ListView) findViewById(R.id.comment);
		user_report.setAdapter(adapter);




		search = (ImageView) findViewById(R.id.daohang_search);
		report = (Button) findViewById(R.id.report);
		softgallery = (Gallery) findViewById(R.id.gallery);
		search.setOnClickListener(new click_());


		//App详细界面图片动态显示
		app_img = (ImageView) findViewById(R.id.soft_img);
		final ImageView imageView = app_img;
		final String keys = getIntent().getStringExtra("key");
		final Object obj = getIntent().getSerializableExtra("obj");
		String url = null;
		if (keys.equals("Software")) {
			url = Constant.urlpath + "Software/" +
					(((Software) obj).getsoft_id()) + "/touxiang.jpg";

		} else if (keys.equals("auther_software") || keys.equals("software")) {
			url = Constant.urlpath + "Software/" +
					(((Software) obj).getsoft_id()) + "/touxiang.jpg";
		}else if (keys.equals("auther_game") || keys.equals("game")) {
			url = Constant.urlpath + "Game/" +
					(((game) obj).getid()) + "/touxiang.jpg";
		}
		Bitmap bitmap = getHttpBitmap(url);
		imageView.setImageBitmap(bitmap);




		//App详细界面下载功能

		app_download = (Button) findViewById(R.id.app_download);
		app_download.setOnClickListener(new OnClickListener() {
			String key = getIntent().getStringExtra("key");
			Object obj = getIntent().getSerializableExtra("obj");
			String name = getIntent().getStringExtra("soft_name");

			@Override
			public void onClick(View arg0) {
				if (arg0 == app_download) {
					if (key.equals("auther_game") || key.equals("game")) {
						download_file_queue.Add_item(
								new download_file((((game) obj).getid()), (((game) obj).getgame_name())
										, getApplicationContext(), "game"));
					} else if (key.equals("auther_software") || key.equals("software")) {
						download_file_queue.Add_item(
								new download_file((((Software) obj).getsoft_id()), (((Software) obj).getsoft_name())
										, getApplicationContext(), "software"));
					} else if (key.equals("Software")) {
						download_file_queue.Add_item(
								new download_file((((Software) obj).getsoft_id()), (((Software) obj).getsoft_name())
										, getApplicationContext(), "software"));
					}


				}


			}
		});


		Intent intent = getIntent();
		String soft_name = intent.getStringExtra("soft_name");
		String introduce = intent.getStringExtra("introduce");
		String size = intent.getStringExtra("size");
		String download_count = intent.getStringExtra("download_count");
		String version = intent.getStringExtra("version");
		id = intent.getStringExtra("id");
		String update_time = intent.getStringExtra("update_time");
		String dev_name = intent.getStringExtra("dev_name");
		dev_id = intent.getStringExtra("dev_id");
		String language = intent.getStringExtra("language");
		table_name = intent.getStringExtra("table_name");
		tv_soft_name.setText(soft_name);
		tv_private_name.setText(" " + soft_name);
		soft_private_daxiao.setText(size + "M");
		tv_soft_version.setText("版本号：" + version);
		tv_soft_download_count.setText("下载量：" + download_count + "次");
		tv_soft_language.setText("语言：" + language);
		tv_update.setText("更新时间；" + update_time);
		tv_auther.setText("作者：" + dev_name);
		tv_introduce.setText(introduce);
		click Click = new click();
		//  other_product.setOnClickListener(Click);
		report.setOnClickListener(Click);
		//new thread(handler).start();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		//client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

			try{
			String comment_result = new HttpsTest().get_comment(id);
			JSONArray jsonArray = new JSONArray(comment_result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String get_aid = (String) jsonObject.get("aid");
				String get_content = (String) jsonObject.get("content");
				String get_time = (String) jsonObject.get("time");

				Log.d("2222",get_content+"");
				commentList.add(new Why_Hate_item(get_content,get_time));
			}

			}catch (Exception e){
				e.printStackTrace();
			}

	}





	public static Bitmap getHttpBitmap (String url){
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			//获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
			//设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			//连接设置获得数据流
			conn.setDoInput(true);
			//不使用缓存
			conn.setUseCaches(false);
			//这句可有可无，没有影响
			//conn.connect();
			//得到数据流
			InputStream is = conn.getInputStream();
			//解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			//关闭数据流
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;

	}






	/*@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Detail_info Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.example.android_app_stroe/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Detail_info Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.example.android_app_stroe/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}*/

	class click_ implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Detail_info.this, Search.class);
			startActivity(intent);
		}

	}

	class click implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (arg0 == report) {
				Intent intent = new Intent(Detail_info.this, Why_Hate.class);
				intent.putExtra("id", id);
				startActivityForResult(intent,0);
			}
			/*else if(arg0 == other_product){
				Intent intent = new Intent(Detail_info.this,List_Software.class);
				intent.putExtra("operate", "auther_other_work");
				intent.putExtra("dev_id", dev_id);
				startActivity(intent);
			}*/

		}

	}


	@Override
	public void onResume() {
		super.onResume();

	}


	/*private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == 1) {
				user_report.setVisibility(View.VISIBLE);
				user_report.setAdapter(new user_comment_adapter(getApplicationContext()));
				ListView_height.setListViewHeightBasedOnChildren(user_report);
				pb.setVisibility(View.GONE);
			} else if (msg.arg1 == 2) {
				pb_load.setVisibility(View.GONE);
				rl_pb.setVisibility(View.GONE);
				softgallery.setVisibility(View.VISIBLE);
				softgallery.setAdapter(new ImageAdapter(getApplicationContext()));
			}
		}
	};


	class user_comment_adapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public user_comment_adapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return array.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			TextView report;
			if (arg1 == null) {

				arg1 = mInflater.inflate(R.layout.user_comment_xml, null);
				report = (TextView) arg1.findViewById(R.id.tv_user_report);
				arg1.setTag(report);
			} else {
				report = (TextView) arg1.getTag();

			}
			report.setText(array.get(arg0));
			return arg1;
		}

	}

	class ImageAdapter extends BaseAdapter {

		Context context;

		public ImageAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (drawable != null) {
				return drawable.length;
			}
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ImageView image = new ImageView(context);
			image.setImageDrawable(drawable[arg0]);
			image.setAdjustViewBounds(false);
	 		image.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			return image;
		}

	}

	class thread extends Thread {
		Handler handler;

		public thread(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void run() {
			array = JSON.get_estimate(table_name, id);
			if (array == null) {
				array = new ArrayList<String>();
			}
			Message msg = Message.obtain();
			msg.arg1 = 1;
			handler.sendMessage(msg);


			if (table_name.equals("estimate_game")) {
				load_jiemian load = new load_jiemian(Integer.parseInt(id), "Game");
				drawable = load.get_jiemian();
			} else if (table_name.equals("estimate_software")) {
				load_jiemian load = new load_jiemian(Integer.parseInt(id), "Software");
				drawable = load.get_jiemian();
			}
			Message msg1 = Message.obtain();
			msg1.arg1 = 2;
			handler.sendMessage(msg1);
			System.out.println("得到了嘛?");
		}
	}*/


	//以下都是7.14日改的
	/*private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

				user_report.setVisibility(View.VISIBLE);
				user_report.setAdapter(new report_BaseAdapter(getApplicationContext()));
				ListView_height.setListViewHeightBasedOnChildren(user_report);
				pb.setVisibility(View.GONE);

		}
	};*/
	/*class report_BaseAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public report_BaseAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return download_file_queue.get_ArrayList().size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		public View getView(int position , View convertView, ViewGroup parent){
			TextView report;
			if (convertView == null) {

				convertView = mInflater.inflate(R.layout.detail_info_comment_item, null);
				report = (TextView) convertView.findViewById(R.id.comment_item);
				convertView.setTag(report);
			} else {
				report = (TextView) convertView.getTag();

			}
			report.setText(array.get(position));
			return  convertView;


		}

	}

	class thread extends Thread {
		Handler handler;

		public thread(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void run() {
			array = JSON.get_estimate(table_name, id);
			if (array == null) {
				array = new ArrayList<String>();
			}
			Message msg = Message.obtain();
			msg.arg1 = 1;
			handler.sendMessage(msg);


			if (table_name.equals("estimate_game")) {
				load_jiemian load = new load_jiemian(Integer.parseInt(id), "Game");
				drawable = load.get_jiemian();
			} else if (table_name.equals("estimate_software")) {
				load_jiemian load = new load_jiemian(Integer.parseInt(id), "Software");
				drawable = load.get_jiemian();
			}
			Message msg1 = Message.obtain();
			msg1.arg1 = 2;
			handler.sendMessage(msg1);
			System.out.println("得到了嘛?");
		}
	}*/
	public class commentAdapter extends ArrayAdapter<Why_Hate_item>{
		private int resourceId;
		public commentAdapter(Context context, int textViewResourceId, List<Why_Hate_item>objects){
			super(context,textViewResourceId,objects);
			resourceId = textViewResourceId;
		}
		public View getView(int position , View convertView, ViewGroup parent) {
			Why_Hate_item comment_item = getItem(position);
			View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
			TextView content = (TextView)view.findViewById(R.id.comment_item);
			TextView time = (TextView)view.findViewById(R.id.comment_time);
			content.setText(comment_item.getContent());
			time.setText(comment_item.getTime());
			return  view;
		}
	}
}













