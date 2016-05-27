package com.bigdata.bgis.fragment;

import com.bigdata.bgis.R;
import com.bigdata.bgis.domain.Device;
import com.bigdata.bgis.util.CommandUtil;
import com.bigdata.bgis.util.SpeakerCache;
import com.bigdata.bgis.view.SpeakerVoiceBar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SpeakerFragment extends Fragment {
	private static final String TAG = "bgis/SpeakerFragment";
	private ImageView reduceVoice; //减少音量按钮
	private ImageView addVoice;//增加音量按钮
	private SpeakerVoiceBar voiceBar;//音量显示条
	
	private Device device;
	
	public SpeakerFragment(Device device){
		this.device = device;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_speaker, null);
		voiceBar = (SpeakerVoiceBar)view.findViewById(R.id.voicebar);
		voiceBar.setRating(SpeakerCache.getInstance().getVolum(device));
		
		reduceVoice = (ImageView)view.findViewById(R.id.reducevoicebutton);
		reduceVoice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				float addrated = voiceBar.getRating()-0.5f;
				voiceBar.setRating(addrated);
				Log.d(TAG,"raking : "+voiceBar.getRating());
				
				Float volum = voiceBar.getRating();//获取改变之后当前的音量
				SpeakerCache.getInstance().addVolum(device, volum);//记录音量缓存中
				
				CommandUtil.getInstance().sendCommand(device, volum+"");//发送调节音量指令
			}
		});
		
		addVoice = (ImageView)view.findViewById(R.id.addvoicebutton);
		addVoice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				float addrated = voiceBar.getRating()+0.5f;
				voiceBar.setRating(addrated);
				Log.d(TAG,"raking : "+voiceBar.getRating());
				
				Float volum = voiceBar.getRating();//获取改变之后当前的音量
				SpeakerCache.getInstance().addVolum(device, volum);//记录音量到换存中
				
				CommandUtil.getInstance().sendCommand(device, volum+"");//发送调节音量指令
			}
		});
		
		
		return view;
	}
}
