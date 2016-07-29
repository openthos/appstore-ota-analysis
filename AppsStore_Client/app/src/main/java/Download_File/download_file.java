package Download_File;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import sql.Sql_Lite;

import android.content.Context;
import android.os.Environment;
/**
 * 线程下载   最多三个线程 并且计数重0开始
 * 启动下载器的时候插入本地数据库或则更新
 * 停止的时候看看是不是下载完毕是的话对数据库进行一些操作
 * 需要开启一个线程检测自己是否下载完毕
 * @author Administrator
 * have_done 是否下载完毕的标志
 */
public class download_file {

	private int file_id ;//�ļ�id
	private String file_name ;
	private int download_thread_count ;
	private int thread_who = 0 ;
	private long file_size = 0 ;
	private boolean flag = false;//下载是否进行中
	private download_Thread[] download_thread;
	private Context context ;
	private Sql_Lite sql_lite ;
	private boolean have_done = false;
	public download_file(int file_id, String file_name,Context context ){
		this.file_id = file_id ;
		this.file_name = file_name;
		this.context = context ;
		sql_lite = new Sql_Lite(context);
		file_size = getFileSize(file_id);
		if(file_size < 1024*1024 && file_size > 0){
			this.download_thread_count = 1;
		}
		else if(file_size < 5*1024*1024){
			this.download_thread_count = 2;
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
	/**
	 * �������ص��߳�
	 * ���ݿ���û�м�¼��ô����������¼
	 * �ղ������ݿ���ʱ�����ݶ���ʼ��Ϊ0��Ϊ���ʱ���ļ��Ĵ�С�����ܻ�ú���Ҫ����ͣ���߱���Ե�֪�ļ��Ĵ�С
	 */

	public void download_start(){
		flag = true;
		have_done = false;
		String[] record_db = new  String[8];
		record_db=sql_lite.select(file_id);
		//��ʾ���ݿ����Ƿ��¼
		if(record_db == null){
			System.out.println("没记录的");
			sql_lite.insert_value(file_id, file_name, 0,download_thread_count, 0, 0, 0,0);
			for(int i = 0 ; i < download_thread_count ; i++){
				download_thread[i] = new download_Thread(download_thread_count,false);
				download_thread[i].start();
			}
		}
		else{
			for(int i = 0 ; i < download_thread_count ; i++){
				download_thread[i] = new download_Thread(download_thread_count,true,Integer.parseInt(record_db[4])
						,Integer.parseInt(record_db[4]),Integer.parseInt(record_db[4]));
				download_thread[i].start();
			}
		}

	}



	public void download_stop(){
		if(file_size == getFileSize(file_id) ){
			//�������
			have_done = true;
			sql_lite.get_done(file_id);
		}
		else{
			//ֻ����ͣ����
			int temp_count =  Integer.parseInt(sql_lite.select(file_id)[3]);//�鿴֮ǰ���߳���
			int temp_counts[] = new int[temp_count];
			for(int i = 0 ; i < temp_count ; i++){
				temp_counts[i] = (int) download_thread[i].get_thread_downloaded_size();
			}

			switch(temp_count){

				case 1:
					sql_lite.update(file_id, temp_counts[0], 0, 0);
					break;
				case 2:
					sql_lite.update(file_id, temp_counts[0], temp_counts[1], 0);
					break;
				case 3:
					sql_lite.update(file_id, temp_counts[0], temp_counts[1], temp_counts[2]);
					break;
			}


		}
		flag = false;
	}


	private class download_Thread extends Thread{
		private long start_position = 0;
		private long end_position = 0 ;
		private int surplus_bytes = 0  ;
		private int thread_downloaded_size = 0 ;
		private long common_size = 0 ;
		private int I_m_who ;//�ж��Լ��ǵڼ����߳�
		private int H_m_thread ;//���м����߳����س���
		private int download_section ;
		private boolean database_flag = false;//��־���ݿ����Ƿ��м�¼ true �м�¼
		private long record_thread1_down_size,record_thread2_down_size,record_thread3_down_size;;
		public download_Thread(int H_m_thread,boolean database_flag ){
			I_m_who = thread_who ;
			this.H_m_thread = H_m_thread;
			thread_who++;
			this.database_flag = database_flag;
		}
		public download_Thread(int H_m_thread,boolean database_flag,
							   int record_thread1_down_size ,int record_thread2_down_size,int record_thread3_down_size){
			I_m_who = thread_who ;
			this.H_m_thread = H_m_thread;
			thread_who++;
			this.database_flag = database_flag;
			this.record_thread1_down_size = record_thread1_down_size;
			this.record_thread2_down_size = record_thread2_down_size;
			this.record_thread3_down_size = record_thread3_down_size;
		}

		@Override
		public void run(){
			String urlpath="http://192.168.0.134:8080/shuai/xhc/"+file_id;

			URL url;
			try {
				url = new URL(urlpath);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(6*1000);
				conn.setRequestProperty("Accept-Language", "zh-CN");
				conn.setRequestProperty("Referer", urlpath);
				conn.setRequestProperty("Charset", "UTF-8");
				common_size = file_size /  H_m_thread ;
				surplus_bytes = (int) (file_size % H_m_thread) ;
				if(I_m_who == 0){//��һ���߳�
					if(database_flag ){
						conn.setRequestProperty("Range","bytes="+(record_thread1_down_size-1)+"-"+(common_size+surplus_bytes-1));
						start_position = record_thread1_down_size-1;
						download_section = (int)( (common_size +  surplus_bytes)-record_thread1_down_size);
					}
					else{
						conn.setRequestProperty("Range","bytes=0-"+(common_size+surplus_bytes-1));
						start_position = 0;
						download_section = (int) (common_size +  surplus_bytes);
						System.out.println("第一个线程信息   startposition = "+start_position+"下载长度"+download_section);
					}

				}
				else if(I_m_who == 1){

					if(database_flag){
						download_section = (int) (common_size - record_thread2_down_size);
						start_position = ((common_size+surplus_bytes-1)+record_thread2_down_size);
						conn.setRequestProperty("Range","bytes="
								+((common_size+surplus_bytes-1)+record_thread2_down_size)+"-"
								+(file_size-common_size-1));



					}
					else{
						download_section = (int) (common_size);
						start_position = common_size+surplus_bytes-1;
						conn.setRequestProperty("Range","bytes="+(common_size+surplus_bytes-1)+"-"+(2*common_size+surplus_bytes-1));
						System.out.println("第2个线程信息   startposition = "+start_position+"下载长度"+download_section);
					}

				}
				else{

					if(database_flag){
						download_section = (int) (common_size - record_thread3_down_size);
						start_position = file_size-1-(common_size-record_thread3_down_size) ;
						conn.setRequestProperty("Range","bytes="+(file_size-common_size-record_thread3_down_size-1)+"-");
					}
					else{

						download_section = (int) (common_size);
						start_position = file_size-common_size-1 ;
						conn.setRequestProperty("Range","bytes="+(file_size-common_size-1)+"-");
						System.out.println("第3个线程信息   startposition = "+start_position+"下载长度"+download_section);
					}

				}
				int temp = conn.getResponseCode();
				if( conn.getResponseCode() == 206){
					InputStream inputStream = conn.getInputStream();
					Save_data(inputStream,download_section,start_position);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		//Ψһ���߳̿��ٵ����ռ���1m
		private void Save_data(InputStream inputstream ,long datasize, long startposition ) throws IOException{
			int one_omen = 1024*1024;
			ByteArrayOutputStream byteoutputstream = new ByteArrayOutputStream();

			File sdCardDir = Environment.getExternalStorageDirectory();
			int get_data = 0 ;
			File destDir = new File(sdCardDir + "/app_store/");

			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			File file = new File(sdCardDir+"/app_store/" ,file_name);
			RandomAccessFile outfile = new RandomAccessFile(file, "rw");

			byte[] buffer = new byte[300 * 1024];
			while (((get_data = inputstream.read(buffer)) != -1) && flag) {
				byteoutputstream.write(buffer, 0, get_data);



				outfile.seek(startposition + thread_downloaded_size);
				thread_downloaded_size += get_data;
				System.out.println("我来查看位置 startposition " + startposition
						+"下载了多少"+ thread_downloaded_size);
				outfile.write(byteoutputstream.toByteArray());

				byteoutputstream.flush();
				byteoutputstream.reset();

			}


			outfile.close();
			byteoutputstream.close();
			inputstream.close();




		}
		public long get_thread_downloaded_size(){
			return thread_downloaded_size;
		}

	}

	private long getFileSize(int file_id){
		if(file_size == 0){
			String urlpath="http://192.168.0.134:8080/shuai/xhc/"+file_id;
			URL url;
			try {
				url = new URL(urlpath);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				conn.setRequestMethod("GET");
				conn.setConnectTimeout(6*1000);
				conn.setRequestProperty("Accept-Language", "zh-CN");
				conn.setRequestProperty("Referer", urlpath);
				if( conn.getResponseCode()== 200){
					int temp =conn.getContentLength();
					System.out.println("文件长度"+temp);
					return  temp;
				}
			} catch ( Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1 ;
		}else return file_size;

	}
	public long get_filesize(){
		return file_size ;
	}


	//获取文件下载的长度
	public long get_downloaded_size() {

		long size = 0 ;
		if(flag){
			for(int i = 0 ; i < download_thread_count; i++){
				size += download_thread[i].get_thread_downloaded_size() ;
			}
			if(size == file_size) {
				have_done= true ;
				download_stop();
			}
			return size ;
		}
		else{
			size = sql_lite.get_downloaded_record(file_id);
			return size ;
		}

	}
	public boolean Have_done(){
		return have_done ;
	}


}
















