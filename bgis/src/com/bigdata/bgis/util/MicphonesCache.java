package com.bigdata.bgis.util;

import java.util.HashMap;

import com.bigdata.bgis.domain.Device;

//缓存seekbar的progress取消seekbar的lazyload
public class MicphonesCache {
	
	private static MicphonesCache micphonesCache;
	
	private HashMap<Device,Integer> cache = new HashMap();
	private int progress;
	
	private MicphonesCache(){}
	
	public static MicphonesCache getInstance(){
		if(micphonesCache == null){
			micphonesCache = new MicphonesCache();
		}
		
		return micphonesCache;
	}
	
	public void addProgress(Device device,Integer progress){
		cache.put(device , progress);
	}
	
	public Integer getProgress(Device device){
		if(cache.get(device) == null ){
			return 0;
		}
		return cache.get(device);
	}
	
	public HashMap<Device,Integer> getMap(){
		return this.cache;
	}
}
