package com.gmsz.domain;



/**
 * 
 * 矩阵控制数据的数据模型
 * 
 * @author Jun Lee
 *
 */
public class Matrix {
	private String name;//工控机名称
	private String ip;//工控机ip地址
	private Integer screan;//工控机控制的屏幕
	private Integer inputport;//工控机在矩阵中的输入端口
	private Integer outputport;//工控机在矩阵中的输出端口
	
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
