package com.bigdata.bgis;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Choreographer.FrameCallback;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

import com.bigdata.bgis.R.id;
import com.bigdata.bgis.domain.Device;
import com.bigdata.bgis.iflytek.IFlytekUtil;
import com.bigdata.bgis.util.CommandUtil;
import com.bigdata.bgis.util.DeviceManager;
import com.bigdata.bgis.util.JsonUtil;
import com.bigdata.bgis.util.ScreenUtil;
import com.bigdata.bgis.util.ToolbarButtonUtil;

public class MainActivity extends FragmentActivity{
	private static final String TAG = "bgis/MainActivity";

	private ImageView screenSwitch;// 显示设备开关
	private boolean screenStatus = false;// 显示设备开关状态

	private ImageView split1Button;//1分屏按钮
	private ImageView split4Button;//4分屏按钮

	private ImageView voiceCtrlButton;//左1语音控制按钮
	private ImageView modeSwitchButton;//模式选择按钮
	
	private RelativeLayout screenContainer;//屏幕区域
	private RelativeLayout toolbarContainer;//底部按钮栏区域
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置横屏状态
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 取消显示系统状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// 从布局文件中货的屏幕的layout
		screenContainer = (RelativeLayout) findViewById(R.id.screencontainer);

		// 打开直接显示4分屏状态
		// ScreenUtil.getInstance(this).show(screenContainer,4);

		// 请求获取所有设备列表
		try {
			JsonUtil.getInstance(new MessageHandler()).doPost(
					JsonUtil.ALLDEVICELIST);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

		// 设置显示设备开关点击事件
		screenSwitch = (ImageView) findViewById(R.id.screenswitch);
		screenSwitch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (screenStatus) {
					screenSwitch.setImageResource(R.drawable.screenclose);
					screenStatus = false;
					// ScreenUtil.getInstance(MainActivity.this).close(screenContainer);
				} else {
					screenSwitch.setImageResource(R.drawable.screenopen);
					screenStatus = true;
					// ScreenUtil.getInstance(MainActivity.this).close(screenContainer);
				}
			}
		});

		// 分屏按钮部分
		split1Button = (ImageView) findViewById(R.id.split1);
		split1Button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// if (screenStatus) {
				ScreenUtil.getInstance(MainActivity.this).show(screenContainer,
						1);
				/*
				 * } else{ Toast.makeText(MainActivity.this, "请先打开电源",
				 * Toast.LENGTH_LONG).show(); }
				 */
			}
		});

		split4Button = (ImageView) findViewById(R.id.split4);
		split4Button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (screenStatus) {
				ScreenUtil.getInstance(MainActivity.this).show(screenContainer,
						4);
				/*
				 * } else{
				 * Toast.makeText(MainActivity.this,"请先打开电源",Toast.LENGTH_LONG
				 * ).show(); }
				 */
			}
		});
		
		//获得按钮组父类控件
		toolbarContainer = (RelativeLayout)findViewById(R.id.toolbarcontainer);
		
		//语音控制按钮
		voiceCtrlButton = (ImageView) findViewById(R.id.voiceCtrlButton);
		voiceCtrlButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				showVoiceCtrlWindow(v);	
			}
		});
		
		//模式选择按钮
		modeSwitchButton = (ImageView) findViewById(R.id.modeButton);
		modeSwitchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showModePopupWindow(v);
			}
		});
	
	}

	
	// 新建消息处理内部类
	private class MessageHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// super.handleMessage(msg);
			// 判断是否完成json数据获取
			boolean hasFinishParseMsg = msg.getData().containsKey(
					JsonUtil.MESSAGE);
			if (hasFinishParseMsg) {
				// 进行界面图像加载
				// 添加显示设备元素
				//ArrayList<Device> showDevices = DeviceManager.getInstance()
						//.getShowDevices();
				ToolbarButtonUtil.getInstance().showToolbarButton(MainActivity.this, toolbarContainer);
				
			}
		}
	}
	
	//显示语音控制对话框
	private void showVoiceCtrlWindow(View view){
		IFlytekUtil iflytekUtil = IFlytekUtil.getInstance(MainActivity.this);
		iflytekUtil.init();
		iflytekUtil.show();
	}
	
	
	//显示模式选择对话框
	private void showModePopupWindow(View view){
		final String modename = "";//模式标签:开会模式还是散会模式
		View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_mode, null);
		
		ViewGroup openMeetingMode = (ViewGroup) contentView.findViewById(R.id.openmeetingmode);
		final PopupWindow modePopupWindow = new PopupWindow(contentView, 950,
				500, true);
		openMeetingMode.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//开会模式操作
				CommandUtil.getInstance().callMode("openmeeting");
				//Toast.makeText(MainActivity.this,"您已选择开会模式，正在执行。。。",Toast.LENGTH_LONG).show();
				
				Log.d(TAG,"send command: call openmeeting mode");
				modePopupWindow.dismiss();
			}
		});
		
		
		ViewGroup stopMeetingMode = (ViewGroup) contentView.findViewById(R.id.stopmeetingmode);
		stopMeetingMode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//散会模式操作
				CommandUtil.getInstance().callMode("closemeeting");
				//Toast.makeText(MainActivity.this,"您已选择散会模式，正在执行。。。",Toast.LENGTH_LONG).show();
				
				Log.d(TAG,"send command: call closemeeting mode");
				modePopupWindow.dismiss();
			}
		});
		
		modePopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				switch(modename){
				case "openmode":
					Toast.makeText(MainActivity.this,"你已选择开会模式,正在执行。。。",Toast.LENGTH_LONG).show();
				case "closemode":
					Toast.makeText(MainActivity.this,"你已选择散会模式,正在执行。。。",Toast.LENGTH_LONG).show();
				}
			}
		});
		
		modePopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.popupwindowbg));
		
		modePopupWindow.setTouchable(true);
		modePopupWindow.setFocusable(true);
		
		modePopupWindow.showAsDropDown(view, 800, -300);
		modePopupWindow.update();
	}
}
