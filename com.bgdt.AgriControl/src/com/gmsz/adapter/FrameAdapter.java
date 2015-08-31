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
 * Class name:FrameAdapter Description: ������
 * 
 */
public class FrameAdapter extends BaseAdapter {
	private static final String TAG = "FrameAdapter";
	private List<Frame> frames;// �ڰ󶨵�����
	private int resource;// �󶨵���Ŀ����
	private LayoutInflater inflater;
	private int screenCount;// ������
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
		return frames.size();// ��������
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

			Frame frame = frames.get(position);// һ������

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
				Log.i("Demo", "����convertView" + position);
				convertView = inflater.inflate(resource, null);// ������Ŀ�������
			}
			textView = (TextView) convertView.findViewById(R.id.textView);
			textView.getBackground().setAlpha(100);
			int width = getWidth(position);
			int height = getHeight(position);
			surfaceView = (SurfaceView) convertView.findViewById(R.id.videoView);
			FrameLayout.LayoutParams videoViewParams = (FrameLayout.LayoutParams) surfaceView.getLayoutParams(); // ȡ�ؼ�textView��ǰ�Ĳ��ֲ���
			webView = (WebView) convertView.findViewById(R.id.webView);
			WebSettings settings = webView.getSettings();
			settings.setUseWideViewPort(true);//�趨֧��viewport
			//settings.setLoadWithOverviewMode(true);//����web����Ӧ��Ļ��С
			//settings.setJavaScriptEnabled(true);//�趨֧��js
			//settings.setBuiltInZoomControls(true);
			//settings.setSupportZoom(true);//�趨֧������  
			
			//webview ����Ӧ��Ļ
			webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			webView.getSettings().setLoadWithOverviewMode(true);
			
			if ("WEB".equals(type.toUpperCase()) || "HDMI-VGA".equals(type) || "VIDEO".equals(type.toUpperCase())) {
				FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) webView.getLayoutParams(); // ȡ�ؼ�textView��ǰ�Ĳ��ֲ���
				linearParams.height = height;// �ؼ��� �߶�
				linearParams.width = width;// �ؼ��Ŀ��
				//imageView.setLayoutParams(linearParams); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ�</pre>
				webView.setLayoutParams(linearParams);
			   videoViewParams.height = 0;// �ؼ��� �߶�
				videoViewParams.width = 0;// �ؼ��Ŀ��
			/*textView.setHeight(height);
				textView.setWidth(width);*/

			} else {
				int oldHeight = videoViewParams.height;
				int oldWidth = videoViewParams.width;
				if (oldHeight < 200) {
					videoViewParams.height = height;// �ؼ��� �߶�
					videoViewParams.width = width;// �ؼ��Ŀ��
				} else {// Ϊ����surfaceView �ػ� ���޸�һ�����Ŀ��
					videoViewParams.height = height;// �ؼ��� �߶�
					videoViewParams.width = oldWidth - 1;// �ؼ��Ŀ��
				}
			}
			surfaceView.setLayoutParams(videoViewParams); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ�</pre>

			// �������ʵ�����ݰ�
			textView.setText(frame.getName());
			if ("Web".equals(type) || "Video".equals(type)) {		
					webView.loadUrl(frame.getData());	
			} else {// ��ʾһ�ŵ���ͼƬ
					
				//String str = "http://192.168.18.145/index.html?id=default";
				String defaulturl = MixcellaneousUtil.getInstance().getTableInsertDefaultUrl();
				webView.loadUrl(defaulturl);
			} 
			frame.setConvertView(convertView);
		} catch (Exception e) {
			Log.e("Demo", "getView��" +e.getMessage());
		}
		return convertView;
	}

	/**
	 * 
	 * Description:���ݷ���ȡ��һ��item�ĸ�
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
	 * Description: ���ݷ���ȡ��һ��item�Ŀ�
	 * 
	 * @return
	 */
	private int getWidth(int position) {
		int width = 890;
		if (screenCount == 2) {
			width = 1780;
		} else if (screenCount == 3) {
			width = 593;// ������
		}

		else if (screenCount == 3 && position == 1) {
			width = 1780;// ���������м�
		}
		return width;
	}

}
