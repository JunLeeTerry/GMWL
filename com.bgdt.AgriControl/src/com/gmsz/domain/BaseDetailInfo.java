package com.gmsz.domain;
/**
 * 
 * Class name:BaseDetailInfo
 * Description: ������ϸ��Ϣ
 */
public class BaseDetailInfo {
	private int  id;
	private String name;//��Դ����
	private String type;//���ͣ���ҳ������Ƶ
	private String url;//��Դurl
	private String data;//��Դ��Ԥ����ҳ�ĵ�ַ
	
	public BaseDetailInfo(){
		
	}
	public BaseDetailInfo(String name,String type,String url){
		this.name=name;
		this.type=type;
		this.url=url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	

}
