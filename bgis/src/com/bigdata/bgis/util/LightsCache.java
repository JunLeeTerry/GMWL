package com.bigdata.bgis.util;

import java.util.HashMap;

import com.bigdata.bgis.domain.Device;

public class LightsCache {
	private static LightsCache lightsCache;
	private HashMap<Device,Boolean> cache = new HashMap();
	
	private LightsCache(){}
	
	public static LightsCache getInstance(){
		if(lightsCache == null){
			lightsCache = new LightsCache();
		}
		return lightsCache;
	}

	public void setStatus(Device device,Boolean bool){
		cache.put(device,bool);
	}
	
	public Boolean getStatus(Device device){
		if (cache.get(device) == null){
			return false;
		}
		return cache.get(device);
		
	}
	
}
