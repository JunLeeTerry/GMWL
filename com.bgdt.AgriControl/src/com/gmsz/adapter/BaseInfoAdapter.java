package com.gmsz.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gmsz.demo4.R;
import com.gmsz.domain.BaseInfo;
/**
 * 
 * Class name:BaseInfoAdapter
 * Description:��ʼ��������Ϣ ������
 */
public class BaseInfoAdapter extends BaseAdapter {
	private List<BaseInfo> baseInfos;//�ڰ󶨵�����
	private int resource;//�󶨵���Ŀ����
	private LayoutInflater inflater;//���ڶ�̬�������
	
	public BaseInfoAdapter(Context context, List<BaseInfo> baseInfos, int resource) {
		this.baseInfos = baseInfos;
		this.resource = resource;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return baseInfos.size();//��������
	}

	@Override
	public Object getItem(int position) {
		return baseInfos.get(position);//�������Ԫ��
	}

	@Override
	public long getItemId(int position) {
		return position;//���id����ߵ�id����position
	}

	@Override
	//this method is for getting view where shows the specified data
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView nameView = null;
		
		if(convertView==null){
			convertView = inflater.inflate(resource, null);//������Ŀ�������
			//��ʾ��������text view
			nameView = (TextView) convertView.findViewById(R.id.infoName);
			
			ViewCache cache = new ViewCache();
			cache.nameView = nameView;			
			convertView.setTag(cache);
		}else{
			ViewCache cache = (ViewCache) convertView.getTag();
			nameView = cache.nameView;
		}
		BaseInfo baseInfo = baseInfos.get(position);
		//�������ʵ�����ݰ�
		nameView.setText(baseInfo.getName());
		
		
		return convertView;
	}
	
	private final class ViewCache{
		public TextView nameView;
	}

}
