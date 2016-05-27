package com.bigdata.bgis.util;

import java.util.ArrayList;

import com.bigdata.bgis.R;
import com.bigdata.bgis.TypeImageMap;
import com.bigdata.bgis.domain.ButtonFragmentMap;
import com.bigdata.bgis.domain.Device;
import com.bigdata.bgis.factory.CtrlerFactory;
import com.bigdata.bgis.fragment.CameraFragment;
import com.bigdata.bgis.fragment.CurtainFragment;
import com.bigdata.bgis.fragment.LightsFragment;
import com.bigdata.bgis.fragment.MicphonesFragment;
import com.bigdata.bgis.fragment.ProjectorFragment;
import com.bigdata.bgis.fragment.SpeakerFragment;
import com.bigdata.bgis.view.ToolbarButtonView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

//��Ҫ�Թ�������ť����ʾ����һϵ�еĲ���
public class ToolbarButtonUtil {
	private final static String TAG = "bgis/ToolbarButtonUtil";

	// private ArrayList<ToolbarButtonView> buttonviews;//��¼���а�ť����
	private ArrayList<ButtonFragmentMap> buttonFragmentList = new ArrayList();// ��ť��fragment�Ķ�Ӧ��ϵ

	private CtrlerFactory factory;// ����fragment�Ĺ�����

	private static ToolbarButtonUtil toolbarButtonUtil;

	private ToolbarButtonUtil() {
		this.factory = new CtrlerFactory();
	}

	public static ToolbarButtonUtil getInstance() {
		if (toolbarButtonUtil == null) {
			toolbarButtonUtil = new ToolbarButtonUtil();
		}
		return toolbarButtonUtil;
	}

	// �ڹ�������ʾ��ť������ͼ��ӵ����ֿؼ���
	public void showToolbarButton(Context context, ViewGroup layout) {
		ArrayList<Device> devicelist = DeviceManager.getInstance()
				.getAllDevice();
		int buttonnum = devicelist.size();

		ArrayList<Device> lightsList = new ArrayList<Device>();// ��¼���еƹ��豸
		ArrayList<Device> micphonesList = new ArrayList<Device>();// ��¼������˷������豸

		// ��Ӵ���������豸�������ǵİ�ť�Ͷ�Ӧ��fragment��Ӧ����
		for (int i = 0; i < devicelist.size(); i++) {
			Device device = devicelist.get(i);
			int devicetype = device.getType();
			switch (devicetype) {
			case 2:
				ToolbarButtonView cameraButton = setToolbarButton(context,
						device);
				// layout.addView(cameraButton);
				// buttonviews.add(cameraButton);
				// CameraFragment cameraFragment = new CameraFragment(device);
				CameraFragment cameraFragment = (CameraFragment) factory
						.createFragment(device);
				buttonFragmentList.add(new ButtonFragmentMap(cameraButton,
						cameraFragment));
				break;
			case 3:
				ToolbarButtonView projectorButton = setToolbarButton(context,
						device);
				// layout.addView(projectorButton);
				// ProjectorFragment projectorFragment = new ProjectorFragment(
				// device);
				ProjectorFragment projectorFragment = (ProjectorFragment) factory
						.createFragment(device);
				buttonFragmentList.add(new ButtonFragmentMap(projectorButton,
						projectorFragment));
				break;
			case 4:
				ToolbarButtonView curtainButton = setToolbarButton(context,
						device);
				// layout.addView(curtainButton);
				// CurtainFragment curtainFragment = new
				// CurtainFragment(device);
				CurtainFragment curtainFragment = (CurtainFragment) factory
						.createFragment(device);
				buttonFragmentList.add(new ButtonFragmentMap(curtainButton,
						curtainFragment));
				break;
			case 5:
				lightsList.add(device);
				break;
			case 6:
				micphonesList.add(device);
				break;
			case 7:
				ToolbarButtonView speakerButton = setToolbarButton(context,
						device);
				// layout.addView(speakerButton);
				SpeakerFragment speakerFragment = (SpeakerFragment) factory
						.createFragment(device);
				buttonFragmentList.add(new ButtonFragmentMap(speakerButton,
						speakerFragment));
				break;
			}

		}
		// ��ӵ�ƿ��ư�ť
		if (lightsList.size() > 0) {
			ToolbarButtonView lightsButton = setLightToolbarButton(context);
			// layout.addView(lightsButton);
			// LightsFragment lightsFragment = new LightsFragment(lightsList);
			LightsFragment lightsFragment = (LightsFragment) factory
					.createConpoFragment(lightsList, 5);
			buttonFragmentList.add(new ButtonFragmentMap(lightsButton,
					lightsFragment));
		}
		// ����������ư�ť
		if (micphonesList.size() > 0) {
			ToolbarButtonView audiosButton = setMicphoneToolbarButton(context);
			// layout.addView(audiosButton);
			MicphonesFragment micphoneFragment = (MicphonesFragment) factory
					.createConpoFragment(micphonesList, 6);
			buttonFragmentList.add(new ButtonFragmentMap(audiosButton,
					micphoneFragment));
		}

		bindFragment(buttonFragmentList, context);
		addButtonView(buttonFragmentList, context, layout);
	}

