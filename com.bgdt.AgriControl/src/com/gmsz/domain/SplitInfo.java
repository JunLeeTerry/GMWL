/**
 * 
 */
package com.gmsz.domain;

/**
 * 记录分屏的主要信息
 * @author zhoufm
 *
 */
public class SplitInfo {
	private int id;
	private String name;//分屏的名称 e.g. 二分屏
	private int splitValue;//分屏的数量值 e.g. 2、3。。。。
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSplitValue() {
		return splitValue;
	}
	public void setSplitValue(int splitValue) {
		this.splitValue = splitValue;
	}
	

}
