package com.gmsz.domain;


 /**
  * Description:封装工控机对应的ip地址的数据模型类
  * name成员变量为工控机名称
  * ip成员变量为工控机的ip地址
  * 
 * @author Jun Lee
 *
 */
public class IpcIp {
	private String ip;
	private String name;

	public void setIp(String ip){
		this.ip = ip;
		
	}
	public void setName(String name ){
		this.name = name;
	}

	public String getIp(){
		return this.ip;
	}
	public String getName(){
		return this.name;
	}
	@Override
	public String toString(){
		return this.name+":  "+this.ip;
	}
	
	//通过对象的name变量作为参数，返回对象的ip
	public String getIpByName(String name){
		if(name.equals(this.name)){
			return this.ip;
		}
		else
			return null;
	}
}
