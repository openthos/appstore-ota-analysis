package com.example.android_app_stroe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Download_File.download_file;
import com.example.Download_File.download_file_queue;
import com.example.JSON.JSON;
import com.example.JSON.Software;
import com.example.JSON.game;
import com.example.Tool.Constant;
import com.example.loadImage.AsyncLoadImage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//import com.example.JSON.amusement;

public class List_Software extends Activity {

	ListView listview_class;
	TextView soft_class,title,no_result;
	private String Title = null,keywords;
	private ArrayList<Software> software ;
	private ArrayList<HashMap<String,Object>> array ;
	//	private ArrayList<com.example.JSON.amusement> array_amusement;
	private ArrayList<game> array_game;
	private ArrayList<HashMap<String,Object>> array_search;
	private int classify ;
	String operate ,dev_id,classify_amusement;

	int classify_game;
	private ImageView search ;
	@Override
	public void onCreate(Bundle o ){
		super.onCreate(o);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //û�б���
		setContentView(R.layout.load);

		title = (TextView)findViewById(R.id.title);
		search= (ImageView)findViewById(R.id.daohang_search);
		search.setOnClickListener(new click_());
		Intent intent = getIntent();
		operate = intent.getStringExtra("operate");

		if(operate.equals("get_software")){
			String getreguest = intent.getStringExtra("request");
			classify = intent.getIntExtra("classify", 0);
			Title = getreguest;
		}
		/*else if(operate.equals("auther_other_work")){
			Title="其他作品";
			dev_id = intent.getStringExtra("dev_id");
		}
		else if(operate.equals("amusement")){
			Title = "娱乐";
			classify_amusement = intent.getStringExtra("classify");
		}*/
		else if(operate.equals("game")){
			Title = "游戏";
			classify_game = intent.getIntExtra("classify",0);
		}
		else if(operate.equals("search")){
			keywords = intent.getStringExtra("keywords");
			Title = "搜索结果";
		}
		title.setText(Title);
		new load(mhandler).start();
	}
	class click_ implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(List_Software.this,Search.class);
			startActivity(intent);
		}

	}
	private Handler mhandler = new Handler(){

		@Override
		public void handleMessage(Message msg ){
			setContentView(R.layout.what_soft_list);
			listview_class = (ListView)findViewById(R.id.list_what_soft);
			TextView tv_class = (TextView)findViewById(R.id.tv_class);
			tv_class.setText(Title);
			search= (ImageView)findViewById(R.id.daohang_search);
			search.setOnClickListener(new click_());
			if(msg.arg1 == 1){



				listview_class.setAdapter(new ListViewAdapter(getApplicationContext()));

			}
			else if(msg.arg1 == 2){
				no_result = (TextView)findViewById(R.id.no_result);
				no_result.setVisibility(View.VISIBLE);
				listview_class.setVisibility(View.GONE);
				System.out.println("进来了没/");
			}
		}
	};





	//�����Ϊ�˸�create��ʱ����ʾ����0.0�ط��������Ǵ��м���ʾ��


	//���listview��adapter
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
			if(operate.equals("get_software")) return  software.size();
				//	else if(operate.equals("amusement")) return array_amusement.size();
			else if(operate.equals("game")) return array_game.size();
			else if(operate.equals("search")){
				int temp ;
				if((temp = array_search.size()) >= 1){
					return temp;
				}

				return 0;
			}
			else if(operate.equals("auther_other_work")) return array.size();
			return 0 ;
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
			String url = null;
			if(convertView == null){

				convertView = mInflater.inflate(R.layout.list_view_child,null);
				holder = new ViewHolder();

				holder.soft_touxiang= (ImageView)convertView.findViewById(R.id.soft_image);
				holder.soft_name = (TextView)convertView.findViewById(R.id.soft_name);
				holder.soft_jianjie = (TextView)convertView.findViewById(R.id.soft_jianjie);
				holder.soft_daxiao = (TextView)convertView.findViewById(R.id.soft_daxiao);
				holder.soft_download_count = (TextView)convertView.findViewById(R.id.soft_download_count);
				holder.rl_downlowad = (Button) convertView.findViewById(R.id.img_download);
				holder.ll_who = (LinearLayout)convertView.findViewById(R.id.Ll_who);
				convertView.setTag(holder);


			}
			holder = (ViewHolder) convertView.getTag();
			String key = null;
			final ImageView iamgeview = holder.soft_touxiang;
			if(operate.equals("get_software")){
				key = "software";
				holder.soft_name.setText(software.get(position).getsoft_name());
				holder.soft_jianjie.setText(software.get(position).getintroduce());
				holder.soft_daxiao.setText(software.get(position).get_size()+"M");
				holder.soft_download_count.setText(software.get(position).get_download_count()+"次");
				click Click = new click(holder,key,software.get(position));
				holder.ll_who.setOnClickListener(Click);
				holder.rl_downlowad.setOnClickListener(Click);
				url = Constant.urlpath+"Software/"+
						software.get(position).getsoft_id()+"/touxiang.jpg";
			}
				/*else if(operate.equals("auther_other_work")){
					Set set=array.get(position).keySet();
					Iterator iterator=set.iterator();
					if(iterator.hasNext()){
						key=(String)iterator.next();
					}
					if(key.equals("auther_software")){
						holder.soft_name.setText(((Software)array.get(position).get(key)).getsoft_name());
						holder.soft_jianjie.setText(((Software)array.get(position).get(key)).getintroduce());
						holder.soft_daxiao.setText(((Software)array.get(position).get(key)).get_size()+"M");
						holder.soft_download_count.setText(((Software)array.get(position).get(key)).get_download_count()+"次");
						url = Constant.urlpath+"Software/"+
								(((Software)array.get(position).get(key)).getsoft_id())+"/touxiang.jpg";
					}
					else if(key.equals("auther_game")){
						holder.soft_name.setText(((game)array.get(position).get(key)).getgame_name());
						holder.soft_jianjie.setText(((game)array.get(position).get(key)).getintroduce());
						holder.soft_daxiao.setText(((game)array.get(position).get(key)).getsize()+"M");
						holder.soft_download_count.setText(((game)array.get(position).get(key)).getdownload_count()+"次");
						url = Constant.urlpath+"Game/"+
								((game)array.get(position).get(key)).getid()+"/touxiang.jpg";
					}
					click Click = new click(holder,key,array.get(position).get(key));
					holder.ll_who.setOnClickListener(Click);
					holder.rl_downlowad.setOnClickListener(Click);
				}*/
				/*else if(operate.equals("amusement")){
					key = "amusement";
					holder.soft_name.setText(array_amusement.get(position).getfile_name());
					holder.soft_jianjie.setText(array_amusement.get(position).getintroduce());
					holder.soft_daxiao.setText(array_amusement.get(position).getsize()+"M");
					holder.soft_download_count.setText(array_amusement.get(position).getdownload_count()+"��");
					click Click = new click(holder,key,array_amusement.get(position));
					holder.ll_who.setOnClickListener(Click);
					holder.rl_downlowad.setOnClickListener(Click);
					url = Constant.urlpath+"Amusement/"+
							array_amusement.get(position).getid()+"/touxiang.jpg";
				}*/
			else if(operate.equals("game")){
				key = "game";
				holder.soft_name.setText(array_game.get(position).getgame_name());
				holder.soft_jianjie.setText(array_game.get(position).getintroduce());
				holder.soft_daxiao.setText(array_game.get(position).getsize()+"M");
				holder.soft_download_count.setText(array_game.get(position).getdownload_count()+"次");
				click Click = new click(holder,key,array_game.get(position));
				holder.ll_who.setOnClickListener(Click);
				holder.rl_downlowad.setOnClickListener(Click);
				url = Constant.urlpath+"Game/"+
						array_game.get(position).getid()+"/touxiang.jpg";
			}
			else if(operate.equals("search")){
				Set set=array_search.get(position).keySet();

				Iterator iterator=set.iterator();
				String key_ = null;
				if(iterator.hasNext()){
					key_=(String)iterator.next();
				}
				if(key_.equals("software")){
					key = "software";
					holder.soft_name.setText(((Software)array_search.get(position).get(key_)).getsoft_name());
					holder.soft_jianjie.setText(((Software)array_search.get(position).get(key_)).getintroduce());
					holder.soft_daxiao.setText(((Software)array_search.get(position).get(key_)).get_size()+"M");
					holder.soft_download_count.setText(((Software)array_search.get(position).get(key_)).get_download_count()+"次");
					click Click = new click(holder,key,((Software)array_search.get(position).get(key_)));
					holder.ll_who.setOnClickListener(Click);
					holder.rl_downlowad.setOnClickListener(Click);
					url = Constant.urlpath+"Software/"+
							((Software)array_search.get(position).get(key_)).getsoft_id()+"/touxiang.jpg";
					System.out.println();
				}
				else if(key_.equals("game")){
					key = "game";
					holder.soft_name.setText(((game)array_search.get(position).get(key_)).getgame_name());
					holder.soft_jianjie.setText(((game)array_search.get(position).get(key_)).getintroduce());
					holder.soft_daxiao.setText(((game)array_search.get(position).get(key_)).getsize()+"M");
					holder.soft_download_count.setText(((game)array_search.get(position).get(key_)).getdownload_count()+"次");
					click Click = new click(holder,key,((game)array_search.get(position).get(key_)));
					holder.ll_who.setOnClickListener(Click);
					holder.rl_downlowad.setOnClickListener(Click);
					url = Constant.urlpath+"Game/"+
							((game)array_search.get(position).get(key_)).getid()+"/touxiang.jpg";
				}
					/*else if(key_.equals("amusement")){
						key = "amusement";
						holder.soft_name.setText(((amusement)array_search.get(position).get(key_)).getfile_name());
						holder.soft_jianjie.setText(((amusement)array_search.get(position).get(key_)).getintroduce());
						holder.soft_daxiao.setText(((amusement)array_search.get(position).get(key_)).getsize()+"M");
						holder.soft_download_count.setText(((amusement)array_search.get(position).get(key_)).getdownload_count()+"��");
						click Click = new click(holder,key,((amusement)array_search.get(position).get(key_)));
						holder.ll_who.setOnClickListener(Click);
						holder.rl_downlowad.setOnClickListener(Click);
						url = Constant.urlpath+"Amusement/"+
								((amusement)array_search.get(position).get(key_)).getid()+"/touxiang.jpg";
					}*/
			}
			iamgeview.setTag(url);

			Drawable drawable = asyncLoadImage.loadDrawable(url, new AsyncLoadImage.ImageCallback(){

				@Override
				public void imageLoad(Drawable image,String imageUrl) {
					if(imageUrl.equals(iamgeview.getTag())){
						System.out.println(imageUrl+"  我是url");

						iamgeview.setImageDrawable(image);
					}
					else {
						System.out.println("adsfadsfadsfasdfadfzheli ?????");
					}
				}
			});
			if(drawable==null){
				iamgeview.setImageResource(R.drawable.baihu);
			}else{
				iamgeview.setImageDrawable(drawable);
			}

			return convertView;
		}
	}


	class click implements OnClickListener{
		ViewHolder holder ;
		String key ;
		Object obj;


		public click(ViewHolder holder,String key,Object obj){
			this.holder = holder;
			this.key = key ;
			this.obj = obj ;
		}


		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(arg0 == holder.rl_downlowad){
				if(key.equals("auther_game")||key.equals("game")){
					download_file_queue.Add_item(
							new download_file((((game) obj).getid()),(((game) obj).getgame_name())
									,getApplicationContext(),"game"));
				}
				else if(key.equals("auther_software")||key.equals("software")){
					download_file_queue.Add_item(
							new download_file((((Software) obj).getsoft_id()),(((Software) obj).getsoft_name())
									,getApplicationContext(),"software"));
				}
			/*	else if(key.equals("amusement")){
					download_file_queue.Add_item(
							new download_file((((amusement) obj).getid()),(((amusement) obj).getfile_name())
									,getApplicationContext(),"."+(((amusement) obj).getfile_classify())));
					System.out.println(((amusement)obj).getfile_classify()+ "s文件类型");
				}*/

			}
			else if(arg0 == holder.ll_who){
				Intent intent = new Intent(List_Software.this,Detail_info.class);
				if(key.equals("auther_software")||key.equals("software")){

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
				else if(key.equals("auther_game")||key.equals("game")){

					intent.putExtra("key",key);
					Bundle bundle = new Bundle();
					bundle.putSerializable("obj", (Serializable)obj);
					intent.putExtras(bundle);


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
			/*	else if(key.equals("amusement")){
					intent.putExtra("soft_name",(((com.example.JSON.amusement) obj).getfile_name()));
					intent.putExtra("introduce",(((com.example.JSON.amusement) obj).getintroduce()));
					intent.putExtra("size",(((com.example.JSON.amusement) obj).getsize())+"");
					intent.putExtra("download_count",(((com.example.JSON.amusement) obj).getdownload_count())+"");
					intent.putExtra("version", "无");
					intent.putExtra("id", (((com.example.JSON.amusement) obj).getid())+"");
					intent.putExtra("update_time", (((com.example.JSON.amusement) obj).getupdate_time()));
					intent.putExtra("dev_name", (((com.example.JSON.amusement) obj).getdev_name()));
					intent.putExtra("dev_id", (((com.example.JSON.amusement) obj).getdev_id()));
					intent.putExtra("language","无");
					intent.putExtra("table_name", "estimate_amusemenet");
				}*/

				startActivity(intent);
			}
		}

	}



	class ViewHolder{
		public ImageView soft_touxiang;
		public TextView soft_name,soft_jianjie,soft_daxiao,soft_download_count;
		public Button rl_downlowad;
		public LinearLayout ll_who ;
	}



	class load extends Thread{

		Handler handler;
		public load(Handler handler){
			this.handler = handler ;
		}
		@Override
		public void run(){
			Message msg = new Message();
			msg.arg1 = 1;
			/* if(operate.equals("auther_other_work")){
				 array = JSON.get_auther_other_work(dev_id);
				 handler.sendMessage(msg);
			 }
			 else */
			if(operate.equals("get_software")){
				software = JSON.get_software(classify, 1);
				handler.sendMessage(msg);
			}
			/* else if(operate.equals("amusement")){
				 array_amusement = JSON.get_amusement(classify_amusement, 1);
				 handler.sendMessage(msg);
			 }*/
			else if(operate.equals("game")){
				array_game = JSON.get_game(1, classify_game);

				handler.sendMessage(msg);
			}

			else if(operate.equals("search")){
				array_search = JSON.get_search(keywords);
				if(array_search.size() == 0){

					msg.arg1 = 2;
//					 handler.sendMessage(msg);
				}
				handler.sendMessage(msg);
			}

		}
	}
}



