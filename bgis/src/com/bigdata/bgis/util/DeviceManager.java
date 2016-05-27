package com.bigdata.bgis.util;

import java.util.ArrayList;

import com.bigdata.bgis.domain.Device;

public class DeviceManager {
	private static DeviceManager deviceManager;
	private DeviceManager(){}
	
	private ArrayList<Device> devices;
	
	public static DeviceManager getInstance(){
		if(deviceManager == null){
			deviceManager = new DeviceManager();
		}
		return deviceManager;
	}
	
	//获取所有设备对象列表
	public ArrayList getAllDevice(){
		return this.devices;
	}
	
	public void setDevices(ArrayList<Device> devices){
		this.devices = devices;
	}
	
	//获取所有显示设备列表
	public ArrayList<Device> getShowDevices(){
		ArrayList<Device> showDevices = new ArrayList<Device>();
		for(Device device:devices){
			if (device.getType() == 1){
				showDevices.add(device);
			}
		}
		return showDevices;
	}
	
	//获取所有摄像头类型设备的列表
	public ArrayList<Device> getCameraDevices(){
		ArrayList<Device> cameraDevices = new ArrayList<Device>();
		for(Device device:devices){
			if (device.getType() == 2){
				cameraDevices.add(device);
			}
		}
		return cameraDevices;
	}
}
