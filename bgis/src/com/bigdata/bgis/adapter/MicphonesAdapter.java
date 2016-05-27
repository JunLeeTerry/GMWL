package com.bigdata.bgis.adapter;

import java.util.ArrayList;

import com.bigdata.bgis.R;
import com.bigdata.bgis.domain.Device;
import com.bigdata.bgis.util.CommandUtil;
import com.bigdata.bgis.util.MicphonesCache;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MicphonesAdapter extends BaseAdapter {
	private static final String TAG = "bgis/MicphonesAdapter";
	
	private ArrayList<Device> micphoneDevices;//要传入adapter的设备对象列表
	private Context context;//要渲染视图的context（这边是MicphoneFragment）
	
	private TextView micphoneName;
	private SeekBar micphoneSeekBar;
	
	public MicphonesAdapter(Context context,ArrayList<Device> micphoneDevices){
		this.micphoneDevices = micphoneDevices;
		this.context = context;
	}

	@Override
	public int getCount() {
		return this.micphoneDevices.size();
	}

	@Override
	public Object getItem(int position) {
		return this.micphoneDevices.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Device device = micphoneDevices.get(position);
		convertView = LayoutInflater.from(context).inflate(R.layout.micphoneitem , null);
		
		micphoneName = (TextView)convertView.findViewById(R.id.micphonename);
		micphoneSeekBar = (SeekBar)convertView.findViewById(R.id.micphoneseekbar);
		
		micphoneName.setText(device.getName());
		
		int progress = MicphonesCache.getInstance().getProgress(device);
		micphoneSeekBar.setProgress(progress);
		
		micphoneSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				int stopprogress = seekBar.getProgress();
				Log.d(TAG,"micphone progress:"+stopprogress);
				MicphonesCache.getInstance().addProgress(device, stopprogress);
				
				//发送麦克风调节命令
				CommandUtil.getInstance().sendCommand(device, stopprogress+"");
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});
		return convertView;
	}

}
