package com.gmsz.preview;

import java.lang.reflect.Field;

import com.gmsz.demo4.R;
import com.gmsz.demo4.R.id;
import com.gmsz.domain.BaseDetailInfo;
import com.gmsz.domain.Frame;
import com.gmsz.utils.MixcellaneousUtil;
import com.gmsz.view.VlcVideoView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 
 * 预览窗口的Adapter
 * 
 * @author Jun Lee
 * 
 */
public class PreViewAdapter extends BaseAdapter {
	// private Frame frame;
	private int resource;// layout的资源值
	private LayoutInflater layoutInflater;
	private BaseDetailInfo basedetailinfo;// 基地详细信息

	// controller
	private TextView textView;
	private WebView webView;
	private SurfaceView surfaceView;

	// the height and width of the controller
	private int height;
	private int width;

	private Context context;
	
	private String defaulturl;

	// private static final String defaulturl = "";

	public PreViewAdapter(Context context, BaseDetailInfo baseDetailInfo,
			int resource) {
		// this.frame = frame;
		this.basedetailinfo = baseDetailInfo;
		this.resource = resource;
		this.layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int position) {
		return basedetailinfo;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(basedetailinfo == null){
			webView.loadUrl(defaulturl);
		}
		
		String type = basedetailinfo.getType();
		if (convertView == null)
			convertView = layoutInflater.inflate(resource, null);
		surfaceView = (SurfaceView) convertView
				.findViewById(R.id.preview_surfaceview);
		FrameLayout.LayoutParams videoViewParams = (FrameLayout.LayoutParams) surfaceView
				.getLayoutParams();
		webView = (WebView) convertView.findViewById(R.id.preview_webview);

		WebSettings settings = webView.getSettings();
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setJavaScriptEnabled(true);
		// settings.setBuiltInZoomControls(true);
		// settings.setSupportZoom(true);

		videoViewParams.height = 0;// 控件的 高度
		videoViewParams.width = 0;// 控件的宽度

		surfaceView.setLayoutParams(videoViewParams); // 使设置好的布局参数应用到控件</pre>
		
		defaulturl = MixcellaneousUtil.getInstance()
				.getTableInsertDefaultUrl();
		webView.loadUrl(defaulturl);
		
		// 如果是web类型的，加载<data>标签中的预览地址
		if ("WEB".equals(type.toUpperCase())
				|| "VIDEO".equals(type.toUpperCase())) {

			webView.loadUrl(basedetailinfo.getData());

		} else {
			// 显示本地网页
			// String str = "http://192.168.18.145/index.html?id=default";
			webView.loadUrl(defaulturl);

		}
		return convertView;

	}
}
