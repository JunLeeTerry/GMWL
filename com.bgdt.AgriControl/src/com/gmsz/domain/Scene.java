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

import java.util.List;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	

}
