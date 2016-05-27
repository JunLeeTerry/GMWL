package com.bigdata.bgis.fragment;

import com.bigdata.bgis.R;
import com.bigdata.bgis.domain.Device;
import com.bigdata.bgis.util.CommandUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CameraFragment extends Fragment{
	private static final String TAG = "bgis/CameraFragment";
	
	private Device cameraDevice;
	private ImageView upButton;//向上按钮
	private ImageView downButton;//向下按钮
	private ImageView rightButton;//向右按钮
	private ImageView leftButton;//向左按钮
	private TextView cameraName;//设备名称
	private Button closeButton;//焦距拉近按钮
	private Button farButton;//焦距拉远按钮
	
	public CameraFragment(Device cameraDevice){
		this.cameraDevice = cameraDevice;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_camera, null);
		TextView cameraName = (TextView)view.findViewById(R.id.cameraname);
		cameraName.setText(cameraDevice.getName());
		upButton = (ImageView)view.findViewById(R.id.cameraup);
		downButton = (ImageView)view.findViewById(R.id.cameradown);
		rightButton = (ImageView)view.findViewById(R.id.cameraright);
		leftButton = (ImageView)view.findViewById(R.id.cameraleft);
		
		upButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//发送摄像头上移命令
				CommandUtil.getInstance().sendCommand(cameraDevice,"上");
				Log.d(TAG,"send command:camera up");
			}
		});
		
		downButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//发送摄像头下移命令
				CommandUtil.getInstance().sendCommand(cameraDevice,"下");
				Log.d(TAG,"send command:camera down");
			}
		});
		
		rightButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//发送摄像头右移命令
				CommandUtil.getInstance().sendCommand(cameraDevice,"右");
				Log.d(TAG,"send command:camera right");
			}
		});
		
		leftButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//发送摄像头左移命令
				CommandUtil.getInstance().sendCommand(cameraDevice,"左");
				Log.d(TAG,"send command:camera left");
			}
		});
		
		closeButton = (Button)view.findViewById(R.id.cameraclose);
		closeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//发送摄像头焦距拉近命令
				CommandUtil.getInstance().sendCommand(cameraDevice,"拉近");
				Log.d(TAG,"send command:camera close");
			}
		});
		
		farButton = (Button)view.findViewById(R.id.camerafar);
		farButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//发送摄像头焦距拉远命令
				CommandUtil.getInstance().sendCommand(cameraDevice, "拉远");
				Log.d(TAG,"send command:camera far");
			}
		});
		
		return view;
	}
}
