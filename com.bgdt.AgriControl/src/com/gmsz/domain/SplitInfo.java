/**
 * 
 */
package com.gmsz.domain;

/**
 * ��¼��������Ҫ��Ϣ
 * @author zhoufm
 *
 */
public class SplitInfo {
	private int id;
	private String name;//���������� e.g. ������
	private int splitValue;//����������ֵ e.g. 2��3��������
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