	// ��fragment�Ͷ�Ӧ�İ�ť������
	private void bindFragment(ArrayList<ButtonFragmentMap> buttonFragmentList,
			Context context) {
		final FragmentActivity fragmentActivity = (FragmentActivity) context;
		final FrameLayout ctrlerFragment = (FrameLayout) ((Activity) context)
				.findViewById(R.id.ctrlerfrag);
		FragmentManager fragmentManager = fragmentActivity
				.getSupportFragmentManager();
		// final FragmentTransaction fragmentTransaction = fragmentManager
		// .beginTransaction();

		for (ButtonFragmentMap buttonFragment : buttonFragmentList) {
			final Fragment fragment = buttonFragment.getFragment();
			final ToolbarButtonView button = buttonFragment.getButton();
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					FragmentTransaction fragmentTransaction = fragmentActivity
							.getSupportFragmentManager().beginTransaction();
					fragmentTransaction.replace(R.id.ctrlerfrag, fragment);
					fragmentTransaction.commit();
				}
			});
		}
	}

	// ����Fragment ��button��ӵ���Ӧ��ϵ�б���
	private ToolbarButtonView setToolbarButton(Context context, Device device) {
		ToolbarButtonView button = new ToolbarButtonView(context);
		button.setName(device.getName());
		button.setImage(TypeImageMap.instance().getResource(device.getType()));
		return button;
	}

	// ������Ƶ�fragment��button��ӵ���Ӧ�Ĺ�ϵ�б���
	private ToolbarButtonView setLightToolbarButton(Context context) {
		ToolbarButtonView button = new ToolbarButtonView(context);
		button.setName("���");
		button.setImage(TypeImageMap.instance().getResource(5));
		return button;
	}

	// ������Ƶ��fragment��button��ӵ���Ӧ�Ĺ����б���
	private ToolbarButtonView setMicphoneToolbarButton(Context context) {
		ToolbarButtonView button = new ToolbarButtonView(context);
		button.setName("��˷�");
		button.setImage(TypeImageMap.instance().getResource(6));
		return button;
	}

	/*
	 * param:context��layout����Ӧ�İ�ťʵ������ť�ĸ�����Ŀǰ��ӵ��ǵڼ�����ť
	 */
	private void addButtonView(ArrayList<ButtonFragmentMap> buttonFragmentList,
			Context context, ViewGroup layout) {
		for (int i = 0; i < buttonFragmentList.size(); i++) {
			/*WindowManager windowManager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			Display display = windowManager.getDefaultDisplay();
			Point point = new Point();
			display.getSize(point);
			int divWidth = point.x;*/
			int divWidth = layout.getWidth();
			
			ToolbarButtonView toolbarButtonView = buttonFragmentList.get(i).getButton();
			RelativeLayout.LayoutParams params = new LayoutParams(
					(divWidth / buttonFragmentList.size()), LayoutParams.MATCH_PARENT);
			//params.addRule(RelativeLayout.CENTER_IN_PARENT);
			
			int buttonWidth = divWidth / buttonFragmentList.size();
			
			toolbarButtonView.setX((buttonWidth * i) + (buttonWidth/3));
			toolbarButtonView.setY(10);
			layout.addView(toolbarButtonView, params);
			
		}
	}

}
