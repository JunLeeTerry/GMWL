package com.gmsz.domain;
/**
 * 
 * Class name:IPC���ݰ���ģ����
 * Description: udp-���ػ�
 */
public class IpcPackage {
	private String name;//���ػ�����
	private int screen;//���ػ����Ƶ���Ļ
	private String url;//��Դ��λ��
	private String type;//��Դ������
	
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
	 * Description: ��д ת��Ϊjson��ʽ����װ��udp���ݰ�����֡������
	 * @return
	 */
	@Override
	public String toString() {
		return "{\"Name\":\"" + name + "\",\"Screen\":\"" + screen+ "\",\"Type\":\"" + type + "\",\"Url\":\"" + url  + "\"}";
	}
	
	
	
	

}
