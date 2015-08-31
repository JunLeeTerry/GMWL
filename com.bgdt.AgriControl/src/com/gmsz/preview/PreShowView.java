package com.gmsz.preview;

import com.gmsz.adapter.FrameAdapter;
import com.gmsz.domain.Frame;
import com.gmsz.main.MainActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Ԥ�����ڿؼ�
 * 
 * @author Jun Lee
 *
 */
public class PreShowView extends View {
	public PreShowView(Context context,AttributeSet attrs){
		super(context,attrs);
	}
	
	//Ԥ����������ʾ���ݵķ���
	public void showFrame(){
		FrameAdapter adapter = (FrameAdapter) MainActivity.sceneGv.getAdapter();
		int position = adapter.getCount();
		Frame frame = (Frame) adapter.getItem(position);
		//�˷���������������⵽����ֵ�ĸı���Զ�����view
		adapter.notifyDataSetChanged();
	}
}
