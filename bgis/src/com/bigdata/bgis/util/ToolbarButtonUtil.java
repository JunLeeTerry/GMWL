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

//主要对工具栏按钮的显示进行一系列的操作
public class ToolbarButtonUtil {
	private final static String TAG = "bgis/ToolbarButtonUtil";

	// private ArrayList<ToolbarButtonView> buttonviews;//记录素有按钮对象
	private ArrayList<ButtonFragmentMap> buttonFragmentList = new ArrayList();// 按钮和fragment的对应关系

	private CtrlerFactory factory;// 创建fragment的工厂类

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

	// 在工具栏显示按钮，将视图添加到布局控件中
	public void showToolbarButton(Context context, ViewGroup layout) {
		ArrayList<Device> devicelist = DeviceManager.getInstance()
				.getAllDevice();
		int buttonnum = devicelist.size();

		ArrayList<Device> lightsList = new ArrayList<Device>();// 记录所有灯光设备
		ArrayList<Device> micphonesList = new ArrayList<Device>();// 记录所有麦克风类型设备

		// 添加处理各种累设备，将他们的按钮和对应的fragment对应起来
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
		// 添加电灯控制按钮
		if (lightsList.size() > 0) {
			ToolbarButtonView lightsButton = setLightToolbarButton(context);
			// layout.addView(lightsButton);
			// LightsFragment lightsFragment = new LightsFragment(lightsList);
			LightsFragment lightsFragment = (LightsFragment) factory
					.createConpoFragment(lightsList, 5);
			buttonFragmentList.add(new ButtonFragmentMap(lightsButton,
					lightsFragment));
		}
		// 添加声音控制按钮
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

	// 将fragment和对应的按钮绑定起来
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

	// 创建Fragment 和button添加到对应关系列表中
	private ToolbarButtonView setToolbarButton(Context context, Device device) {
		ToolbarButtonView button = new ToolbarButtonView(context);
		button.setName(device.getName());
		button.setImage(TypeImageMap.instance().getResource(device.getType()));
		return button;
	}

	// 创建电灯的fragment和button添加到对应的管系列表中
	private ToolbarButtonView setLightToolbarButton(Context context) {
		ToolbarButtonView button = new ToolbarButtonView(context);
		button.setName("电灯");
		button.setImage(TypeImageMap.instance().getResource(5));
		return button;
	}

	// 创建音频的fragment和button添加到对应的管理列表中
	private ToolbarButtonView setMicphoneToolbarButton(Context context) {
		ToolbarButtonView button = new ToolbarButtonView(context);
		button.setName("麦克风");
		button.setImage(TypeImageMap.instance().getResource(6));
		return button;
	}

	/*
	 * param:context、layout、对应的按钮实例，按钮的个数、目前添加的是第几个按钮
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
