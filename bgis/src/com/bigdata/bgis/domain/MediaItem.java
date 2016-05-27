package com.bigdata.bgis.domain;

import android.graphics.Bitmap;

public class MediaItem {
	private String name;
	private int imageResourceId;
	private Bitmap imageBitmap;
	
	public MediaItem(){}
	
	public MediaItem(String name,Bitmap bitmap){
		this.name = name;
		this.imageBitmap = bitmap;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getImageResourceId(){
		return this.imageResourceId;
	}
	public void setImageResourceId(int imageResourceId){
		this.imageResourceId = imageResourceId;
	}
	
	public Bitmap getImageBitmap(){
		return this.imageBitmap;
	}
	
	public void setImageBitmap(Bitmap bitmap){
		this.imageBitmap = bitmap;
	}
}
