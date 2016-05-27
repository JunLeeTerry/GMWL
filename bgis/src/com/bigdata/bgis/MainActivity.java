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

	private ImageView screenSwitch;// ��ʾ�豸����
	private boolean screenStatus = false;// ��ʾ�豸����״̬

	private ImageView split1Button;//1������ť
	private ImageView split4Button;//4������ť

	private ImageView voiceCtrlButton;//��1�������ư�ť
	private ImageView modeSwitchButton;//ģʽѡ��ť
	
	private RelativeLayout screenContainer;//��Ļ����
	private RelativeLayout toolbarContainer;//�ײ���ť������
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// ���ú���״̬
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ȡ����ʾϵͳ״̬��
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// �Ӳ����ļ��л�����Ļ��layout
		screenContainer = (RelativeLayout) findViewById(R.id.screencontainer);

		// ��ֱ����ʾ4����״̬
		// ScreenUtil.getInstance(this).show(screenContainer,4);

		// �����ȡ�����豸�б�
		try {
			JsonUtil.getInstance(new MessageHandler()).doPost(
					JsonUtil.ALLDEVICELIST);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

		// ������ʾ�豸���ص���¼�
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

		// ������ť����
		split1Button = (ImageView) findViewById(R.id.split1);
		split1Button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// if (screenStatus) {
				ScreenUtil.getInstance(MainActivity.this).show(screenContainer,
						1);
				/*
				 * } else{ Toast.makeText(MainActivity.this, "���ȴ򿪵�Դ",
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
				 * Toast.makeText(MainActivity.this,"���ȴ򿪵�Դ",Toast.LENGTH_LONG
				 * ).show(); }
				 */
			}
		});
		
		//��ð�ť�鸸��ؼ�
		toolbarContainer = (RelativeLayout)findViewById(R.id.toolbarcontainer);
		
		//�������ư�ť
		voiceCtrlButton = (ImageView) findViewById(R.id.voiceCtrlButton);
		voiceCtrlButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				showVoiceCtrlWindow(v);	
			}
		});
		
		//ģʽѡ��ť
		modeSwitchButton = (ImageView) findViewById(R.id.modeButton);
		modeSwitchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showModePopupWindow(v);
			}
		});
	
	}

	
	// �½���Ϣ�����ڲ���
	private class MessageHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// super.handleMessage(msg);
			// �ж��Ƿ����json���ݻ�ȡ
			boolean hasFinishParseMsg = msg.getData().containsKey(
					JsonUtil.MESSAGE);
			if (hasFinishParseMsg) {
				// ���н���ͼ�����
				// �����ʾ�豸Ԫ��
				//ArrayList<Device> showDevices = DeviceManager.getInstance()
						//.getShowDevices();
				ToolbarButtonUtil.getInstance().showToolbarButton(MainActivity.this, toolbarContainer);
				
			}
		}
	}
	
	//��ʾ�������ƶԻ���
	private void showVoiceCtrlWindow(View view){
		IFlytekUtil iflytekUtil = IFlytekUtil.getInstance(MainActivity.this);
		iflytekUtil.init();
		iflytekUtil.show();
	}
	
	
	//��ʾģʽѡ��Ի���
	private void showModePopupWindow(View view){
		final String modename = "";//ģʽ��ǩ:����ģʽ����ɢ��ģʽ
		View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_mode, null);
		
		ViewGroup openMeetingMode = (ViewGroup) contentView.findViewById(R.id.openmeetingmode);
		final PopupWindow modePopupWindow = new PopupWindow(contentView, 950,
				500, true);
		openMeetingMode.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//����ģʽ����
				CommandUtil.getInstance().callMode("openmeeting");
				//Toast.makeText(MainActivity.this,"����ѡ�񿪻�ģʽ������ִ�С�����",Toast.LENGTH_LONG).show();
				
				Log.d(TAG,"send command: call openmeeting mode");
				modePopupWindow.dismiss();
			}
		});
		
		
		ViewGroup stopMeetingMode = (ViewGroup) contentView.findViewById(R.id.stopmeetingmode);
		stopMeetingMode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//ɢ��ģʽ����
				CommandUtil.getInstance().callMode("closemeeting");
				//Toast.makeText(MainActivity.this,"����ѡ��ɢ��ģʽ������ִ�С�����",Toast.LENGTH_LONG).show();
				
				Log.d(TAG,"send command: call closemeeting mode");
				modePopupWindow.dismiss();
			}
		});
		
		modePopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				switch(modename){
				case "openmode":
					Toast.makeText(MainActivity.this,"����ѡ�񿪻�ģʽ,����ִ�С�����",Toast.LENGTH_LONG).show();
				case "closemode":
					Toast.makeText(MainActivity.this,"����ѡ��ɢ��ģʽ,����ִ�С�����",Toast.LENGTH_LONG).show();
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
