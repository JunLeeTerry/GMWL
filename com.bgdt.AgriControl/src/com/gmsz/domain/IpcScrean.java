package com.gmsz.domain;
/**
 * 
 * Class name:Screan
 * Description: ��Ļ�빤�ػ���Ӧ��ϵ
 * @author LiangYan
 */
public class IpcScrean {
	private String name;//���ػ�����
	private int graphics;//���ػ����Ƶ���Ļ
	private int sc;//android�пؼ��е��Ŀ�����
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGraphics() {
		return graphics;
	}
	public void setGraphics(int graphics) {
		this.graphics = graphics;
	}
	public int getSc() {
		return sc;
	}
	public void setSc(int sc) {
		this.sc = sc;
	}
	@Override
	public String toString() {
		return "IpcScrean [name=" + name + ", graphics=" + graphics + ", sc=" + sc + "]";
	}
	
	

}
