package com.bigdata.bgis.fragment;

import java.util.ArrayList;

import com.bigdata.bgis.R;
import com.bigdata.bgis.adapter.LightsAdapter;
import com.bigdata.bgis.domain.Device;
import com.bigdata.bgis.util.CommandUtil;
import com.bigdata.bgis.util.LightsCache;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class LightsFragment extends Fragment{
	private static final String TAG = "bgis/LightsFragment";
	private ArrayList<Device> lightDevices;//所有灯光设备
	private GridView gridView;//显示灯光设备控件
	
	public LightsFragment(ArrayList<Device> lightDevices){
		this.lightDevices = lightDevices;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_lights, null);
		
		gridView = (GridView)view.findViewById(R.id.lightsgrid);
		
		LightsAdapter lightsAdapter = new LightsAdapter(this.getActivity(),lightDevices);
		gridView.setAdapter(lightsAdapter);
		
		
		//添加点击gridview元素的事件
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//View tmpview = view;
				LinearLayout container = (LinearLayout) view;
				ImageView lightimg = (ImageView)view.findViewById(R.id.lightimg);
				//灯泡点亮图片
				Drawable lightimgonResource = (LightsFragment.this).getResources().getDrawable(R.drawable.lightimgon, null);
				//灯泡未点亮图片
				Drawable lightimgoffResource = (LightsFragment.this).getResources().getDrawable(R.drawable.lightimgoff, null);
				/*if(lightimg.getBackground().getConstantState().equals(lightimgonResource.getConstantState())){
					lightimg.setBackground(lightimgoffResource);
					//发送关闭电灯指令
					CommandUtil.getInstance().sendCommand(lightDevices.get(position), "关");
					Log.d(TAG,"send command:No."+(position+1)+" light close");
				}
				else{
					lightimg.setBackground(lightimgonResource);
					CommandUtil.getInstance().sendCommand(lightDevices.get(position),"开");
					Log.d(TAG,"send command:No."+(position+1)+" light on");
				}*/
				Device device = lightDevices.get(position);
				if(LightsCache.getInstance().getStatus(device)){
					lightimg.setBackground(lightimgoffResource);
					CommandUtil.getInstance().sendCommand(lightDevices.get(position), "关");
					Log.d(TAG,"send command:No."+(position+1)+" light close");
					LightsCache.getInstance().setStatus(device, false);
				}else{
					lightimg.setBackground(lightimgonResource);
					CommandUtil.getInstance().sendCommand(lightDevices.get(position),"开");
					Log.d(TAG,"send command:No."+(position+1)+" light on");
					LightsCache.getInstance().setStatus(device, true);
				}
				
			}
		});
		
		return view;
	}
}
