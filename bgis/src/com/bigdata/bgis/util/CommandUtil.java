package com.bigdata.bgis.util;

import com.bigdata.bgis.domain.Device;

public class CommandUtil {
	private static CommandUtil commandUtil;
	private Device device;
	
	private CommandUtil(){}
	
	public static CommandUtil getInstance(){
		if(commandUtil == null){
			commandUtil = new CommandUtil();
		}
		return commandUtil;
	}
	
	
	//�����豸��������
	public void sendCommand(Device device,String command){
				
	}
	
	//�������ģʽ
	public void callMode(String modeName){
		
	}
}
