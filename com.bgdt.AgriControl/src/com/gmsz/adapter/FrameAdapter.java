package com.gmsz.adapter;

import java.lang.reflect.Field;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmsz.demo4.R;
import com.gmsz.domain.Frame;
import com.gmsz.utils.MixcellaneousUtil;
import com.gmsz.view.VlcVideoView;

/**
 * 
 * Class name:FrameAdapter Description: 适配器
 * 
 */
public class FrameAdapter extends BaseAdapter {
	private static final String TAG = "FrameAdapter";
	private List<Frame> frames;// 在绑定的数据
	private int resource;// 绑定的条目界面
	private LayoutInflater inflater;
	private int screenCount;// 几分屏
	private Activity ac;//MainActivity;

	public FrameAdapter(Context context, List<Frame> frames, int resource) {
		this.frames = frames;
		this.resource = resource;
		this.ac = (Activity) context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		screenCount = frames.size();
	}

	@Override
	public int getCount() {
		return frames.size();// 数据总数
	}

	@Override
	public Object getItem(int position) {
		return frames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {

			Frame frame = frames.get(position);// 一个画面

			if (!frame.isChange()) {
				Log.i(TAG, frame.getConvertView().toString());
				return frame.getConvertView();
			}
			Log.i("Demo",
					position + "name:" + frame.getName() + ",url:" + frame.getUrl() + "change:" + frame.isChange());
			frame.setChange(false);
			frame.stopPlay();

			WebView webView = null;
			SurfaceView surfaceView = null;
			TextView textView = null;

			String type = frame.getType();
			if (convertView == null) {
				Log.i("Demo", "创建convertView" + position);
				convertView = inflater.inflate(resource, null);// 生成条目界面对象
			}
			textView = (TextView) convertView.findViewById(R.id.textView);
			textView.getBackground().setAlpha(100);
			int width = getWidth(position);
			int height = getHeight(position);
			surfaceView = (SurfaceView) convertView.findViewById(R.id.videoView);
			FrameLayout.LayoutParams videoViewParams = (FrameLayout.LayoutParams) surfaceView.getLayoutParams(); // 取控件textView当前的布局参数
			webView = (WebView) convertView.findViewById(R.id.webView);
			WebSettings settings = webView.getSettings();
			settings.setUseWideViewPort(true);//设定支持viewport
			//settings.setLoadWithOverviewMode(true);//设置web自适应屏幕大小
			//settings.setJavaScriptEnabled(true);//设定支持js
			//settings.setBuiltInZoomControls(true);
			//settings.setSupportZoom(true);//设定支持缩放  
			
			//webview 自适应屏幕
			webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			webView.getSettings().setLoadWithOverviewMode(true);
			
			if ("WEB".equals(type.toUpperCase()) || "HDMI-VGA".equals(type) || "VIDEO".equals(type.toUpperCase())) {
				FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) webView.getLayoutParams(); // 取控件textView当前的布局参数
				linearParams.height = height;// 控件的 高度
				linearParams.width = width;// 控件的宽度
				//imageView.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件</pre>
				webView.setLayoutParams(linearParams);
			   videoViewParams.height = 0;// 控件的 高度
				videoViewParams.width = 0;// 控件的宽度
			/*textView.setHeight(height);
				textView.setWidth(width);*/

			} else {
				int oldHeight = videoViewParams.height;
				int oldWidth = videoViewParams.width;
				if (oldHeight < 200) {
					videoViewParams.height = height;// 控件的 高度
					videoViewParams.width = width;// 控件的宽度
				} else {// 为了让surfaceView 重绘 则修改一下它的宽度
					videoViewParams.height = height;// 控件的 高度
					videoViewParams.width = oldWidth - 1;// 控件的宽度
				}
			}
			surfaceView.setLayoutParams(videoViewParams); // 使设置好的布局参数应用到控件</pre>

			// 下面代码实现数据绑定
			textView.setText(frame.getName());
			if ("Web".equals(type) || "Video".equals(type)) {		
					webView.loadUrl(frame.getData());	
			} else {// 显示一张电脑图片
					
				//String str = "http://192.168.18.145/index.html?id=default";
				String defaulturl = MixcellaneousUtil.getInstance().getTableInsertDefaultUrl();
				webView.loadUrl(defaulturl);
			} 
			frame.setConvertView(convertView);
		} catch (Exception e) {
			Log.e("Demo", "getView：" +e.getMessage());
		}
		return convertView;
	}

	/**
	 * 
	 * Description:根据分屏取得一个item的高
	 * 
	 * @return
	 */
	private int getHeight(int position) {
		// int height = 496;
		int height = 596;
		if (screenCount == 8) {
			height = 298;
		}
		return height;
	}

	/**
	 * 
	 * Description: 根据分屏取得一个item的宽
	 * 
	 * @return
	 */
	private int getWidth(int position) {
		int width = 890;
		if (screenCount == 2) {
			width = 1780;
		} else if (screenCount == 3) {
			width = 593;// 三分屏
		}

		else if (screenCount == 3 && position == 1) {
			width = 1780;// 三分屏，中间
		}
		return width;
	}

}
