package com.bigdata.bgis.fragment;

import com.bigdata.bgis.R;
import com.bigdata.bgis.domain.Device;
import com.bigdata.bgis.util.CommandUtil;
import com.bigdata.bgis.view.DragSwitchButton;
import com.bigdata.bgis.view.DragSwitchButton.OnChangedListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProjectorFragment extends Fragment{
	private static final String TAG = "bgis/ProjectorFragment";
	private Device device;
	
	private DragSwitchButton projectorButton; //自定义滑动控件
	private TextView projectorName; //fragment左上角，显示设备的名称
	private Button tipButton;//显示提示信息的按钮
	private TextView projectorTip; //提示信息
	
	private boolean tipStatus = false;//提示内容显示状态
	
	public ProjectorFragment(Device device){
		this.device = device;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_projector, null);
		projectorButton = (DragSwitchButton) view.findViewById(R.id.projectorbutton);
		projectorButton.setOnChangedListener(new OnChangedListener() {
		
			@Override
			public void OnChanged(DragSwitchButton projectorButton, boolean checkState) {
				Log.d(TAG,checkState+"");
				if(checkState){
					CommandUtil.getInstance().sendCommand(device, "开");
					Log.d(TAG,"send command:projector open");
				}else{
					CommandUtil.getInstance().sendCommand(device,"关");
					Log.d(TAG,"send command:projector close");
				}
			}
		});
		
		projectorName  = (TextView)view.findViewById(R.id.projectorname);
		projectorName.setText(device.getName());
		
		tipButton = (Button)view.findViewById(R.id.tipbutton);//显示提示按钮
		projectorTip = (TextView)view.findViewById(R.id.projectortip);//提示文本框
		tipButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!tipStatus){
					tipButton.setText("隐藏");
					projectorTip.setVisibility(View.VISIBLE);
					tipStatus = true;
				}else {
					tipButton.setText("帮助");
					projectorTip.setVisibility(View.INVISIBLE);					
					tipStatus = false;
				}
				
			}
		});
		
		return view;
	}
}
