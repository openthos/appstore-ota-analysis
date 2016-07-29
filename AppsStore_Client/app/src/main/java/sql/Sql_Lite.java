package sql;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sql_Lite extends SQLiteOpenHelper{

	private final static String DATABASE_NAME = "DOWNLOAD_SOFTWARE";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "software_download";
	private final static String SOFT_ID = "SOFT_ID" ;
	private final static String SOFT_NAME = "SOFT_NAME";
	private final static String SOFT_SIZE = "SOFT_SIZE" ;
	private final static String SOFT_THREAD_SUM = "THREAD_SUM";
	private final static String THREAD1_COUNT = "THREAD1_COUNT" ;
	private final static String THREAD2_COUNT = "THREAD2_COUNT" ;
	private final static String THREAD3_COUNT = "THREAD3_COUNT" ;
	private final static String GET_DONE = "DONE";


	public Sql_Lite(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub11-11 20:09:20.451: I/Database(24423): sqlite returned: error code = 1, msg = no such table: software_download



	}





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
		System.out.println("创建数据库");
		db.execSQL(create_table);
	}

	@Override
	public void onUpgrade( SQLiteDatabase db,int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		String sql = "drop table if exists "+ TABLE_NAME;
		System.out.println("我在这里删除数据库了");
		db.execSQL(sql);
		String create_table = "create table "+TABLE_NAME+"("+SOFT_ID+" integer primary key ,"
				+SOFT_NAME+" text,"
				+SOFT_SIZE+" long,"
				+SOFT_THREAD_SUM+" int,"
				+THREAD1_COUNT+" long,"
				+THREAD2_COUNT+" long,"
				+THREAD3_COUNT+" long,"
				+GET_DONE +"  int )";
		db.execSQL(create_table);
	}



	public void insert_value(int soft_id,String soft_name,long soft_size,int thread_count,
							 long thread1_count,long thread2_count,long thread3_count,int done_flag){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "insert into "+TABLE_NAME+" VALUES ( "+soft_id+","+"'"+soft_name+"'"+","+soft_size+","+thread_count+","
				+thread1_count+","+thread2_count+","+thread3_count+","+done_flag+")";


		db.execSQL(sql);
		db.close();
	}



	public void delete(int soft_id){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "delete from "+TABLE_NAME+" where soft_id="+soft_id;
		db.execSQL(sql);
		db.close();
	}



	public void update(int soft_id,
					   long thread1_count,long thread2_count,long thread3_count){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "update "+TABLE_NAME+" set thread1_count ="+thread1_count+","+"thread2_count ="+thread2_count+","+
				"thread3_count ="+thread3_count +" where soft_id = "+soft_id ;
		db.execSQL(sql);
		db.close();
	}



	public String[] select(int soft_id){
		String[] result = new String[8];
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "select * from " + TABLE_NAME + " where soft_id = "
				+ soft_id;

		Cursor cursor = db.rawQuery(sql, null);
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

		return null;
	}


	public boolean get_record(int soft_id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "select * from " + TABLE_NAME + " where soft_id = "+ soft_id;

		Cursor cursor = db.rawQuery(sql, null);

		if (cursor.moveToFirst())
			return true;
		return false;
	}

	public void get_done(int soft_id){
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "update "+TABLE_NAME+" set DONE="+1+" where soft_id="+soft_id;
		db.execSQL(sql);
		db.close();
	}

	public ArrayList<String[]>  select_all(){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "select * from " + TABLE_NAME;

		ArrayList<String[]> download_collection= new ArrayList<String[]>();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst()){
			while(cursor.moveToNext()){
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
			}
		}
		return download_collection;
	}

	public long get_downloaded_record(int soft_id){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "select "+THREAD1_COUNT+","+THREAD2_COUNT+","+THREAD3_COUNT+"  from " + TABLE_NAME+
				" where SOFT_ID ="+soft_id;
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst()){
			System.out.println("here "+cursor.getInt(0)+" "+cursor.getInt(1)+" "+cursor.getInt(2));

			return cursor.getInt(0)+cursor.getInt(1)+cursor.getInt(2);
		}
		return -1;
	}
}































