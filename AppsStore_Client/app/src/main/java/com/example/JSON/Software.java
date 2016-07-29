package com.example.JSON;


import java.io.Serializable;

public class Software implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    private int soft_id;
    private String soft_name;
    private String dev_id;
    private String update_time;
    private String soft_language;
    private String soft_version;
    private int download_count;
    private String introduce ;
    private int size;
    private String dev_name ;
    public Software(int soft_id,String soft_name,String dev_id,String update_time,String soft_language,String soft_version
            ,int download_count,String introduce,int size,String dev_name){
        this.soft_id = soft_id ;
        this.soft_name = soft_name;
        this.dev_id = dev_id;
        this.dev_name = dev_name;
        this.download_count = download_count ;
        this.introduce = introduce;
        this.size = size;
        this.soft_version = soft_version;
        this.soft_language = soft_language;
        this.update_time = update_time;
    }
    public int getsoft_id(){
        return soft_id;
    }
    public int get_download_count(){
        return download_count;
    }
    public int get_size(){
        return size;
    }
    public String getsoft_name(){
        return soft_name;
    }
    public String getdev_id(){
        return dev_id;
    }
    public String getupdate_time(){
        return update_time;
    }
    public String getsoft_language(){
        return soft_language;
    }
    public String getsoft_version(){
        return soft_version;
    }
    public String getintroduce(){
        return introduce;
    }
    public String getdev_name(){
        return dev_name;
    }
    public String get_who(){
        return "Software";
    }


}