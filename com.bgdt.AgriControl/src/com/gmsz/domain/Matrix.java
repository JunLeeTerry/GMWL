package com.gmsz.domain;



/**
 * 
 * ����������ݵ�����ģ��
 * 
 * @author Jun Lee
 *
 */
public class Matrix {
	private String name;//���ػ�����
	private String ip;//���ػ�ip��ַ
	private Integer screan;//���ػ����Ƶ���Ļ
	private Integer inputport;//���ػ��ھ����е�����˿�
	private Integer outputport;//���ػ��ھ����е�����˿�
	
	public Matrix(){

	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getIp(){
		return this.ip;
	}
	
	public void setIp(String ip){
		this.ip = ip;
	}
	
	public Integer getScrean(){
		return this.screan;
	}
	
	public void setScrean(Integer screan){
		this.screan = screan;
	}
	
	public Integer getOutputport(){
		return this.outputport;
	}
	
	public void setOutputport(Integer outputport){
		this.outputport = outputport;
	}
	
	public Integer getInputport(){
		return this.inputport;
	}
	
	public void setInputport(Integer input){
		this.inputport = input;
	}
	
}
