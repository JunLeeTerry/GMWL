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
 * Description: 初始化基本详细信息 适配器
 */
public class BaseDetailInfoAdapter extends BaseAdapter {
	private List<BaseDetailInfo> baseDetailInfos;//在绑定的数据
	private int resource;//绑定的条目界面
	private LayoutInflater inflater;
	
	public BaseDetailInfoAdapter(Context context, List<BaseDetailInfo> baseDetailInfos, int resource) {
		this.baseDetailInfos = baseDetailInfos;
		this.resource = resource;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return baseDetailInfos.size();//数据总数
	}

	@Override
	public Object getItem(int position) {
		return baseDetailInfos.get(position);//获取单个的元素
	}

	@Override
	public long getItemId(int position) {
		return position;//获取单个元素的id，此处id就是元素的定位值
	}

	//getView是用来获得显示定义的数据的一个视图
	//获取显示基本数据的详细的视图
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView nameView = null;
		ImageView imageView = null;
		if(convertView==null){
			convertView = inflater.inflate(resource, null);//生成条目界面对象
			nameView = (TextView) convertView.findViewById(R.id.detailName);//get textview,the content is detail name
			imageView = (ImageView) convertView.findViewById(R.id.detailImg);//get imageview,the content is logo picture
			
			//view储存器包括名称和图像
			ViewCache cache = new ViewCache();
			cache.nameView = nameView;//名称
			cache.imageView = imageView;//图像
			convertView.setTag(cache);
		}else{
			ViewCache cache = (ViewCache) convertView.getTag();
			nameView = cache.nameView;
			imageView = cache.imageView;
		}
		BaseDetailInfo baseDetailInfo = baseDetailInfos.get(position);
		//下面代码实现数据绑定
		nameView.setText(baseDetailInfo.getName());
		
		//根据不同的资源类型加载不同的图片
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
