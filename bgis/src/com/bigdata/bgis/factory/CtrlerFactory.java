package com.bigdata.bgis.factory;

import java.util.ArrayList;

import com.bigdata.bgis.domain.Device;
import com.bigdata.bgis.fragment.MicphonesFragment;
import com.bigdata.bgis.fragment.CameraFragment;
import com.bigdata.bgis.fragment.CurtainFragment;
import com.bigdata.bgis.fragment.LightsFragment;
import com.bigdata.bgis.fragment.ProjectorFragment;
import com.bigdata.bgis.fragment.SpeakerFragment;

import android.support.v4.app.Fragment;

public class CtrlerFactory {

	public CtrlerFactory() {
	}

	public Fragment createFragment(Device device) {
		switch (device.getType()) {
		case 2:// 2 <=> 摄像头
			return new CameraFragment(device);
		case 3:// 3 <=> 投影机
			return new ProjectorFragment(device);
		case 4:// 4 <=> 窗帘
			return new CurtainFragment(device);
		case 7://7 <=> 喇叭
			return new SpeakerFragment(device);
		}
			
		return null;
	}

	public Fragment createConpoFragment(ArrayList<Device> devices, int type) {
		switch (type) {
		case 5:
			return new LightsFragment(devices);
		case 6:
			return new MicphonesFragment(devices);
		}
		return null;
	}
}
