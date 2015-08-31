package com.gmsz.domain;
/**
 * 
 * Class name:Screan
 * Description: 屏幕与工控机对应关系
 * @author LiangYan
 */
public class IpcScrean {
	private String name;//工控机名称
	private int graphics;//工控机控制的屏幕
	private int sc;//android中控件中的哪块区域
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGraphics() {
		return graphics;
	}
	public void setGraphics(int graphics) {
		this.graphics = graphics;
	}
	public int getSc() {
		return sc;
	}
	public void setSc(int sc) {
		this.sc = sc;
	}
	@Override
	public String toString() {
		return "IpcScrean [name=" + name + ", graphics=" + graphics + ", sc=" + sc + "]";
	}
	
	

}
