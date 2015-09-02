package com.gmsz.main;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gmsz.adapter.BaseDetailInfoAdapter;
import com.gmsz.adapter.BaseInfoAdapter;
import com.gmsz.adapter.FrameAdapter;
import com.gmsz.adapter.SplitAdapter;
import com.gmsz.demo4.R;
import com.gmsz.domain.BaseDetailInfo;
import com.gmsz.domain.BaseInfo;
import com.gmsz.domain.Frame;
import com.gmsz.domain.Scene;
import com.gmsz.domain.SplitInfo;
import com.gmsz.preview.PreViewAdapter;
import com.gmsz.service.UdpService;
import com.gmsz.utils.IpcScreanUtil;
import com.gmsz.view.DragGridView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	protected static final int Menu_Update = Menu.FIRST;
	protected static final int Menu_About = Menu.FIRST + 1;
	protected static final int Menu_Exit = Menu.FIRST + 2;
	private UdpService demoService;
	private List<Scene> sceneList;// 所有场景信息
	private Spinner sceneSp;// 场景下拉框
	private ArrayAdapter<String> adapter;// 适配器
	private String[] spValue;// 场景下拉控件值
	public static GridView sceneGv; // 场景显示信息

	private Scene currScene;// 当前显示的场景
	private List<Frame> currFrames;// 当前显示的场景画面信息

	private List<BaseInfo> baseInfoList;// 所有基本信息
	private ListView listView;// 左边listview基本信息
	private DragGridView baseInfoGv; // 右边基本详细信息
	private Spinner splitSp;// 分屏下拉框
	private List<SplitInfo> splitInfoList;// 分屏信息列表
	private SplitAdapter splitAdapter;// 分屏适配器
	private static GridView preview_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置为横屏
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*
		 * getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		 * getActionBar().hide();
		 */

		/*
		 * getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		 * getActionBar().hide();
		 */

		// 取消状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		demoService = new UdpService();
		readData();// 从文件中读取数据

		setSceneSpinner();// 设置下拉框
		setSpliteSpinner();

		sceneGv = (GridView) findViewById(R.id.sceneGv);

		// this is part of preview view
		preview_view = (GridView) findViewById(R.id.preview_view);

		// list view at media list
		listView = (ListView) findViewById(R.id.listview);
		listView.setOnItemClickListener(new ItemClickEvent());
		baseInfoGv = (DragGridView) findViewById(R.id.baseInfoGv);

		showBaseInfo();
		showBaseDetailInfo(0);

		if (!isNetworkAvailable(this)) {
			Toast.makeText(getApplicationContext(), R.string.nonetworktip,
					Toast.LENGTH_LONG).show();
		}
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // TODO
	 * Auto-generated method stub //return super.onCreateOptionsMenu(menu);
	 * getMenuInflater().inflate(R.menu.main, menu); return true;
	 * 
	 * 
	 * }
	 */

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // TODO
	 * Auto-generated method stub switch(item.getItemId()) { case Menu_Update:
	 * Log.v(TAG, "更新！"); break; case Menu_About: Log.v(TAG, "关于！"); break; case
	 * Menu_Exit: Log.v(TAG, "退出！"); break; default: Log.v(TAG, "未匹配！"); break;
	 * } return super.onOptionsItemSelected(item); }
	 */

	/**
	 * 从文件中读取数据
	 */
	private void readData() {
		try {
			sceneList = demoService.getScenes();
			spValue = new String[sceneList.size()];
			for (int i = 0; i < sceneList.size(); i++) {
				Scene scene = sceneList.get(i);
				spValue[i] = scene.getName();
			}
			splitInfoList = demoService.getSplitInfos();
			baseInfoList = demoService.getBaseInfos();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			Toast.makeText(getApplicationContext(), R.string.fileError,
					Toast.LENGTH_LONG).show();
			return;
		}
	}

	/**
	 * 设置场景下拉框属性
	 */
	private void setSceneSpinner() {
		// 下拉控件相关
		sceneSp = (Spinner) findViewById(R.id.sceneSp);
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spValue);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		sceneSp.setAdapter(adapter);
		// 添加事件Spinner事件监听
		sceneSp.setOnItemSelectedListener(new SpinnerSelectedListener());
		// sceneSp.setPrompt("请选择场景:");
		sceneSp.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置分屏属性
	 */
	private void setSpliteSpinner() {
		// 下拉控件相关
		splitSp = (Spinner) findViewById(R.id.splitSp);
		// 将可选内容与ArrayAdapter连接起来
		splitAdapter = new SplitAdapter(this, splitInfoList, R.layout.splititem);
		// 设置下拉列表的风格
		// splitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		splitSp.setAdapter(splitAdapter);
		// splitSp.setPrompt("只有这三天");
		// 添加事件Spinner事件监听
		splitSp.setOnItemSelectedListener(new SplitSelectedListener());
		/*
		 * splitAdapter.setOnItemSelectedListener(new
		 * SpinnerSelectedListener()); splitAdapter.setVisibility(View.VISIBLE);
		 */
	}

	/**
	 * 
	 * 现在左边基本信息
	 */
	private void showBaseInfo() {
		BaseInfoAdapter baseInfoAdapter = new BaseInfoAdapter(this,
				baseInfoList, R.layout.leftitem);
		listView.setAdapter(baseInfoAdapter);
	}

	/**
	 * 
	 * 现在zuo边基本信息
	 */
	private void showBaseDetailInfo(int index) {
		BaseInfo baseInfo = baseInfoList.get(index);
		BaseDetailInfoAdapter baseInfoAdapter = new BaseDetailInfoAdapter(this,
				baseInfo.getDetailList(), R.layout.rightitem);
		baseInfoGv.setAdapter(baseInfoAdapter);

		// 左边列表点击了之后会接受到事件

		// add item click listener
		baseInfoGv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg3) {
				BaseDetailInfo basedetailinfo = (BaseDetailInfo) adapter
						.getItemAtPosition(position);
				showPreview(basedetailinfo);
				// Toast.makeText(MainActivity.this, "click",
				// Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 显示场景画面 Description: please write your description
	 */
	private void showFrame(Scene scene) {
		// 切换场景之前先把当前屏幕正在播的视频停止
		stopCurrVideo();

		currScene = scene;// 当前显示的场景
		currFrames = scene.getFrameList();
		if (currFrames.size() < 4) {
			sceneGv.setNumColumns(currFrames.size());// 设置列数，三分屏3列，二分屏两列
		} else {
			sceneGv.setNumColumns(4);
		}
		try {
			FrameAdapter frameAdapter = new FrameAdapter(this, currFrames,
					R.layout.frameitem);
			sceneGv.setAdapter(frameAdapter);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			Toast.makeText(getApplicationContext(), R.string.showError,
					Toast.LENGTH_LONG).show();
		}

	}

	// 在预览窗口显示内容
	private void showPreview(BaseDetailInfo basedetailinfo) {
		stopCurrVideo();
		preview_view.setNumColumns(1);
		PreViewAdapter preViewAdapter = new PreViewAdapter(this,
				basedetailinfo, R.layout.previewitem);
		preview_view.setAdapter(preViewAdapter);
	}

	private void showSplitFrames(List<Frame> frameList, int screan) {
		// 切换场景之前先把当前屏幕正在播的视频停止
		stopCurrVideo();
		if (screan < 8) {
			sceneGv.setNumColumns(screan);// 设置列数，三分屏3列，二分屏两列
		} else {
			sceneGv.setNumColumns(4);

		}

		try {
			FrameAdapter frameAdapter = new FrameAdapter(this, frameList,
					R.layout.frameitem);
			sceneGv.setAdapter(frameAdapter);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			Toast.makeText(getApplicationContext(), R.string.showError,
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 
	 * Class name:ItemClickEvent Description:左边listview监听
	 * 
	 * @author LiangYan
	 */
	class ItemClickEvent implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			showBaseDetailInfo(position);
		}

	}

	/**
	 * 
	 * Class name:SpinnerSelectedListener Description:下拉框监听 使用数组形式操作
	 * 
	 * @author LiangYan
	 */
	private class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			Toast.makeText(getApplicationContext(), "显示场景", Toast.LENGTH_SHORT)
					.show();
			Scene scene = sceneList.get(arg2);// 当前选中场景
			showFrame(scene);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	private class SplitSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			SplitInfo split = splitInfoList.get(arg2);
			List<Frame> frames = currScene.getFrameList();
			List<Frame> splitframes = IpcScreanUtil.getSplitFrame(frames,
					split.getSplitValue());
			showSplitFrames(splitframes, split.getSplitValue());

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		// startCurrVideo();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// stopCurrVideo();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopCurrVideo();
	}

	/**
	 * 
	 * Description: 停止所有当前在播视频
	 */
	public void stopCurrVideo() {
		if (currFrames != null) {// 把当前场景画面的
			for (Frame frame : currFrames) {
				frame.setChange(true);
				frame.stopPlay();
			}
		}

	}

	/**
	 * 
	 * Description: 开屏
	 * 
	 * @param view
	 */
	public void openScreen(View view) {

		demoService.open();
		Toast.makeText(getApplicationContext(), R.string.openSuccess,
				Toast.LENGTH_LONG).show();

	}

	/**
	 * 
	 * Description: 关屏
	 * 
	 * @param view
	 */
	public void closeScreen(View view) {

		demoService.close();
		Toast.makeText(getApplicationContext(), R.string.closeSuccess,
				Toast.LENGTH_LONG).show();

	}

	/**
	 * 
	 * Description: 上墙
	 * 
	 * @param view
	 */
	public void onWall(View view) {
		demoService.onWall(currScene);
		Toast.makeText(getApplicationContext(), R.string.onwallSuccess,
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Description: 预览窗口
	 */
	public static GridView getPreView() {
		return preview_view;
	}

	// 判断是否有网络连接
	private boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
		} else {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
