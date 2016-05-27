package com.bigdata.bgis.util;

import com.bigdata.bgis.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.support.v7.appcompat.R.color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ScreenUtil {
	private static ScreenUtil screenUtil;
	private Activity activity;

	private ScreenUtil(Activity activity) {
		this.activity = activity;
	}

	public static ScreenUtil getInstance(Activity activity) {
		if (screenUtil == null) {
			screenUtil = new ScreenUtil(activity);
		}
		return screenUtil;
	}

	public void show(ViewGroup layout, int frameid) {
		layout.removeAllViews();
		addViews(layout, frameid);
	}
	
	public void close(ViewGroup layout){
		layout.removeAllViews();
	}
	
	private void addViews(ViewGroup layout, int frameid) {
		switch (frameid) {
		case 1: {
			addView(0,0,1,1,layout);
			break;
		}
		case 4: {
			for(int i = 0; i< 4;i++){
				switch(i){
				case 0:{
					addView(0,0,(float)0.5,(float)0.5,layout);
					break;
				}
				case 1:{
					addView((float)0.5,0,(float)0.5,(float)0.5,layout);
					break;
				}
				case 2:{
					addView(0,(float)0.5,(float)0.5,(float)0.5,layout);
					break;
				}
				case 3:{
					addView((float)0.5,(float)0.5,(float)0.5,(float)0.5,layout);
					break;
				}
				}
					
			}
		}
		}
	}
	
	@SuppressLint("NewApi")
	private void addView(float x,float y,float height,float width,ViewGroup layout){
		int layout_width = layout.getWidth();
		int layout_height = layout.getHeight();
		
		ImageView imageView = new ImageView(activity);
		imageView.setX(x * layout_width);
		imageView.setY(y * layout_height);
		//imageView.setBackgroundResource(R.drawable.splitscreen);
		imageView.setBackgroundColor(Color.WHITE);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				(int)(layout_width * width - 6) , (int)(layout_height * height - 6));
		
		layout.addView(imageView,params);
	}
}
