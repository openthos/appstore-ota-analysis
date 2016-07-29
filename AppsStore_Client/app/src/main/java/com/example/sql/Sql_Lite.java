package com.example.sql;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.android_app_stroe.Main_face;
import com.example.android_app_stroe.Update;

public class Sql_Lite extends SQLiteOpenHelper{

	public boolean close=true;

	private final static String DATABASE_NAME = "DOWNLOAD_SOFTWARE";
	private final static int DATABASE_VERSION = 4;
	private final static String TABLE_NAME = "software_download";
	private final static String TABLE1_NAME = "game_download";
	private final static String TABLE2_NAME = "amusement_download";
	private final static String SOFT_ID = "SOFT_ID" ;
	private final static String SOFT_NAME = "SOFT_NAME";
	private final static String SOFT_SIZE = "SOFT_SIZE" ;
	private final static String SOFT_THREAD_SUM = "THREAD_SUM";
	private final static String THREAD1_COUNT = "THREAD1_COUNT" ;
	private final static String THREAD2_COUNT = "THREAD2_COUNT" ;
	private final static String THREAD3_COUNT = "THREAD3_COUNT" ;
	private final static String GET_DONE = "DONE";//判断文件是不是完成了。
	public  final Context context ;


	public Sql_Lite(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context ;

		/*11-15 10:05:46.780: E/AndroidRuntime(19832):
		 * java.lang.RuntimeException: Unable to start activity ComponentInfo{com.example.android_app_stroe/com.example.android_app_stroe.DownLoading}: android.database.StaleDataException: Attempting to access a closed CursorWindow.Most probable cause: cursor is deactivated prior to calling this method.
*/
	}



	/**
	 * soft_id   软件唯一码id
	 * soft_name  软件名字
	 * soft_size   软件大小
	 * soft_thread_sum   下载该软件需要多少线程数
	 * thread1_count     线程1下载了多少
	 * thread2_count     线程2下载了多少
	 * thread3_count     线程3下载了多少
	 * get_done          该软件是否下载完毕 1（下载完毕）  0没有
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String create_table = "create table "+TABLE_NAME+"("+SOFT_ID+" integer primary key ,"
				+SOFT_NAME+" text,"
				+SOFT_SIZE+" long,"
				+SOFT_THREAD_SUM+" int,"
				+THREAD1_COUNT+" long,"
				+THREAD2_COUNT+" long,"
				+THREAD3_COUNT+" long,"
				+GET_DONE +"  int )";
		String create_table1 =
				"create table "+TABLE1_NAME+"("+SOFT_ID+" integer primary key ,"
						+SOFT_NAME+" text,"
						+SOFT_SIZE+" long,"
						+SOFT_THREAD_SUM+" int,"
						+THREAD1_COUNT+" long,"
						+THREAD2_COUNT+" long,"
						+THREAD3_COUNT+" long,"
						+GET_DONE +"  int )";
		String create_table2 =
				"create table "+TABLE2_NAME+"("+SOFT_ID+" integer primary key ,"
						+SOFT_NAME+" text,"
						+"file_classify "+" text,"//娱乐表中的文件类型
						+SOFT_SIZE+" long,"
						+SOFT_THREAD_SUM+" int,"
						+THREAD1_COUNT+" long,"
						+THREAD2_COUNT+" long,"
						+THREAD3_COUNT+" long,"
						+GET_DONE +"  int )"

				;
		System.out.println("create");
		db.execSQL(create_table);
		db.execSQL(create_table1);
		db.execSQL(create_table2);
	}

	@Override
	public void onUpgrade( SQLiteDatabase db,int oldVersion, int newVersion) {
		// TODO Auto-generated method stub


	}

	/**
	 *
	 * @param
	 * @param soft_id
	 * @param soft_name
	 * @param soft_size
	 * @param thread_count
	 * @param thread1_count
	 * @param thread2_count
	 * @param thread3_count
	 * @return
	 * �տ�ʼ���ص�ʱ��Ͳ���������¼ �����ظ���¼Ҫ���쳣
	 * file_classify �д��� software game jpg��
	 */
	public void insert_value(int soft_id,String soft_name,long soft_size,int thread_count,
							 long thread1_count,long thread2_count,long thread3_count,int done_flag,String file_classify){
		String sql = null;
		SQLiteDatabase db = this.getWritableDatabase();
		if(file_classify.equals("software")){
			sql = "insert into "+TABLE_NAME+" VALUES ( "+soft_id+","+"'"+soft_name+"'"+","+soft_size+","+thread_count+","
					+thread1_count+","+thread2_count+","+thread3_count+","+done_flag+")";
		}
		else if(file_classify.equals("game")){
			sql = "insert into "+TABLE1_NAME+" VALUES ( "+soft_id+","+"'"+soft_name+"'"+","+soft_size+","+thread_count+","
					+thread1_count+","+thread2_count+","+thread3_count+","+done_flag+")";
		}
		else{
			sql = "insert into "+TABLE2_NAME+" VALUES ( "+soft_id+","+"'"+soft_name+"'"+",'"+file_classify+"',"+soft_size+","+thread_count+","
					+thread1_count+","+thread2_count+","+thread3_count+","+done_flag+")";
		}




		db.execSQL(sql);
		db.close();
	}

