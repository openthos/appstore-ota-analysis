package com.example.Download_File;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.Tool.Constant;
import com.example.Tool.Http_;
import com.example.Tool.HttpsTest;
import com.example.Tool.State;
import com.example.sql.Sql_Lite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class download_file {

	private int file_id ;
	private String file_name ;
	private int done_thread = 0 ;
	private int download_thread_count ;
	private int thread_who = 0 ;
	private long file_size = 0 ;
	private int flag = State.pause_download;
	private download_Thread[] download_thread;
	private Context context ;
	private Sql_Lite sql_lite ;
	private long file_pause_size = 0 ;
	String file_classify ;
	private long record_thread_down_size[];


	public download_file(int file_id, String file_name,Context context,String file_classify ){
		this.file_id = file_id ;
		this.file_name = file_name;
		this.file_classify = file_classify;

		this.context = context ;
		sql_lite = new Sql_Lite(context);
		file_size = getFileSize(file_id);
		record_thread_down_size = new long[3];

		for(int i = 0 ; i < record_thread_down_size.length ; i++){
			record_thread_down_size[i] = 0 ;
		}
		if(file_size < 1024*1024 && file_size > 0){
			this.download_thread_count = 3;
		}
		else if(file_size < 5*1024*1024){
			this.download_thread_count = 3;
		}
		else if(file_size >= 5*1024*1024){
			this.download_thread_count = 3;
		}

		else {

			throw new RuntimeException("can't get file_size");

		}
		download_thread =  new download_Thread[download_thread_count];


	}

	public String get_filename(){

		return file_name;
	}
	public int get_fileid(){
		return file_id;
	}
	public int get_flag(){
		return flag;
	}
	public void set_flag(int flag){
		this.flag = flag ;
	}
	public String get_classify (){
		return file_classify ;
	}

	public void download_start(){
		done_thread = 0 ;
		String[] record_db = new  String[8];
		record_db=sql_lite.select(file_id,file_classify);
		boolean countFlag=false;
		if(record_db == null){
			sql_lite.insert_value(file_id, file_name, file_size,download_thread_count, 0, 0, 0,0,file_classify);
			for(int i = 0 ; i < download_thread_count ; i++){

				download_thread[i] = new download_Thread(download_thread_count,false);
				download_thread[i].start();
			}
			countFlag=true;
		}
		else{
			for(int i = 0 ; i < download_thread_count ; i++){
				download_thread[i] = new download_Thread(download_thread_count,true,Integer.parseInt(record_db[4])
						,Integer.parseInt(record_db[5]),Integer.parseInt(record_db[6]));
				download_thread[i].start();
			}
			countFlag=true;
		}

		if (countFlag){
			String urlc=Constant.Zurl+"/count?id="+file_id+"&classify="+file_classify;
			try {
				//URL url = new URL(urlc);
				HttpsURLConnection conn = (HttpsURLConnection) HttpsTest.getHttpsURLConnection(urlc);
				conn.setRequestMethod("GET");
				conn.getInputStream();
				countFlag=false;
			}
			catch (Exception e){

			}
		}


	}



	public void download_stop(){
		if(flag == 2 ){

			sql_lite.get_done(file_id,file_classify);
			flag = State.done_download;
			thread_who = 0 ;
		}
		else{

			int temp_counts[] = new int[download_thread_count];
			for(int i = 0 ; i < download_thread_count ; i++){
				temp_counts[i] = (int) download_thread[i].get_thread_downloaded_size();
				download_thread[i] = null;
			}

			switch(download_thread_count){

				case 1:
					sql_lite.update(file_id, temp_counts[0], 0, 0,file_classify);
					break;
				case 2:
					sql_lite.update(file_id, temp_counts[0], temp_counts[1], 0,file_classify);
					break;
				case 3:
					sql_lite.update(file_id, temp_counts[0], temp_counts[1], temp_counts[2],file_classify);
					break;
			}
			thread_who = 0 ;
		}

	}

	private class download_Thread extends Thread{
		private long start_position = 0;
		private long end_position = 0 ;
		private int surplus_bytes = 0  ;
		private long common_size = 0 ;
		private int I_m_who ;
		private int H_m_thread ;
		private boolean database_flag = false;

		public download_Thread(int H_m_thread,boolean database_flag ){
			I_m_who = thread_who ;
			this.H_m_thread = H_m_thread;
			thread_who++;
			this.database_flag = database_flag;
		}
		public download_Thread(int H_m_thread,boolean database_flag,
							   int record_thread1_down_size_ ,int record_thread2_down_size_,int record_thread3_down_size_){
			I_m_who = thread_who ;
			this.H_m_thread = H_m_thread;
			thread_who++;
			this.database_flag = database_flag;
			record_thread_down_size[0] = record_thread1_down_size_;
			record_thread_down_size[1] = record_thread2_down_size_;
			record_thread_down_size[2] = record_thread3_down_size_;
		}

		@Override
		public void run(){
			String urlpath = null;
			if(file_classify.equals("game")){

				urlpath ="https://dev.openthos.org/appstore/AppStoreServer/download?path=Game/"+file_id+"/"+file_id+".apk";
			}
			else if(file_classify.equals("software")){

				urlpath ="https://dev.openthos.org/appstore/AppStoreServer/download?path=Software/"+file_id+"/"+file_id+".apk";
			}
			System.out.println(urlpath);
			URL url;
			try {
				//url = new URL(urlpath);
				//HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				HttpsURLConnection conn = (HttpsURLConnection) HttpsTest.getHttpsURLConnection(urlpath);
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(6*1000);
				conn.setRequestProperty("Accept-Language", "zh-CN");
				conn.setRequestProperty("Referer", urlpath);
				conn.setRequestProperty("Charset", "UTF-8");
				common_size = file_size /  H_m_thread ;
				surplus_bytes = (int) (file_size % H_m_thread) ;
				if(I_m_who == 0){
					if(database_flag ){

						conn.setRequestProperty("Range","bytes="+(record_thread_down_size[0])+"-"+(common_size+surplus_bytes-1));
						System.out.println("有记录中线程 i_m_who="+I_m_who+"   "+(record_thread_down_size[0]));
//						start_position = record_thread_down_size[0];
						start_position = 0 ;
					}
					else{
						System.out.println("没记录线程 i_m_who="+I_m_who+"    "+0);
						conn.setRequestProperty("Range","bytes=0-"+(common_size+surplus_bytes-1));
						start_position = 0;
					}

				}
				else if(I_m_who == 1){

					if(database_flag){
//						start_position = ((common_size+surplus_bytes-1)+record_thread_down_size[1]);
						start_position = (common_size+surplus_bytes-1);
						conn.setRequestProperty("Range","bytes="+ ((common_size+surplus_bytes-1)+record_thread_down_size[1])+"-"
								+(common_size*2+surplus_bytes-1));
						System.out.println("有记录中线程 i_m_who="+I_m_who+"   "+((common_size+surplus_bytes-1)+record_thread_down_size[1]));



					}
					else{
						start_position = common_size+surplus_bytes-1;
						conn.setRequestProperty("Range","bytes="+(common_size+surplus_bytes-1)+"-"+(2*common_size+surplus_bytes-1));
						System.out.println("没记录线程 i_m_who="+I_m_who+"    "+(common_size+surplus_bytes-1));
					}

				}
				else if(I_m_who == 2){

					if(database_flag){
						System.out.println("thread_3");
//						start_position = file_size-1-(common_size-record_thread_down_size[2]) ;
						start_position = file_size-common_size-1 ;
						conn.setRequestProperty("Range","bytes="+(file_size-1-(common_size-record_thread_down_size[2]))+"-");
						System.out.println("有记录中线程 i_m_who="+I_m_who+"   "+(file_size-1-(common_size-record_thread_down_size[2])));

					}
					else{
						start_position = file_size-common_size-1 ;
						conn.setRequestProperty("Range","bytes="+(file_size-common_size-1)+"-");
						System.out.println("没记录线程 i_m_who="+I_m_who+"    "+(file_size-common_size-1));
					}

				}
				int temp = -1;
				try{

					temp = Http_.check(conn.getResponseCode());

				}catch(Exception e){
					if(I_m_who == 0){
						Looper.prepare();
						flag = 3;
						Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
						Looper.loop();
					}


				}



				if( temp == Http_.Partial_Content){
					flag = 0;
					InputStream inputStream = conn.getInputStream();
					Save_data(inputStream,start_position);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		private void Save_data(InputStream inputstream , long startposition ) throws IOException{
			int one_omen = 1024*1024;
			ByteArrayOutputStream byteoutputstream = new ByteArrayOutputStream();
			File sdCardDir = null;
			int get_data = 0 ;
			File destDir = null ;

			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				sdCardDir  = Environment.getExternalStorageDirectory();

				if(file_id == 100){
					Log.d("1111", "11111");
					//sdCardDir  = Environment.getRootDirectory();
					destDir  =new File (sdCardDir + "/System_OS/" );
				}else{
					Log.d("2222", "2222");

					destDir = new File(sdCardDir + "/app_store/");
				}

			}
			else{
				Looper.prepare();
				Toast.makeText(context, "下载到手机中", Toast.LENGTH_SHORT).show();
			//	sdCardDir=Environment.getRootDirectory();
			//	destDir  =new File (sdCardDir + "system apk" );
			     Looper.loop();
				return ;
			}

			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			File file;
			if(file_id == 100) {
				file = new File(sdCardDir + "/System_OS/", file_name + ".iso");
			}
			else {
				file = new File(sdCardDir + "/app_store/", file_name + ".apk");
			}
			RandomAccessFile outfile = new RandomAccessFile(file, "rw");
			int temp_count = 0 ;
			byte[] buffer = new byte[300 * 1024];
			while (((get_data = inputstream.read(buffer)) != -1) && flag == 0) {
				byteoutputstream.write(buffer, 0, get_data);
				if(temp_count == 0 )
					outfile.seek(startposition + record_thread_down_size[I_m_who]);
				record_thread_down_size[I_m_who] += get_data;
				outfile.write(byteoutputstream.toByteArray());
				byteoutputstream.flush();
				byteoutputstream.reset();
				temp_count++;//************************
			}
			done_thread++;
			System.out.println("我来看看完成下载的数量 "+done_thread);
			if(done_thread == download_thread_count && flag == 0 ){
				flag = 2;//�������
				download_stop();
			}

			outfile.close();
			byteoutputstream.close();
			inputstream.close();




		}
		public long get_thread_downloaded_size(){
			return record_thread_down_size[I_m_who];
		}

	}

	private long getFileSize(int file_id){
		if(file_size == 0){
			String urlpath=null;
			if (file_classify.equals("game")) {
				urlpath = Constant.urlpath + "Game/" + file_id + "/" + file_id+".apk";
			} else if (file_classify.equals("software")) {
				urlpath = Constant.urlpath + "Software/" + file_id + "/" + file_id+".apk";
			}
			URL url;
			try {
				//url = new URL(urlpath);
				//HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				HttpsURLConnection conn = (HttpsURLConnection) HttpsTest.getHttpsURLConnection(urlpath);
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(6 * 1000);
				conn.setRequestProperty("Accept-Language", "zh-CN");
				conn.setRequestProperty("Referer", urlpath);
				if (conn.getResponseCode() == 200) {
					int temp = conn.getContentLength();
					System.out.println("文件长度" + temp);
					return temp;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;
		}else return file_size;

	}
	public long get_filesize(){
		return file_size ;
	}

	public long get_downloaded_size( ) {
		try{
			long size = 0 ;
			if(flag == State.downloading){
				file_pause_size = 0 ;
				for(int i = 0 ; i < download_thread_count; i++){
					size += download_thread[i].get_thread_downloaded_size() ;
				}
				if(size == file_size) {
					download_stop();
				}
				return size ;
			}
			else{
				if(file_pause_size != 0){
					return file_pause_size;
				}
				else
					file_pause_size = sql_lite.get_downloaded_record(file_id,file_classify);
				return file_pause_size ;
			}
		}catch(NullPointerException e){
			System.out.println("我是空指针哦");
			return 0 ;

		}


	}



}
















