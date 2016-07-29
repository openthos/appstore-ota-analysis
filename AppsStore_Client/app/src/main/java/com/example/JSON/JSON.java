package com.example.JSON;

import android.util.Log;

import com.example.Tool.Constant;
import com.example.Tool.HttpsTest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

//import org.apache.commons.logging.Log;

public class JSON {


	public static ArrayList<Software> get_software(int classify , int time){
		ArrayList<Software> array = new ArrayList<Software>();
		String path=Constant.database_path+"software&time="+time+"&classify="+classify;
		System.out.println(path);
		try {
			HttpsURLConnection conn = (HttpsURLConnection) HttpsTest.getHttpsURLConnection(path);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			if(conn.getResponseCode() == 200){
				InputStream json = conn.getInputStream();
				byte data[] = new byte[1024];
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				int length = 0 ;
				while((length = json.read(data))!=-1){
					out.write(data, 0, length);
				}
				String json_out = out.toString("UTF-8");
				System.out.println("sofwaer"+json_out);
				JSONArray jsonArray = new JSONArray(json_out);
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				String operate = jsonObject.getString("operate");
				if(operate.equals("software")){
					for(int i = 1 ; i < jsonArray.length() ; i++) {
						JSONObject jsonObject_ = jsonArray.getJSONObject(i);
						int id = jsonObject_.getInt("id");
						String dev_name = jsonObject_.getString("dev_name");
						String dev_id = jsonObject_.getString("dev_id");
						String soft_name = jsonObject_.getString("soft_name");
						String update_time =jsonObject_.getString("update_time") ;
						String soft_language = jsonObject_.getString("soft_language");
						String soft_version =jsonObject_.getString("soft_version");
						int soft_download_count = jsonObject_.getInt("soft_download_count");
						String introduce =jsonObject_.getString("introduce").toString();
						int soft_size = jsonObject_.getInt("soft_size");
						array.add(new Software(id,soft_name,dev_id,update_time,soft_language,soft_version,
								soft_download_count,introduce,soft_size,dev_name));
					}



				}
				return array;
			}
		}
		catch(Exception e){

		}
		return null;
	}

