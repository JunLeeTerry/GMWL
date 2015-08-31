package com.gmsz.domain;

import android.view.View;

import com.gmsz.view.VlcVideoView;

/**
 * 
 * Class name:Frame Description: 画面
 *画面的数据模型类
 * 
 * @author LiangYan
 */
public class Frame {
	private Integer id;
	/**
	 * 类型 web图片, video视频 ,HDMI-VGA
	 */
	private String type;
	private String url;//资源地址
	private String data;//资源预览网页地址
	private String name;
	
	private VlcVideoView videoView;
	private View convertView;
	private boolean change=true;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Frame [id=" + id + ", type=" + type + ", url=" + url + "]";
	}

	public VlcVideoView getVideoView() {
		return videoView;
	}

	public void setVideoView(VlcVideoView videoView) {
		this.videoView = videoView;
	}

	//开始播放视频
	public void startPlay() {
		if (videoView != null) {
			videoView.createPlayer();
		}
	}

	//停止播放视频
	public void stopPlay() {
		if (videoView != null) {
			videoView.releasePlayer();
			videoView=null;
		}
	}

	public View getConvertView() {
		return convertView;
	}

	public void setConvertView(View convertView) {
		this.convertView = convertView;
	}
	
	//判断画面有没有改变
	public boolean isChange() {
		return change;
	}

	//主动设置画面有没有改变
	public void setChange(boolean change) {
		this.change = change;
	}

	

}
