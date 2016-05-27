package com.bigdata.bgis.util;

import java.util.HashMap;

import com.bigdata.bgis.domain.Device;

public class SpeakerCache {
	private static final String TAG = "bgis/SpeakerCache";
	private static SpeakerCache speakerCache;
	private HashMap<Device, Float> map = new HashMap();

	private SpeakerCache() {

	}

	public static SpeakerCache getInstance() {
		if (speakerCache == null) {
			speakerCache = new SpeakerCache();
		}
		return speakerCache;
	}

	// ���������뵽���浱��
	public void addVolum(Device device, Float volum) {
		map.put(device, volum);
	}

	// ��ȡ�����е������������������Ϊ0
	public Float getVolum(Device device) {
		if (map.get(device) == null) {
			return 0f;
		}
		return map.get(device);
	}
}