	public void delete(int soft_id ,String file_classify){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = null;
		if(file_classify.equals("software")){
			sql = "delete from "+TABLE_NAME+" where soft_id="+soft_id;
		}
		else if(file_classify.equals("game")){
			sql = "delete from "+TABLE1_NAME+" where soft_id="+soft_id;
		}
		else{
			sql = "delete from "+TABLE2_NAME+" where soft_id="+soft_id;
		}
		db.execSQL(sql);
		db.close();
	}
	/**
	 * 当从用户暂停的时候调用
	 * @param
	 * @param soft_id
	 * @param thread1_count
	 * @param thread2_count
	 * @param thread3_count
	 */
	public void update(int soft_id,
					   long thread1_count,long thread2_count,long thread3_count, String file_classify){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = null;
		if(file_classify.equals("software")){
			sql = "update "+TABLE_NAME+" set thread1_count ="+thread1_count+","+"thread2_count ="+thread2_count+","+
					"thread3_count ="+thread3_count +" where soft_id = "+soft_id ;
		}
		else if(file_classify.equals("game")){
			sql = "update "+TABLE1_NAME+" set thread1_count ="+thread1_count+","+"thread2_count ="+thread2_count+","+
					"thread3_count ="+thread3_count +" where soft_id = "+soft_id ;
		}
		else{
			sql = "update "+TABLE2_NAME+" set thread1_count ="+thread1_count+","+"thread2_count ="+thread2_count+","+
					"thread3_count ="+thread3_count +" where soft_id = "+soft_id ;
		}

		db.execSQL(sql);

		db.close();
	}
	/**
	 * ��ѯ����   ���������ͣ�����غ�����ݿ��в�ѯ�����˶��� Ȼ��ʼ���Ǹ��ط�����
	 */
	public String[] select(int soft_id,String file_classify){
		String[] result ;
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = null;
		if(file_classify.equals("software")){
			sql = "select * from " + TABLE_NAME + " where soft_id = "
					+ soft_id;
		}
		else if(file_classify.equals("game")){
			sql = "select * from " + TABLE1_NAME + " where soft_id = "
					+ soft_id;
		}
		else {
			sql = "select * from " + TABLE2_NAME + " where soft_id = "
					+ soft_id;
		}



		Cursor cursor = db.rawQuery(sql, null);
		if(file_classify.equals("software")||file_classify.equals("game")){
			result =  new String[8];
			if (cursor.moveToFirst()) {
				result[0] = cursor.getString(0);
				result[1] = cursor.getString(1);
				result[2] = cursor.getString(2);
				result[3] = cursor.getString(3);
				result[4] = cursor.getString(4);
				result[5] = cursor.getString(5);
				result[6] = cursor.getString(6);
				result[7] = cursor.getString(7);
				db.close();
				cursor.close();
				return result;
			}
		}
		else {
			result =  new String[9];
			if (cursor.moveToFirst()) {
				result[0] = cursor.getString(0);
				result[1] = cursor.getString(1);
				result[2] = cursor.getString(2);
				result[3] = cursor.getString(3);
				result[4] = cursor.getString(4);
				result[5] = cursor.getString(5);
				result[6] = cursor.getString(6);
				result[7] = cursor.getString(7);
				result[8] = cursor.getString(8);
				db.close();
				cursor.close();
				return result;
			}
		}
		cursor.close();
		db.close();
		return null;
	}


