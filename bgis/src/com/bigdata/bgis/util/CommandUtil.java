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
	
	
	//发送设备控制命令
	public void sendCommand(Device device,String command){
				
	}
	
	//请求会议模式
	public void callMode(String modeName){
		
	}
}
