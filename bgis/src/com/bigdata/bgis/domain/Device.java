package com.bigdata.bgis.domain;

public class Device {
	private int id;
	private String name;
	private int type;
	
	
	public Device(int id,int type,String name){
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public int getType(){
		return this.type;
	}
}
