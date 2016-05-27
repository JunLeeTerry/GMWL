package com.bigdata.bgis.adapter;

import java.util.ArrayList;

import com.bigdata.bgis.R;
import com.bigdata.bgis.domain.Device;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ToolbarButtonAdapter extends BaseAdapter{

	private ArrayList<Device> devices;
	private LayoutInflater inflater;
	private Context context;
	
	public ToolbarButtonAdapter(Context context,ArrayList<Device> devices){
		this.devices = devices;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return devices.size();
	}

	@Override
	public Object getItem(int position) {
		return devices.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.toolbaritem, null);
		return null;
	}

}
