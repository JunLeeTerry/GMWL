package com.gmsz.domain;

import java.util.ArrayList;

public class MatrixPort {
	private String name;//矩阵端口的名称
	private String id;//矩阵端口的id
	private String type;//端口的类型（输入、输出）
	private Integer position;//端口对应的位置 e.g. 1、2、3....
	private String content1;//端口对应的命令内容
	private String content2;//端口对应的命令内容
	public MatrixPort(){
			
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getId(){
		return this.id;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getType(){
		return this.type;
	}
	public void setPosition(Integer position){
		this.position = position;
	}
	public Integer getPosition(){
		return this.position;
	}
	public String getContent1(){
		return this.content1;
	}
	public void setContent1(String content){
		this.content1=content;
	}
	public String getContent2(){
		return this.content2;
	}
	public void setContent2(String content){
		this.content2=content;
	}
	
	public byte[] getContent1bytes(){
		return convertContent(this.content1);
	}
	
	public byte[] getContent2bytes(){
		return convertContent(this.content2);
	}
	
	//将命令内容转化成是byte数组类型
	private byte[] convertContent(String content){
		String[] strings = content.split(",");
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		int length = strings.length;
		for(String string :strings){
			string = string.trim();
			Integer hexint = Integer.parseInt(string, 16);
			bytes.add(hexint.byteValue());
		}
		byte[] bytearray = new byte[length];
		for(int i=0;i<bytearray.length;i++){
			bytearray[i] = bytes.get(i).byteValue();
		}
		
		return bytearray;
	}
}
