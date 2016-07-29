package com.example.Download_File;

import android.content.Context;

import com.example.Tool.State;
import com.example.sql.Sql_Lite;

import java.util.ArrayList;

/**
 * 当用户点击很多文件进行下载的时候就进入队列进行等待
 * 队列中的对象是download——file对象当对其进行实例化并且传入文件名的时候就可以进行下载
 * 当一个正在下载的程序被暂停了。。让后面的程序补上
 * 因为对全部文件进行一个下载监听作用 应该写成静态类。。
 * 当应用商店刚开始启动的时候或者进入软件下载activity的时候先从数据库中获取有哪些没有下载完毕的软件显示出来
 * @author Administrator
 * 注意静态变量在推出程序后需要全部清除否则出错
 */
public class download_file_queue {

	private static  Context context_ ;
	private static Sql_Lite sql_lite ;
	public  static ArrayList<download_file_queue_item> downloadfile_queue = new ArrayList<download_file_queue_item>();

	public static void Start_Listen(Context context){
		context_ = context;
		sql_lite = new Sql_Lite(context);
		ArrayList<String[]> db_record_soft = sql_lite.select_all_waitfordownload("software");
		ArrayList<String[]> db_record_game = sql_lite.select_all_waitfordownload("game");
		ArrayList<String[]> db_record_amu  = sql_lite.select_all_waitfordownload("");
		for(int i = 0 ;i < db_record_soft.size() ; i++){
			if(Integer.parseInt(db_record_soft.get(i)[7]) != State.done_download){
				download_file_queue_item temp_ = new download_file_queue_item(
						new download_file(
								Integer.parseInt(db_record_soft.get(i)[0]),
								db_record_soft.get(i)[1], context,"software"));
				temp_.set_flag(State.pause_download);
				downloadfile_queue.add(temp_);
			}

		}
		for(int i = 0 ;i < db_record_game.size() ; i++){
			if(Integer.parseInt(db_record_game.get(i)[7]) != State.done_download){
				download_file_queue_item temp_ = new download_file_queue_item(
						new download_file(
								Integer.parseInt(db_record_game.get(i)[0]),
								db_record_game.get(i)[1], context,"game"));
				temp_.set_flag(State.pause_download);
				downloadfile_queue.add(temp_);
			}

		}
		for(int i = 0 ;i < db_record_amu.size() ; i++){
			if(Integer.parseInt(db_record_amu.get(i)[8]) != State.done_download){
				download_file_queue_item temp_ = new download_file_queue_item(
						new download_file(
								Integer.parseInt(db_record_amu.get(i)[0]),
								db_record_amu.get(i)[1], context,db_record_amu.get(i)[2]));
				temp_.set_flag(State.pause_download);
				downloadfile_queue.add(temp_);
			}

		}

		new Listen_all_file_down().start();

	}

	public static void Add_item(int file_id ,String file_name,String classify){
		System.out.println("file_name="+file_name);
		boolean  flag = true;

		for(int i = 0 ; i < downloadfile_queue.size() ;i++){
			if( downloadfile_queue.get(i).get_fileid() == file_id && downloadfile_queue.get(i).get_classify().equals(classify)){
				flag = false;
			}

		}
		if(flag){
			download_file_queue_item temp = new download_file_queue_item(new download_file(file_id,file_name,context_,classify));
			temp.set_flag(State.wait_for_download);
			downloadfile_queue.add(temp);
		}

	}

	public static void Add_item(download_file temp_) {
		boolean flag = true;
		for (int i = 0; i < downloadfile_queue.size(); i++) {

			if (downloadfile_queue.get(i).get_fileid() == temp_.get_fileid()
					&& downloadfile_queue.get(i).get_classify().equals(temp_.get_classify())) {
				flag = false;
			}

		}
		if(flag){
			download_file_queue_item temp = new download_file_queue_item(
					temp_);
			temp.set_flag(State.wait_for_download);
			downloadfile_queue.add(temp);
		}

	}
	public static void delete(int x ){
		downloadfile_queue.remove(x);
	}
	public static void delete(download_file_queue_item item){
		downloadfile_queue.remove(item);
	}
	public static ArrayList<download_file_queue_item> get_ArrayList(){
		return downloadfile_queue;
	}



	static class Listen_all_file_down extends Thread{
		private boolean flag_run = true;
		int downloading = 0 ;//查看正在下载的程序数量
		@Override
		public void run(){

			while(flag_run){
				downloading = 0 ;




				for(int i = 0 ; i < downloadfile_queue.size() ; i++){

					for(int j = 0 ; j < downloadfile_queue.size() ; j++){
						//全部遍历一遍看有几个在下载
						if(downloadfile_queue.get(i).get_flag() == 0){
							downloading++;
						}
					}
					if(downloadfile_queue.get(i).get_flag() == 2){
						//下载完毕
						download_file_queue.delete(i);
						downloading--;
					}
					else if(downloadfile_queue.get(i).get_flag() == -1){
						//等待下载的程序
						if(downloading < 2){
							downloadfile_queue.get(i).set_flag(0);
							downloadfile_queue.get(i).start();//在start函数中会改变标志位
							downloading ++;

							System.out.println("我是从等待中开始下载的程序");

						}
						else{
							continue;
						}
					}
					else if(downloadfile_queue.get(i).get_flag() == 3){
						System.out.println("我是异常中哦。。。。。。");
						delete(i);
					}
					else if(downloadfile_queue.get(i).get_flag() == 2){
						delete(i);
					}
				}

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public void stop_(){
			flag_run = false;
		}
	}
}





















