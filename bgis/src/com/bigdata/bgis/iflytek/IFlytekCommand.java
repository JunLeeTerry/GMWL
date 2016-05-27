package com.bigdata.bgis.iflytek;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;


//Ѷ��������ز���
public class IFlytekCommand {
	private static final String TAG = "bgis/IFlytekCommand";
	
	private static IFlytekCommand iflytekCommandMap;
	private HashMap commandMap = new LinkedHashMap<String,String>();
	
	private IFlytekCommand(){}
	
	public static IFlytekCommand getInstance(){
		if(iflytekCommandMap == null){
			iflytekCommandMap = new IFlytekCommand();
		}
		return iflytekCommandMap;
	}
	
	public void execCommand(String command){
		init();
		
		Iterator iterator = commandMap.keySet().iterator();
		while(iterator.hasNext()){
			String commandName = (String)iterator.next();
			String regex = (String)commandMap.get(commandName);
			
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(command);
			
			boolean isMatched = m.matches();
			if(isMatched){
				Log.d(TAG,m.group(0));
				Log.d(TAG,m.group(1));
				Log.d(TAG,m.group(2));
				Log.d(TAG,m.group(3));
			}
			
		}
	}
	
	private void init(){
		commandMap.put("�����豸","(.*(��|��).*��(���ӻ�|����ͷ|ͶӰ��|����|����).*)|(.*��(���ӻ�|����ͷ|ͶӰ��|����|����).*(��|��).*)");
	} 
	
}
