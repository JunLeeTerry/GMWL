package com.gmsz.domain;

import java.util.List;
/**
 * 
 * Class name:BaseInfo
 * Description: 基地信息
 * 
 */
public class BaseInfo {
	private int  id;
	private String name;//基本信息name
	private List<BaseDetailInfo> detailList;//基地详细信息列表
	public BaseInfo(){
		
	}
	public BaseInfo(String name, List<BaseDetailInfo> detailList){
		this.name=name;
		this.detailList=detailList;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<BaseDetailInfo> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<BaseDetailInfo> detailList) {
		this.detailList = detailList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
