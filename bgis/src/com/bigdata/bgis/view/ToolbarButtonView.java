package com.bigdata.bgis.view;
import com.bigdata.bgis.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ToolbarButtonView extends LinearLayout {
	private ImageView image;//控制按钮图标
	private TextView name;//控制按钮名称
	
	public ToolbarButtonView(Context context){
		super(context);
		LayoutInflater.from(context).inflate(R.layout.toolbaritem, this);
		image = (ImageView)findViewById(R.id.toolbaritemlogo);
		name = (TextView)findViewById(R.id.toolbaritemname);
	}
	
	public ToolbarButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.toolbaritem, this);
		image = (ImageView)findViewById(R.id.toolbaritemlogo);
		name = (TextView)findViewById(R.id.toolbaritemname);
	}
	
	public void setImage(int resource){
		this.image.setImageResource(resource);
	}
	
	public void setName(String name){
		this.name.setText(name);
	}
}
