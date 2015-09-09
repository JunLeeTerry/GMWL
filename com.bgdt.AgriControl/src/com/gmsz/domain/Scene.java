/*
 *******************************************************************************
 * All rights Reserved, Copyright (C) www.gmly.com 2015
 * FileName: Scene.java
 * Modify record:
 * NO. |     Date       |    Version      |    Name         |      Content
 * 1   | 2015年6月1日        |   1.0           |  GMSZ)LiangYan  | original version
 *******************************************************************************
 */
package com.gmsz.domain;

import java.util.ArrayList;
import java.util.List;

import com.gmsz.utils.MixcellaneousUtil;

/**
 * Class name:Scene Description: 场景
 * 
 * @author LiangYan
 */
public class Scene {
	private Integer id;
	/**
	 * 场景name
	 */
	private String name;
	/**
	 * 一个场景里的画面
	 */
	private List<Frame> frameList;

	//分屏数
	private int spilit;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getSpilit(){
		return this.spilit;
	}
	
	public void setSpilit(int spilit){
		this.spilit = spilit;
	}
	
	public List<Frame> getFrameList() {
		return frameList;
	}

	public void setFrameList(List<Frame> frameList) {
		this.frameList = frameList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Scene [id=" + id + ", name=" + name + ", frameList=" + frameList + "]";
	}
	
	/**
	 * 
	 * Description: 分屏处理数据
	 * 
	 * @param screan
	 *            几分屏 8 4 3 2
	 * @return
	 */
	public List<Frame> getVisibleSplitFrame() {
		List<Frame> list = new ArrayList<Frame>();
		
		switch (this.spilit) {
		case 8:		
			for(int i=0;i<this.spilit;i++){
				list.add(frameList.get(i));
			}
			break;
		case 4:
			list.add(frameList.get(0));
			list.add(frameList.get(1));
			list.add(frameList.get(2));
			list.add(frameList.get(3));
			frameList.get(4).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(5).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(6).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(7).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			break;
		case 3:
			list.add(frameList.get(0));
			list.add(frameList.get(1));
			list.add(frameList.get(3));
			frameList.get(2).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(4).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(5).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(6).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(7).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			break;
		case 2:
			list.add(frameList.get(0));
			list.add(frameList.get(2));
			frameList.get(1).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(3).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(4).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(5).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(6).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			frameList.get(7).setData(MixcellaneousUtil.getInstance().getDefaultUrl());
			break;
		default:
			break;
		}
		return list;
	}
	
}