	public static ArrayList<HashMap<String,Object>> get_paihang(){
		ArrayList<HashMap<String,Object>> array = new ArrayList<HashMap<String,Object>>();
		String path=Constant.database_path+"paihang";

		try{
			HttpsURLConnection conn = (HttpsURLConnection) HttpsTest.getHttpsURLConnection(path);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			if(conn.getResponseCode() == 200){
				InputStream json = conn.getInputStream();
				byte data[] = new byte[1024];
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				int length = 0 ;
				while((length = json.read(data))!=-1){
					out.write(data, 0, length);
				}
				String json_out = out.toString("UTF-8");

				Log.v("System",json_out);

				JSONArray jsonArray = new JSONArray(json_out);
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				String operate = jsonObject.getString("operate");
				if(operate.equals("paihang_software")){
					int count_i = 1;
					try{

						for(  count_i = 1; count_i < jsonArray.length() ; count_i++){
							JSONObject jsonObject_ = jsonArray.getJSONObject(count_i);

							int id = jsonObject_.getInt("id");

							String soft_name = jsonObject_.getString("soft_name");
							String dev_name = jsonObject_.getString("dev_name");
							String dev_id = jsonObject_.getString("dev_id");
							String update_time = jsonObject_.getString("update_time");
							String soft_language = jsonObject_.getString("soft_language");
							String soft_version = jsonObject_.getString("soft_version");
							int download_count = jsonObject_.getInt("soft_download_count");
							String introduce = jsonObject_.getString("introduce");
							int size = jsonObject_.getInt("soft_size");
							 /*int soft_id,String soft_name,String dev_id,String update_time,String soft_language,String soft_version
	    		              ,int download_count,String introduce,int size,String dev_name*/
							HashMap<String,Object> map = new HashMap<String,Object>();
							map.put("Software", new Software(id, soft_name, dev_id, update_time,soft_language, soft_version,
									download_count, introduce, size, dev_name)) ;
							array.add(map);
						}

					}catch(Exception e){

						for(count_i++; count_i<jsonArray.length();count_i++){
							JSONObject jsonObject_ = jsonArray.getJSONObject(count_i);
							int id = jsonObject_.getInt("id");
							String game_name = jsonObject_.getString("game_name");
							String dev_name = jsonObject_.getString("dev_name");
							String dev_id = jsonObject_.getString("dev_id");
							String update_time = jsonObject_.getString("update_time");
							String soft_language = jsonObject_.getString("soft_language");
							String soft_version = jsonObject_.getString("soft_version");
							int download_count = jsonObject_.getInt("soft_download_count");
							String introduce = jsonObject_.getString("introduce");
							int size = jsonObject_.getInt("size");
							HashMap<String,Object> map = new HashMap<String,Object>();
							map.put("game", new game(id,game_name,dev_id,update_time,soft_language,soft_version,download_count
									,introduce,size,dev_name));
							array.add(map);
						}
					}

				}



			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return array;
	}


	public static ArrayList<HashMap<String,Object>> get_search(String keywords){
		ArrayList<HashMap<String,Object>> array = new ArrayList<HashMap<String,Object>>();
		String path=Constant.database_path+"search&keywords="+keywords;
		System.out.println("我来看看路径       " +path);
		try{
			HttpsURLConnection conn = (HttpsURLConnection) HttpsTest.getHttpsURLConnection(path);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			if(conn.getResponseCode() == 200){
				InputStream json = conn.getInputStream();
				byte data[] = new byte[1024];
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				int length = 0 ;
				while((length = json.read(data))!=-1){
					out.write(data, 0, length);
				}
				String json_out = out.toString("UTF-8");
				System.out.println(json_out);
				JSONArray jsonArray = new JSONArray(json_out);
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				String operate = jsonObject.getString("operage");
				int count_i = 0 ;
				try{
					if(operate.equals("search")){
						JSONObject jsonObject_1 = jsonArray.getJSONObject(1);
						if(jsonObject_1.getString("get_operate").equals("software")){

							for(  count_i = 2 ; count_i <jsonArray.length() ; count_i++){
								JSONObject jsonObject_software = jsonArray.getJSONObject(count_i);
								System.out.println(jsonObject_software.toString()+" 解析json");
								int id = jsonObject_software.getInt("id");
								String soft_name = jsonObject_software.getString("soft_name");
								String dev_name = jsonObject_software.getString("dev_name");
								String dev_id = jsonObject_software.getString("dev_id");
								String update_time = jsonObject_software.getString("update_time");
								String soft_language = jsonObject_software.getString("soft_language");
								String soft_version = jsonObject_software.getString("soft_version");
								int download_count = jsonObject_software.getInt("soft_download_count");
								String introduce = jsonObject_software.getString("introduce");
								int size = jsonObject_software.getInt("soft_size");
								HashMap<String,Object> map = new HashMap<String,Object>();
								map.put("software", new Software(id, soft_name, dev_id, update_time,soft_language, soft_version,
										download_count, introduce, size, dev_name)) ;
								array.add(map);
							}

						}
					}
				}catch(Exception e ){
					System.out.println("我是第一层异常");
					e.printStackTrace();
					try{
						JSONObject jsonObject_2 = jsonArray.getJSONObject(count_i);
						count_i++;
						if(jsonObject_2.getString("get_operate").equals("game"))
							for( ; count_i<jsonArray.length();count_i++){

								JSONObject jsonObject_game = jsonArray.getJSONObject(count_i);
								System.out.println(jsonObject_game.toString()+"解析 jsongame");
								int id = jsonObject_game.getInt("id");
								String game_name = jsonObject_game.getString("game_name");
								String dev_name = jsonObject_game.getString("dev_name");
								String dev_id = jsonObject_game.getString("dev_id");
								String update_time = jsonObject_game.getString("update_time");
								String soft_language = jsonObject_game.getString("soft_language");
								String soft_version = jsonObject_game.getString("soft_version");
								int download_count = jsonObject_game.getInt("soft_download_count");
								String introduce = jsonObject_game.getString("introduce");
								int size = jsonObject_game.getInt("size");
								HashMap<String,Object> map = new HashMap<String,Object>();
								map.put("game", new game(id,game_name,dev_id,update_time,soft_language,soft_version,download_count
										,introduce,size,dev_name));
								array.add(map);
							}
					}
					catch(Exception a){
						System.out.println("我是第2层异常");
						a.printStackTrace();
						JSONObject jsonObject_2 = jsonArray.getJSONObject(count_i);
						count_i++;
						if(jsonObject_2.getString("get_operate").equals("amusement"))
							for( ; count_i < jsonArray.length() ; count_i++){
								JSONObject jsonObject_file = jsonArray.getJSONObject(count_i);
								System.out.println(jsonObject_file.toString()+"解析 jsonobjectfile");
								int id = jsonObject_file.getInt("id");
								String file_name = jsonObject_file.getString("file_name");
								String dev_name = jsonObject_file.getString("dev_name");
								String	dev_id = jsonObject_file.getString("dev_id");
								String update_time = jsonObject_file.getString("update_time");
								int download_count  = jsonObject_file.getInt("download_count");
								String introduce = jsonObject_file.getString("introduce");
								String classify_ = jsonObject_file.getString("classify");
								int size = jsonObject_file.getInt("size");
								HashMap<String,Object> map = new HashMap<String,Object>();
							/* map.put("amusement", new amusement(id, file_name, dev_id, classify_, update_time, download_count,
					                 introduce, size, dev_name));*/
								array.add(map);
							}
					}

				}


			}
			return array;
		}catch(Exception e){
//			if(array.size() >= 4)return array;
			e.printStackTrace();
		}
		return array;
	}

	public static ArrayList<String> get_estimate(String table,String id ){
		ArrayList<String> array = new ArrayList<String>();
		String path=Constant.database_path+"get_estimate&table="+table+"&ID="+id;
		try{
			HttpsURLConnection conn = (HttpsURLConnection) HttpsTest.getHttpsURLConnection(path);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			if(conn.getResponseCode() == 200){
				InputStream json = conn.getInputStream();
				byte data[] = new byte[1024];
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				int length = 0 ;
				while((length = json.read(data))!=-1){
					out.write(data, 0, length);
				}
				String json_out = out.toString("UTF-8");
				System.out.println(json_out);
				JSONArray jsonArray = new JSONArray(json_out);
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				String operate = jsonObject.getString("operage");

				if(operate.equals("estimate")){
					for(int i = 1 ; i < jsonArray.length() ;i++){
						JSONObject jsonObject_ = jsonArray.getJSONObject(i);
						array.add(jsonObject_.getString("estimate"));
					}
				}
			}
			return array;
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}

/*	public static ArrayList<HashMap<String,Object>> get_auther_other_work(String dev_id){
		ArrayList<HashMap<String,Object>> array = new ArrayList<HashMap<String,Object>>();
		String path = Constant.database_path+"auther_other_works&ID="+dev_id;
		try{
			HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			if(conn.getResponseCode() == 200){
				InputStream json = conn.getInputStream();
				byte data[] = new byte[1024];
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				int length = 0 ;
				while((length = json.read(data))!=-1){
					out.write(data, 0, length);
				}
				String json_out = out.toString("UTF-8");
				System.out.println(json_out);
				JSONArray jsonArray = new JSONArray(json_out);
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				String operate = jsonObject.getString("operage");
				int count_i = 1;
				try{
					if(operate.equals("auther_other_software")){
						for(count_i = 1 ; count_i < jsonArray.length() ;count_i++){
							JSONObject jsonObject_ = jsonArray.getJSONObject(count_i);
							 int id = jsonObject_.getInt("id");
		            		   String dev_name = jsonObject_.getString("dev_name");
		            		   String dev_id1 = jsonObject_.getString("dev_id");
		            		   String soft_name = jsonObject_.getString("soft_name");
	                         String update_time =jsonObject_.getString("update_time") ;
	                         String soft_language = jsonObject_.getString("soft_language");
	                         String soft_version =jsonObject_.getString("soft_version");
	                         int soft_download_count = jsonObject_.getInt("soft_download_count");
	                         String introduce =jsonObject_.getString("introduce").toString();
	                         int soft_size = jsonObject_.getInt("soft_size");
	                         HashMap<String,Object> map =new HashMap<String,Object>();
	                         map.put("auther_software", new Software(id,soft_name,dev_id1,update_time,soft_language,soft_version,
	                      		                  soft_download_count,introduce,soft_size,dev_name));
	                         array.add(map);
						}
					}
				}catch(Exception e ){
					if(operate.equals("auther_other_game")){
						for(count_i++; count_i<jsonArray.length();count_i++){
							 JSONObject jsonObject_ = jsonArray.getJSONObject(count_i);
							 int id = jsonObject_.getInt("id");
							 String game_name = jsonObject_.getString("game_name");
							 String dev_name = jsonObject_.getString("dev_name");
							 String dev_id1 = jsonObject_.getString("dev_id");
							 String update_time = jsonObject_.getString("update_time");
							 String soft_language = jsonObject_.getString("soft_language");
							 String soft_version = jsonObject_.getString("soft_version");
							 int download_count = jsonObject_.getInt("soft_download_count");
							 String introduce = jsonObject_.getString("introduce");
							 int size = jsonObject_.getInt("size");
							 HashMap<String,Object> map = new HashMap<String,Object>();
							 map.put("auther_software", new game(id,game_name,dev_id1,update_time,soft_language,soft_version,download_count
							           ,introduce,size,dev_name));
							 array.add(map);
						}
					}
				}
				return array;
			}
		}catch(Exception e){

		}


		return null;
	}*/
	//amusement&time=1&classify=mp3
	/*public static ArrayList<amusement> get_amusement(String classify ,int time){
		ArrayList<amusement> array = new ArrayList<amusement>();
		String path = Constant.database_path+"amusement&time="+time+"&classify="+classify;

		try{
			HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			if(conn.getResponseCode() == 200){
				InputStream json = conn.getInputStream();
				byte data[] = new byte[1024];
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				int length = 0 ;
				while((length = json.read(data))!=-1){
					out.write(data, 0, length);
				}
				String json_out = out.toString("UTF-8");
				System.out.println(json_out);
				JSONArray jsonArray = new JSONArray(json_out);
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				String operate = jsonObject.getString("operate");
				if(operate.equals("amusement")){
					for(int i = 1 ; i < jsonArray.length() ; i++){
						 JSONObject jsonObject_ = jsonArray.getJSONObject(i);
						 int id = jsonObject_.getInt("id");
						 String file_name = jsonObject_.getString("file_name");
						 String dev_name = jsonObject_.getString("dev_name");
						 String	dev_id = jsonObject_.getString("dev_id");
						 String update_time = jsonObject_.getString("update_time");
						 int download_count  = jsonObject_.getInt("download_count");
						 String introduce = jsonObject_.getString("introduce");
						 String classify_ = jsonObject_.getString("classify");
						 int size = jsonObject_.getInt("size");

						 array.add(new amusement(i, file_name, dev_id, classify_, update_time, download_count,
								                 introduce, size, dev_name));
					}
				}
			}
			return array;
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}*/


	public static ArrayList<game> get_game(int time,int classify){
		ArrayList<game> array = new ArrayList<game>();
		String path = Constant.database_path+"game&time="+time+"&classify="+classify;
		try{
			HttpsURLConnection conn = (HttpsURLConnection) HttpsTest.getHttpsURLConnection(path);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			if(conn.getResponseCode() == 200){
				InputStream json = conn.getInputStream();
				byte data[] = new byte[1024];
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				int length = 0 ;
				while((length = json.read(data))!=-1){
					out.write(data, 0, length);
				}
				String json_out = out.toString("UTF-8");
				System.out.println(json_out);
				JSONArray jsonArray = new JSONArray(json_out);
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				String operate = jsonObject.getString("operate");
				if(operate.equals("game")){
					for(int i = 1 ; i<jsonArray.length();i++){
						JSONObject jsonObject_ = jsonArray.getJSONObject(i);
						int id = jsonObject_.getInt("id");
						String game_name = jsonObject_.getString("game_name");
						String dev_name = jsonObject_.getString("dev_name");
						String dev_id1 = jsonObject_.getString("dev_id");
						String update_time = jsonObject_.getString("update_time");
						String soft_language = jsonObject_.getString("soft_language");
						String soft_version = jsonObject_.getString("soft_version");
						int download_count = jsonObject_.getInt("soft_download_count");
						String introduce = jsonObject_.getString("introduce");
						int size = jsonObject_.getInt("size");
						array.add(new game(id,game_name,dev_id1,update_time,soft_language,soft_version,download_count
								,introduce,size,dev_name));
					}
				}
			}
			return array;
		}catch(Exception e){
			e.printStackTrace();
		}


		return null;
	}


}








