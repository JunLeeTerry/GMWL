package com.bigdata.bgis.adapter;

import java.util.ArrayList;

import com.bigdata.bgis.R;
import com.bigdata.bgis.domain.Device;
import com.bigdata.bgis.util.LightsCache;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LightsAdapter extends BaseAdapter {
	private static final String TAG = "bgis/LightsAdapter";
	
	
	private ImageView lightImg;
	private TextView lightName;

	private ArrayList<Device> lights;
	
	private Context context;
	
	public LightsAdapter(Context context,ArrayList<Device> lights){
		this.lights = lights;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return lights.size();
	}

	@Override
	public Object getItem(int position) {
		return lights.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Device light = lights.get(position);
		
		convertView = LayoutInflater.from(context).inflate(R.layout.lightitem, null);
		lightImg = (ImageView) convertView.findViewById(R.id.lightimg);
		lightName = (TextView) convertView.findViewById(R.id.lightname);
		
		lightName.setText(light.getName());	
		
	/*	lightImg.setOnClickListener(new OnClickListener() {
			
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				lightImg.setBackground(context.getDrawable(R.drawable.lightimgon));
			}
		});*/
		if(LightsCache.getInstance().getStatus(light)){
			lightImg.setBackground(context.getDrawable(R.drawable.lightimgon));
		}else{
			lightImg.setBackground(context.getDrawable(R.drawable.lightimgoff));
		}
		
		return convertView;
	}
	
	public ImageView getImageView(){
		return this.lightImg;
	}
}
