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
	
	//��ȡ�����豸�����б�
	public ArrayList getAllDevice(){
		return this.devices;
	}
	
	public void setDevices(ArrayList<Device> devices){
		this.devices = devices;
	}
	
	//��ȡ������ʾ�豸�б�
	public ArrayList<Device> getShowDevices(){
		ArrayList<Device> showDevices = new ArrayList<Device>();
		for(Device device:devices){
			if (device.getType() == 1){
				showDevices.add(device);
			}
		}
		return showDevices;
	}
	
	//��ȡ��������ͷ�����豸���б�
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
