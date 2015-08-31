package com.gmsz.domain;


 /**
  * Description:��װ���ػ���Ӧ��ip��ַ������ģ����
  * name��Ա����Ϊ���ػ�����
  * ip��Ա����Ϊ���ػ���ip��ַ
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
	
	//ͨ�������name������Ϊ���������ض����ip
	public String getIpByName(String name){
		if(name.equals(this.name)){
			return this.ip;
		}
		else
			return null;
	}
}
