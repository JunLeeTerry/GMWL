package com.gmsz.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmsz.demo4.R;
import com.gmsz.domain.BaseDetailInfo;
/**
 * 
 * Class name:BaseDetailInfoAdapter
 * Description: ��ʼ��������ϸ��Ϣ ������
 */
public class BaseDetailInfoAdapter extends BaseAdapter {
	private List<BaseDetailInfo> baseDetailInfos;//�ڰ󶨵�����
	private int resource;//�󶨵���Ŀ����
	private LayoutInflater inflater;
	
	public BaseDetailInfoAdapter(Context context, List<BaseDetailInfo> baseDetailInfos, int resource) {
		this.baseDetailInfos = baseDetailInfos;
		this.resource = resource;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return baseDetailInfos.size();//��������
	}

	@Override
	public Object getItem(int position) {
		return baseDetailInfos.get(position);//��ȡ������Ԫ��
	}

	@Override
	public long getItemId(int position) {
		return position;//��ȡ����Ԫ�ص�id���˴�id����Ԫ�صĶ�λֵ
	}

	//getView�����������ʾ��������ݵ�һ����ͼ
	//��ȡ��ʾ�������ݵ���ϸ����ͼ
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView nameView = null;
		ImageView imageView = null;
		if(convertView==null){
			convertView = inflater.inflate(resource, null);//������Ŀ�������
			nameView = (TextView) convertView.findViewById(R.id.detailName);//get textview,the content is detail name
			imageView = (ImageView) convertView.findViewById(R.id.detailImg);//get imageview,the content is logo picture
			
			//view�������������ƺ�ͼ��
			ViewCache cache = new ViewCache();
			cache.nameView = nameView;//����
			cache.imageView = imageView;//ͼ��
			convertView.setTag(cache);
		}else{
			ViewCache cache = (ViewCache) convertView.getTag();
			nameView = cache.nameView;
			imageView = cache.imageView;
		}
		BaseDetailInfo baseDetailInfo = baseDetailInfos.get(position);
		//�������ʵ�����ݰ�
		nameView.setText(baseDetailInfo.getName());
		
		//���ݲ�ͬ����Դ���ͼ��ز�ͬ��ͼƬ
		if("WEB".equals(baseDetailInfo.getType().toUpperCase())){
			imageView.setImageResource(R.drawable.web);
		}else if("VIDEO".equals(baseDetailInfo.getType().toUpperCase())){
			imageView.setImageResource(R.drawable.video);
		}else {
			imageView.setImageResource(R.drawable.hdmi);
		}
		
		return convertView;
	}
	
	private final class ViewCache{
		public TextView nameView;
		public ImageView imageView;
	}
	
	

}
