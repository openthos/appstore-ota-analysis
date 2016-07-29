package Download_File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sql.Sql_Lite;

import android.content.Context;


/**
 * 当用户点击很多文件进行下载的时候就进入队列进行等待
 * 队列中的对象是download——file对象当对其进行实例化并且传入文件名的时候就可以进行下载
 * 当一个正在下载的程序被暂停了。。让后面的程序补上
 * 因为对全部文件进行一个下载监听作用 应该写成静态类。。
 * 当应用商店刚开始启动的时候或者进入软件下载activity的时候先从数据库中获取有哪些没有下载完毕的软件显示出来
 * @author Administrator
 *
 */
public class download_file_queue {

	private static  Context context_ ;
	private static Sql_Lite sql_lite ;
	private static ArrayList<download_file_queue_item> downloadfile_queue = new ArrayList<download_file_queue_item>();

	public static void Start_Listen(Context context){
		context_ = context;
		sql_lite = new Sql_Lite(context);
		ArrayList<String[]> db_record = sql_lite.select_all();
		for(int i = 0 ;i < db_record.size() ; i++){
			if(Integer.parseInt(db_record.get(i)[7]) != State.done_download){
				download_file_queue_item temp_ = new download_file_queue_item(
						new download_file(
								Integer.parseInt(db_record.get(i)[0]),
								db_record.get(i)[1], context));
				temp_.set_state(State.wait_for_download);
				downloadfile_queue.add(temp_);
			}

		}
//    	  new Listen_all_file_down().start();

	}

	public static void Add_item(int file_id ,String file_name){
//		  downloadfile_queue.add(new download_file_queue_item(new download_file(file_id,file_name,context)));
	}
	public static ArrayList<download_file_queue_item> get_ArrayList(){
		return downloadfile_queue;
	}
	static class Listen_all_file_down extends Thread{
		private boolean flag = true;
		private int array_count = 0;
		@Override
		public void run(){

			while(flag){
				downloadfile_queue.get(array_count);
			}
		}

		public void stop_(){
			flag = false;
		}
	}
}





















