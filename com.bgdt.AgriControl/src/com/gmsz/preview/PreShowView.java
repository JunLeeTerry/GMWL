package com.gmsz.preview;

import com.gmsz.adapter.FrameAdapter;
import com.gmsz.domain.Frame;
import com.gmsz.main.MainActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 预览窗口控件
 * 
 * @author Jun Lee
 *
 */
public class PreShowView extends View {
	public PreShowView(Context context,AttributeSet attrs){
		super(context,attrs);
	}
	
	//预览窗口中显示内容的方法
	public void showFrame(){
		FrameAdapter adapter = (FrameAdapter) MainActivity.sceneGv.getAdapter();
		int position = adapter.getCount();
		Frame frame = (Frame) adapter.getItem(position);
		//此方法用于适配器检测到数据值的改变后自动更新view
		adapter.notifyDataSetChanged();
	}
}
