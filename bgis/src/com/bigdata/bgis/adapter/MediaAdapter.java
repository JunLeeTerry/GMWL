package com.bigdata.bgis.adapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.bigdata.bgis.R;
import com.bigdata.bgis.R.id;
import com.bigdata.bgis.domain.MediaItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MediaAdapter extends BaseAdapter{

	private ImageView mediaItemImageView;
	private TextView mediaItemName;
	
	private ArrayList<MediaItem> mediaList;
	private Context context;
	
	public MediaAdapter(Context context,ArrayList<MediaItem> mediaList){
		this.mediaList = mediaList;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		
		return mediaList.size();
	}

	@Override
	public Object getItem(int position) {
		return mediaList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MediaItem mediaItem = mediaList.get(position);
		convertView = LayoutInflater.from(context).inflate(R.layout.mediaitem, null);
		
		mediaItemImageView = (ImageView)convertView.findViewById(id.mediaitemimage);
		mediaItemName = (TextView)convertView.findViewById(id.mediaitemname);
		
		mediaItemName.setText(mediaItem.getName());
		//mediaItemImageView.setImageResource(mediaItem.getImageResourceId());
		mediaItemImageView.setImageBitmap(mediaItem.getImageBitmap());
		
		return convertView;
	}

}
