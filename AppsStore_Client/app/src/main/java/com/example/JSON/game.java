package com.example.JSON;

import java.io.Serializable;

public class game implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id ;
	private String game_name;
	private String dev_id;
	private String dev_name;
	private String update_time;
	private String game_language;
	private String game_version;
	private int download_count ;
	private String introduce;
	private int size;

	public game(int id ,String game_name,String dev_id,String update_time,String game_language,String game_version,
				int download_count,String introduce,int size,String dev_name){
		this.id = id;
		this.dev_name = dev_name;
		this.game_name = game_name;
		this.dev_id = dev_id;
		this.update_time = update_time;
		this.game_language = game_language;
		this.game_version = game_version;
		this.download_count = download_count;
		this.introduce = introduce;
		this.size = size;

	}
	public int getid(){
		return id;
	}
	public int getsize(){
		return size;
	}
	public int getdownload_count(){
		return download_count;
	}
	public String getgame_name(){
		return game_name;
	}
	public String getdev_id(){
		return dev_id;
	}
	public String getdev_name(){
		return dev_name;
	}
	public String getupdate_time(){
		return update_time;
	}
	public String getgame_language(){
		return game_language;
	}
	public String getgame_version(){
		return game_version;
	}
	public String getintroduce(){
		return introduce;
	}
	public String get_who(){
		return "game";
	}
}













