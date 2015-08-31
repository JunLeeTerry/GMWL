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
 * Description:初始化基本信息 适配器
 */
public class BaseInfoAdapter extends BaseAdapter {
	private List<BaseInfo> baseInfos;//在绑定的数据
	private int resource;//绑定的条目界面
	private LayoutInflater inflater;//用于动态载入界面
	
	public BaseInfoAdapter(Context context, List<BaseInfo> baseInfos, int resource) {
		this.baseInfos = baseInfos;
		this.resource = resource;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return baseInfos.size();//数据总数
	}

	@Override
	public Object getItem(int position) {
		return baseInfos.get(position);//获得数据元素
	}

	@Override
	public long getItemId(int position) {
		return position;//获得id，这边的id就是position
	}

	@Override
	//this method is for getting view where shows the specified data
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView nameView = null;
		
		if(convertView==null){
			convertView = inflater.inflate(resource, null);//生成条目界面对象
			//显示基地名的text view
			nameView = (TextView) convertView.findViewById(R.id.infoName);
			
			ViewCache cache = new ViewCache();
			cache.nameView = nameView;			
			convertView.setTag(cache);
		}else{
			ViewCache cache = (ViewCache) convertView.getTag();
			nameView = cache.nameView;
		}
		BaseInfo baseInfo = baseInfos.get(position);
		//下面代码实现数据绑定
		nameView.setText(baseInfo.getName());
		
		
		return convertView;
	}
	
	private final class ViewCache{
		public TextView nameView;
	}

}