	public boolean get_record(int soft_id,String file_classify) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = null;
		if(file_classify.equals("software")){
			sql = "select * from " + TABLE_NAME + " where soft_id = "+ soft_id;
		}
		else if(file_classify.equals("game")){
			sql = "select * from " + TABLE1_NAME + " where soft_id = "+ soft_id;
		}
		else {
			sql = "select * from " + TABLE2_NAME + " where soft_id = "+ soft_id;
		}


		Cursor cursor = db.rawQuery(sql, null);

		if (cursor.moveToFirst()){
			cursor.close();
			db.close();
			return true;
		}
		cursor.close();
		db.close();
		return false;
	}

	public void get_done(int soft_id,String file_classify) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = null;
		if (file_classify.equals("software")) {
			sql = "update " + TABLE_NAME + " set DONE=" + 1 + " where soft_id = " + soft_id;
		} else if (file_classify.equals("game")) {
			sql = "update " + TABLE1_NAME + " set DONE=" + 1 + " where soft_id = " + soft_id;
		} else {
			sql = "update " + TABLE2_NAME + " set DONE=" + 1 + " where soft_id = " + soft_id;
		}

		db.execSQL(sql);
		db.close();
		Looper.prepare();
		if(soft_id ==100){
			close =true;
			Main_face.handler.sendEmptyMessage(0);
		}

		Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
		Log.d("3333", soft_id + " " + close);






		Looper.loop();

	}
	//String中的第一个元素装文件类型
	public ArrayList<String[]> get_done_record(){
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<String[]> array= new ArrayList<String[]>();
		String sql1 = null,sql2,sql3;
		sql1 = "select SOFT_ID,SOFT_NAME,SOFT_SIZE from software_download where DONE = 1";
		sql2 = "select SOFT_ID,SOFT_NAME,SOFT_SIZE from game_download where DONE = 1";
		sql3 = "select SOFT_ID,SOFT_NAME,SOFT_SIZE,file_classify from amusement_download where DONE = 1" ;
		Cursor[] cursor = new Cursor[3];
		cursor[0] = db.rawQuery(sql1, null);
		cursor[1] = db.rawQuery(sql2, null);
		cursor[2] = db.rawQuery(sql3, null);
		for(int i = 0 ; i < 3 ; i++){




			if(cursor[i].moveToFirst()){
				do{
					String[] temp = new String[4];
					temp[0] = ".apk";
					if(i == 2){
						temp[0] = cursor[i].getString(3);
					}


					temp[1] = cursor[i].getString(0);
					temp[2] = cursor[i].getString(1);
					temp[3] = cursor[i].getString(2);
					array.add(temp);

				}while(cursor[i].moveToNext());
			}

		}


		cursor[0].close();
		cursor[1].close();
		cursor[2].close();
		db.close();
		return array;
	}
	/*public ArrayList<String[]>  select_all(String file_classify){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = null;
		if(file_classify.equals("software")){
    		sql = "select * from " + TABLE_NAME;
    	}
    	else if(file_classify.equals("game")){
    		sql = "select * from " + TABLE1_NAME;
    	}
    	else {
    		sql = "select * from " + TABLE2_NAME;
    	}




		ArrayList<String[]> download_collection= new ArrayList<String[]>();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst()){
			do{
				String[] get_hang = new String[8];


				get_hang[0] = cursor.getString(0);
				get_hang[1] = cursor.getString(1);
				get_hang[2] = cursor.getString(2);
				get_hang[3] = cursor.getString(3);
				get_hang[4] = cursor.getString(4);
				get_hang[5] = cursor.getString(5);
				get_hang[6] = cursor.getString(6);
				get_hang[7] = cursor.getString(7);
				download_collection.add(get_hang);

			}while(cursor.moveToNext());
		}
		db.close();
		cursor.close();
		return download_collection;
	}
	*/
	//�����ݿ��в��ҵȴ����ص������Ϣ
	public ArrayList<String[]>  select_all_waitfordownload(String file_classify){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = null;
		if (file_classify.equals("software")) {
			sql = "select * from " + TABLE_NAME +" where DONE = 0";
		} else if (file_classify.equals("game")) {
			sql = "select * from " + TABLE1_NAME +" where DONE = 0";
		} else  {
			sql = "select * from " + TABLE2_NAME +" where DONE = 0";
		}


		ArrayList<String[]> download_collection= new ArrayList<String[]>();
		Cursor cursor = db.rawQuery(sql, null);
		if(file_classify.equals("software")||file_classify.equals("game")){
			if(cursor.moveToFirst()){
				do{
					String[] get_hang = new String[8];


					get_hang[0] = cursor.getString(0);
					get_hang[1] = cursor.getString(1);
					get_hang[2] = cursor.getString(2);
					get_hang[3] = cursor.getString(3);
					get_hang[4] = cursor.getString(4);
					get_hang[5] = cursor.getString(5);
					get_hang[6] = cursor.getString(6);
					get_hang[7] = cursor.getString(7);
					download_collection.add(get_hang);

				}while(cursor.moveToNext());
			}
		}
		else{
			if(cursor.moveToFirst()){
				do{
					String[] get_hang = new String[9];


					get_hang[0] = cursor.getString(0);
					get_hang[1] = cursor.getString(1);
					get_hang[2] = cursor.getString(2);
					get_hang[3] = cursor.getString(3);
					get_hang[4] = cursor.getString(4);
					get_hang[5] = cursor.getString(5);
					get_hang[6] = cursor.getString(6);
					get_hang[7] = cursor.getString(7);
					get_hang[8] = cursor.getString(8);
					download_collection.add(get_hang);

				}while(cursor.moveToNext());
			}
		}

		db.close();
		cursor.close();
		return download_collection;
	}




	public long get_downloaded_record(int soft_id,String file_classify){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = null;
		if(file_classify.equals("software")){
			sql = "select "+THREAD1_COUNT+","+THREAD2_COUNT+","+THREAD3_COUNT+"  from " + TABLE_NAME+
					" where SOFT_ID ="+soft_id;
		}
		else if(file_classify.equals("game")){
			sql = "select "+THREAD1_COUNT+","+THREAD2_COUNT+","+THREAD3_COUNT+"  from " + TABLE1_NAME+
					" where SOFT_ID ="+soft_id;
		}
		else {
			sql = "select "+THREAD1_COUNT+","+THREAD2_COUNT+","+THREAD3_COUNT+"  from " + TABLE2_NAME+
					" where SOFT_ID ="+soft_id;
		}

		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst()){
			db.close();
			long sum = cursor.getInt(0)+cursor.getInt(1)+cursor.getInt(2);
			cursor.close();
			return sum;
		}
		db.close();
		cursor.close();
		return -1;
	}

	public void delete_all(String file_classify){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = null;
		if(file_classify.equals("software")){
			sql = "delete  from  "+TABLE_NAME;
		}
		else if(file_classify.equals("game")){
			sql = "delete  from  "+TABLE1_NAME;
		}
		else {
			sql = "delete  from  "+TABLE2_NAME;
		}
		db.execSQL(sql);
		db.close();
	}
	public void delete_all_database( ){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = null,sql1,sql2;
		sql = "delete  from  "+TABLE_NAME;
		sql1 = "delete  from  "+TABLE1_NAME;
		sql2 = "delete  from  "+TABLE2_NAME;
		db.execSQL(sql);
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.close();
	}
}































