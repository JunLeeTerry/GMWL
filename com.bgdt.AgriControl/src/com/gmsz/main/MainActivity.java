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
	private List<Scene> sceneList;// ���г�����Ϣ
	private Spinner sceneSp;// ����������
	private ArrayAdapter<String> adapter;// ������
	private String[] spValue;// ���������ؼ�ֵ
	public static GridView sceneGv; // ������ʾ��Ϣ

	private Scene currScene;// ��ǰ��ʾ�ĳ���
	private List<Frame> currFrames;// ��ǰ��ʾ�ĳ���������Ϣ

	private List<BaseInfo> baseInfoList;// ���л�����Ϣ
	private ListView listView;// ���listview������Ϣ
	private DragGridView baseInfoGv; // �ұ߻�����ϸ��Ϣ
	private Spinner splitSp;// ����������
	private List<SplitInfo> splitInfoList;// ������Ϣ�б�
	private SplitAdapter splitAdapter;// ����������
	private static GridView preview_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// ����Ϊ����
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

		// ȡ��״̬��
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		demoService = new UdpService();
		readData();// ���ļ��ж�ȡ����

		setSceneSpinner();// ����������
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
	 * Log.v(TAG, "���£�"); break; case Menu_About: Log.v(TAG, "���ڣ�"); break; case
	 * Menu_Exit: Log.v(TAG, "�˳���"); break; default: Log.v(TAG, "δƥ�䣡"); break;
	 * } return super.onOptionsItemSelected(item); }
	 */

	/**
	 * ���ļ��ж�ȡ����
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
	 * ���ó�������������
	 */
	private void setSceneSpinner() {
		// �����ؼ����
		sceneSp = (Spinner) findViewById(R.id.sceneSp);
		// ����ѡ������ArrayAdapter��������
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spValue);
		// ���������б�ķ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		sceneSp.setAdapter(adapter);
		// ����¼�Spinner�¼�����
		sceneSp.setOnItemSelectedListener(new SpinnerSelectedListener());
		// sceneSp.setPrompt("��ѡ�񳡾�:");
		sceneSp.setVisibility(View.VISIBLE);
	}

	/**
	 * ���÷�������
	 */
	private void setSpliteSpinner() {
		// �����ؼ����
		splitSp = (Spinner) findViewById(R.id.splitSp);
		// ����ѡ������ArrayAdapter��������
		splitAdapter = new SplitAdapter(this, splitInfoList, R.layout.splititem);
		// ���������б�ķ��
		// splitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		splitSp.setAdapter(splitAdapter);
		// splitSp.setPrompt("ֻ��������");
		// ����¼�Spinner�¼�����
		splitSp.setOnItemSelectedListener(new SplitSelectedListener());
		/*
		 * splitAdapter.setOnItemSelectedListener(new
		 * SpinnerSelectedListener()); splitAdapter.setVisibility(View.VISIBLE);
		 */
	}

	/**
	 * 
	 * ������߻�����Ϣ
	 */
	private void showBaseInfo() {
		BaseInfoAdapter baseInfoAdapter = new BaseInfoAdapter(this,
				baseInfoList, R.layout.leftitem);
		listView.setAdapter(baseInfoAdapter);
	}

	/**
	 * 
	 * ����zuo�߻�����Ϣ
	 */
	private void showBaseDetailInfo(int index) {
		BaseInfo baseInfo = baseInfoList.get(index);
		BaseDetailInfoAdapter baseInfoAdapter = new BaseDetailInfoAdapter(this,
				baseInfo.getDetailList(), R.layout.rightitem);
		baseInfoGv.setAdapter(baseInfoAdapter);

		// ����б�����֮�����ܵ��¼�

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
	 * ��ʾ�������� Description: please write your description
	 */
	private void showFrame(Scene scene) {
		// �л�����֮ǰ�Ȱѵ�ǰ��Ļ���ڲ�����Ƶֹͣ
		stopCurrVideo();

		currScene = scene;// ��ǰ��ʾ�ĳ���
		currFrames = scene.getFrameList();
		if (currFrames.size() < 4) {
			sceneGv.setNumColumns(currFrames.size());// ����������������3�У�����������
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

	// ��Ԥ��������ʾ����
	private void showPreview(BaseDetailInfo basedetailinfo) {
		stopCurrVideo();
		preview_view.setNumColumns(1);
		PreViewAdapter preViewAdapter = new PreViewAdapter(this,
				basedetailinfo, R.layout.previewitem);
		preview_view.setAdapter(preViewAdapter);
	}

	private void showSplitFrames(List<Frame> frameList, int screan) {
		// �л�����֮ǰ�Ȱѵ�ǰ��Ļ���ڲ�����Ƶֹͣ
		stopCurrVideo();
		if (screan < 8) {
			sceneGv.setNumColumns(screan);// ����������������3�У�����������
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
	 * Class name:ItemClickEvent Description:���listview����
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
	 * Class name:SpinnerSelectedListener Description:��������� ʹ��������ʽ����
	 * 
	 * @author LiangYan
	 */
	private class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			Toast.makeText(getApplicationContext(), "��ʾ����", Toast.LENGTH_SHORT)
					.show();
			Scene scene = sceneList.get(arg2);// ��ǰѡ�г���
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
	 * Description: ֹͣ���е�ǰ�ڲ���Ƶ
	 */
	public void stopCurrVideo() {
		if (currFrames != null) {// �ѵ�ǰ���������
			for (Frame frame : currFrames) {
				frame.setChange(true);
				frame.stopPlay();
			}
		}

	}

	/**
	 * 
	 * Description: ����
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
	 * Description: ����
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
	 * Description: ��ǽ
	 * 
	 * @param view
	 */
	public void onWall(View view) {
		demoService.onWall(currScene);
		Toast.makeText(getApplicationContext(), R.string.onwallSuccess,
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Description: Ԥ������
	 */
	public static GridView getPreView() {
		return preview_view;
	}

	// �ж��Ƿ�����������
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
