package com.gmsz.domain;
/**
 * 
 * Class name:IPC数据包的模型类
 * Description: udp-工控机
 */
public class IpcPackage {
	private String name;//工控机名称
	private int screen;//工控机控制的屏幕
	private String url;//资源的位置
	private String type;//资源的类型
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getScreen() {
		return screen;
	}
	public void setScreen(int screen) {
		this.screen = screen;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Description: 重写 转化为json格式，组装成udp数据包数据帧的内容
	 * @return
	 */
	@Override
	public String toString() {
		return "{\"Name\":\"" + name + "\",\"Screen\":\"" + screen+ "\",\"Type\":\"" + type + "\",\"Url\":\"" + url  + "\"}";
	}
	
	
	
	

}
