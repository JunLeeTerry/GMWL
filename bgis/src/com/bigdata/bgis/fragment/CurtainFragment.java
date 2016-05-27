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
import android.view.ViewGroup;
import android.widget.TextView;

public class CurtainFragment extends Fragment {
	private static final String TAG = "bgis/CurtainFragment";
	private Device device;
	private TextView curtainName;
	private DragSwitchButton curtainButton;
	
	public CurtainFragment(Device device){
		this.device = device;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_curtain, null);
		curtainName = (TextView) view.findViewById(R.id.curtainname);
		curtainName.setText(device.getName());
		curtainButton = (DragSwitchButton)view.findViewById(R.id.curtainbutton);
		
		curtainButton.setOnChangedListener(new OnChangedListener() {
			
			@Override
			public void OnChanged(DragSwitchButton projectorButton, boolean checkState) {
				Log.d(TAG,checkState+"");
				if(checkState){
					CommandUtil.getInstance().sendCommand(device, "¿ª");
					Log.d(TAG,"send command:curtain open");
				}else{
					CommandUtil.getInstance().sendCommand(device,"¹Ø");
					Log.d(TAG,"send command:contain close");
				}
			}
		});
		
		return view;
	}
}
