/**
 * 
 */
package com.gmsz.adapter;

import java.util.List;

import com.gmsz.demo4.R;
import com.gmsz.domain.SplitInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author zhoufm
 *
 */
public class SplitAdapter extends BaseAdapter {
	private List<SplitInfo> splitInfos;//在绑定的数据
	private int resource;//绑定的条目界面
	private LayoutInflater inflater;
	
	

	public SplitAdapter(Context context,List<SplitInfo> splitInfos, int resource) {
		super();
		this.splitInfos = splitInfos;
		this.resource = resource;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return splitInfos.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return splitInfos.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView nameView = null;
		if(convertView==null){
			convertView = inflater.inflate(resource, null);//生成条目界面对象
			nameView = (TextView) convertView.findViewById(R.id.splitName);
			
			ViewCache cache = new ViewCache();
			cache.nameView = nameView;			
			convertView.setTag(cache);
		}else{
			ViewCache cache = (ViewCache) convertView.getTag();
			nameView = cache.nameView;
		}
		SplitInfo splitInfo = splitInfos.get(position);
		//下面代码实现数据绑定
		nameView.setText(splitInfo.getName());
		return convertView;
		
	}
	
	private final class ViewCache{
		public TextView nameView;
	}

}
