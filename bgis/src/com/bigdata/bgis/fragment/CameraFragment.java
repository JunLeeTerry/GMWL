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
	private ImageView upButton;//���ϰ�ť
	private ImageView downButton;//���°�ť
	private ImageView rightButton;//���Ұ�ť
	private ImageView leftButton;//����ť
	private TextView cameraName;//�豸����
	private Button closeButton;//����������ť
	private Button farButton;//������Զ��ť
	
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
				//��������ͷ��������
				CommandUtil.getInstance().sendCommand(cameraDevice,"��");
				Log.d(TAG,"send command:camera up");
			}
		});
		
		downButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��������ͷ��������
				CommandUtil.getInstance().sendCommand(cameraDevice,"��");
				Log.d(TAG,"send command:camera down");
			}
		});
		
		rightButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��������ͷ��������
				CommandUtil.getInstance().sendCommand(cameraDevice,"��");
				Log.d(TAG,"send command:camera right");
			}
		});
		
		leftButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��������ͷ��������
				CommandUtil.getInstance().sendCommand(cameraDevice,"��");
				Log.d(TAG,"send command:camera left");
			}
		});
		
		closeButton = (Button)view.findViewById(R.id.cameraclose);
		closeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��������ͷ������������
				CommandUtil.getInstance().sendCommand(cameraDevice,"����");
				Log.d(TAG,"send command:camera close");
			}
		});
		
		farButton = (Button)view.findViewById(R.id.camerafar);
		farButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��������ͷ������Զ����
				CommandUtil.getInstance().sendCommand(cameraDevice, "��Զ");
				Log.d(TAG,"send command:camera far");
			}
		});
		
		return view;
	}
}
