package com.example.loadImage;

import java.net.URL;

import com.example.Tool.Constant;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class load_jiemian {

	private int id ;
	private String classify ;
	public load_jiemian(int id ,String classify){
		this.id = id ;
		this.classify = classify ;
	}
	public Drawable[] get_jiemian(){
		Drawable[] drawable = new Drawable[3];
		String url = Constant.urlpath+classify+"/"+id+"/";
		try{
			drawable[0] = getDrawableFormUrl(url+"jiemian1.jpg");
			drawable[1] = getDrawableFormUrl(url+"jiemian2.jpg");
			drawable[2] = getDrawableFormUrl(url+"jiemian3.jpg");
		}catch(Exception e){

		}

		return drawable;
	}
	public Drawable getDrawableFormUrl(String imageUrl){
		Drawable drawable = null ;
		try {
			drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), "image.jpg");
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return drawable ;
	}

	public static Drawable[] get_viewpage(){
		Drawable[] drawable = null;

		drawable= new Drawable[4];
		String url = Constant.urlpath+"Viewpager"+"/";
		drawable[0] = getDrawableFormUrl_(url+"1.jpg");
		drawable[1] = getDrawableFormUrl_(url+"2.jpg");
		drawable[2] = getDrawableFormUrl_(url+"3.jpg");
		drawable[3] = getDrawableFormUrl_(url+"4.jpg");



		return drawable;
	}
	public static Drawable getDrawableFormUrl_(String imageUrl){
		Drawable drawable = null ;
		try {
			drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), "image.jpg");
		} catch (Exception e) {
//			throw new RuntimeException();
		}
		return drawable ;
	}
}