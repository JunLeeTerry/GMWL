package com.bigdata.bgis.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.bigdata.bgis.R;
import com.bigdata.bgis.adapter.MicphonesAdapter;
import com.bigdata.bgis.domain.Device;

public class MicphonesFragment extends Fragment{
	private final static String TAG = "bgis/AudiosFragment";
	private ArrayList<Device> micphoneDevices;
	
	private GridView micphoneView;
	
	public MicphonesFragment(ArrayList<Device> micphoneDevices){
		this.micphoneDevices = micphoneDevices;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_micphones, null);
		micphoneView = (GridView)view.findViewById(R.id.micphoneview);
		
		MicphonesAdapter adapter  =  new MicphonesAdapter(this.getActivity(),micphoneDevices);
		micphoneView.setAdapter(adapter);
		
		return view;
	}
	
}
