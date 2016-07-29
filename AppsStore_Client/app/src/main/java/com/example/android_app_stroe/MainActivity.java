package com.example.android_app_stroe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Download_File.download_file;
import com.example.Download_File.download_file_queue;
import com.example.Download_File.download_file_queue_item;
import com.example.JSON.JSON;
import com.example.JSON.Software;
import com.example.JSON.game;
import com.example.Tool.Constant;
import com.example.Tool.State;
import com.example.loadImage.AsyncLoadImage;
import com.example.loadImage.load_jiemian;


public class MainActivity extends Activity {

	// 7.1
	class viewHolder_download {
		ProgressBar pb_down;
	}
	private viewHolder_download view_progeressBar;



	ViewPager viewpage;
	ListView paihang ;
	ScrollView scrool_all ;
	boolean load_flag = false ;
	ImageView[] xhc;
	private viewpage page_thread;
	click Click ;
	ArrayList<ImageView> img_page;
	ImageView search ;
	private ArrayList<HashMap<String,Object>> array;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //û�б���
		setContentView(R.layout.load);
		search = (ImageView) findViewById(R.id.daohang_search);
		Click = new click();
		search.setOnClickListener(Click);
		new load(load_handler).start();
		img_page = new ArrayList<ImageView>();
	}
	//����listview

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			viewpage.setCurrentItem(msg.arg1);
		}
	} ;
	class click implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this,Search.class);
			startActivity(intent);
		}

	}

	private Handler load_handler = new Handler(){

		@Override
		public void handleMessage(Message msg){
			if(msg.arg1 == 1){
				setContentView(R.layout.activity_main);

				paihang = (ListView) findViewById(R.id.list_paihang);
				scrool_all = (ScrollView)findViewById(R.id.scrool_paihang);
				viewpage = (ViewPager)findViewById(R.id.viewpager);


				search = (ImageView) findViewById(R.id.daohang_search);
				search.setOnClickListener(Click);
				ListViewAdapter paihangAdapter = new ListViewAdapter(getApplicationContext());

				paihang.setAdapter(paihangAdapter);
				ListView_height.setListViewHeightBasedOnChildren(paihang);


				viewpage = (ViewPager) findViewById(R.id.viewpager);


				viewpage.setAdapter(new MyPagerAdapter(img_page) );
				page_thread = new viewpage(handler);
				page_thread.start();
//					scrool_all.post(new xhcrunable());
				scrool_all.smoothScrollTo(0, 0);
			}
		}
	};

	public void onDestroy(){
		super.onDestroy();
		if(page_thread != null){
			page_thread.stop_();
		}

	}


	public class ListViewAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private AsyncLoadImage asyncLoadImage;
		public ListViewAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
			this.asyncLoadImage = new AsyncLoadImage();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;

			if(convertView == null){

				convertView = mInflater.inflate(R.layout.list_view_child,null);
				holder = new ViewHolder();
				view_progeressBar=new MainActivity.viewHolder_download();
				view_progeressBar.pb_down = (ProgressBar)findViewById(R.id.pb_downloading);
				holder.soft_touxiang= (ImageView)convertView.findViewById(R.id.soft_image);
				holder.soft_name = (TextView)convertView.findViewById(R.id.soft_name);
				holder.soft_jianjie = (TextView)convertView.findViewById(R.id.soft_jianjie);
				holder.soft_daxiao = (TextView)convertView.findViewById(R.id.soft_daxiao);
				holder.soft_download_count = (TextView)convertView.findViewById(R.id.soft_download_count);
				holder.rl_downlowad = (Button)convertView.findViewById(R.id.img_download);
				holder.Ll_download = (LinearLayout)convertView.findViewById(R.id.Ll_who);

				convertView.setTag(holder);


			}
			holder = (ViewHolder) convertView.getTag();
			final ImageView iamgeview = holder.soft_touxiang;
			Set set=array.get(position).keySet();
			String url = null;
			Iterator iterator=set.iterator();
			String key = null;
			if(iterator.hasNext()){
				key=(String)iterator.next();
			}
			if(key.equals("Software")){
				holder.soft_name.setText(((Software)array.get(position).get(key)).getsoft_name());
				holder.soft_jianjie.setText(((Software)array.get(position).get(key)).getintroduce());
				holder.soft_daxiao.setText(((Software)array.get(position).get(key)).get_size()+"M");
				holder.soft_download_count.setText(((Software)array.get(position).get(key)).get_download_count()+"次");
				url = Constant.urlpath+"Software/"+
						((Software)array.get(position).get(key)).getsoft_id()+"/touxiang.jpg";

			}


			else{
				holder.soft_name.setText(((game)array.get(position).get(key)).getgame_name());
				holder.soft_jianjie.setText(((game)array.get(position).get(key)).getintroduce());
				holder.soft_daxiao.setText(((game)array.get(position).get(key)).getsize()+"M");
				holder.soft_download_count.setText(((game)array.get(position).get(key)).getdownload_count()+"次");
				url = Constant.urlpath+"game/"+
						((game)array.get(position).get(key)).getid()+"/touxiang.jpg";
			}
			iamgeview.setTag(url);

			Drawable drawable = asyncLoadImage.loadDrawable(url, new AsyncLoadImage.ImageCallback(){
				@Override
				public void imageLoad(Drawable image,String imageUrl) {
					if(imageUrl.equals(iamgeview.getTag())){
						iamgeview.setImageDrawable(image);
					}
				}
			});
			if(drawable==null){
				iamgeview.setImageResource(R.drawable.ic_launcher);
			}else{
				iamgeview.setImageDrawable(drawable);
			}

			rl_download_click click_shijian = new rl_download_click(holder,key,array.get(position).get(key));
			holder.Ll_download.setOnClickListener(click_shijian);
			holder.rl_downlowad.setOnClickListener(click_shijian);


			return convertView;
		}

		class rl_download_click implements OnClickListener{

			int position = 0 ;
			String key ;
			ViewHolder holder;
			Object obj ;
			public rl_download_click(ViewHolder holder,String key,Object obj){
				this.key = key ;
				this.obj = obj;
				this.position = position ;
				this.holder = holder;
			}

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v == holder.Ll_download){
					Intent intent = new Intent(MainActivity.this,Detail_info.class);
					if(key.equals("Software")){


						intent.putExtra("key",key);
						Bundle bundle = new Bundle();
						bundle.putSerializable("obj", (Serializable)obj);
						intent.putExtras(bundle);


						intent.putExtra("soft_name",(((Software) obj).getsoft_name()));
						intent.putExtra("introduce",(((Software) obj).getintroduce()));
						intent.putExtra("size",(((Software) obj).get_size())+"");
						intent.putExtra("download_count",(((Software) obj).get_download_count())+"");
						intent.putExtra("version", (((Software) obj).getsoft_version()));
						intent.putExtra("id", (((Software) obj).getsoft_id())+"");
						intent.putExtra("update_time", (((Software) obj).getupdate_time()));
						intent.putExtra("dev_name", (((Software) obj).getdev_name()));
						intent.putExtra("dev_id", (((Software) obj).getdev_id()));
						intent.putExtra("language", (((Software) obj).getsoft_language()));
						intent.putExtra("table_name", "estimate_software");

					}
					else{
						intent.putExtra("soft_name",(((game) obj).getgame_name()));
						intent.putExtra("introduce",(((game) obj).getintroduce()));
						intent.putExtra("size",(((game) obj).getsize())+"");
						intent.putExtra("download_count",(((game) obj).getdownload_count())+"");
						intent.putExtra("version", (((game) obj).getgame_version()));
						intent.putExtra("id", (((game) obj).getid())+"");
						intent.putExtra("update_time", (((game) obj).getupdate_time()));
						intent.putExtra("dev_name", (((game) obj).getdev_name()));
						intent.putExtra("dev_id", (((game) obj).getdev_id()));
						intent.putExtra("language", (((game) obj).getgame_language()));
						intent.putExtra("table_name", "estimate_game");

					}
					startActivity(intent);
				}
				else if(v == holder.rl_downlowad){

					//��Ӷ��еȴ�����
					if(key.equals("Software")){

						// 首页添加下载进度的提示 2016.7.1
						Toast.makeText(getApplicationContext(), "开始下载", Toast.LENGTH_SHORT).show();

						DownLoading db = new DownLoading();
						double size=db.tmp;
						Log.d("111",size+"");


						view_progeressBar.pb_down.setProgress((int) size);

						download_file_queue.Add_item(
								new download_file((((Software) obj).getsoft_id()),(((Software) obj).getsoft_name())
										,getApplicationContext(),"software"));
					}
				}
			}
		}
		class viewHolder_download {
			ProgressBar pb_down;
		}
		class ViewHolder{
			public ImageView soft_touxiang;
			public TextView soft_name,soft_jianjie,soft_daxiao,soft_download_count;
			public Button rl_downlowad;
			public LinearLayout Ll_download;

		}

	}
	class MyPagerAdapter extends PagerAdapter {
		ArrayList<ImageView> viewList;
		public MyPagerAdapter(ArrayList<ImageView> viewList){
			this.viewList=viewList;
		}
		@Override
		public int getCount() {
			return viewList.size();
		}
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(viewList.get(position));
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(viewList.get(position));
			return viewList.get(position);   //���ص�ǰҪ��ʾ��view
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;  //������д����ʾ���� view
		}

	}
	class xhcrunable implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			scrool_all.smoothScrollTo(0, 0);
		}

	}
	class load extends Thread{

		Handler handler ;
		public load(Handler handler){
			this.handler = handler ;
		}
		@Override
		public void run(){
			download_file_queue.Start_Listen(MainActivity.this);
			array = JSON.get_paihang();
			Drawable[] drawble = load_jiemian.get_viewpage();
			ImageView img =new ImageView(getApplicationContext());
			img.setScaleType(ImageView.ScaleType.FIT_XY);//7.21使图片自适应屏幕宽高
			img.setImageDrawable(drawble[0]);
			ImageView img1 =new ImageView(getApplicationContext());
			img1.setImageDrawable(drawble[1]);
			img1.setScaleType(ImageView.ScaleType.FIT_XY);//7.21使图片自适应屏幕宽高

			ImageView img2 =new ImageView(getApplicationContext());
			img2.setImageDrawable(drawble[2]);
			img2.setScaleType(ImageView.ScaleType.FIT_XY);//7.21使图片自适应屏幕宽高

			ImageView img3 =new ImageView(getApplicationContext());
			img3.setImageDrawable(drawble[3]);
			img3.setScaleType(ImageView.ScaleType.FIT_XY);//7.21使图片自适应屏幕宽高

			img_page.add(img);
			img_page.add(img1);
			img_page.add(img2);
			img_page.add(img3);
			Message msg = new Message();
			msg.arg1 = 1;
			handler.sendMessage(msg);
		}

	}

	class viewpage extends Thread{
		boolean flag = true;
		Handler handler;
		public viewpage (Handler handler){
			this.handler = handler;
		}
		@Override
		public void run(){
			int i = 0 ;
			while(flag ){
				i++;
				if(i > 4){
					i = 0 ;
				}


				Message msg = Message.obtain();
				msg.arg1 = i ;
				handler.sendMessage(msg);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public void stop_(){
			this.flag = false;
		}
	}

}
