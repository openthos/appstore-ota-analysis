package com.example.android_app_stroe;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by yoko on 2016/7/14.
 */
public class Why_Hate_item  {
    private  String content;
    private String time;
    public Why_Hate_item(String content,String time){
        this.content = content;
        this.time= time;
    }
    public String getContent(){
        return content;
    }
    public String getTime(){
        return  time;
    }
}
