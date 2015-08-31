package com.gmsz.domain;

import android.view.View;

import com.gmsz.view.VlcVideoView;

/**
 * 
 * Class name:Frame Description: ����
 *���������ģ����
 * 
 * @author LiangYan
 */
public class Frame {
	private Integer id;
	/**
	 * ���� webͼƬ, video��Ƶ ,HDMI-VGA
	 */
	private String type;
	private String url;//��Դ��ַ
	private String data;//��ԴԤ����ҳ��ַ
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

	//��ʼ������Ƶ
	public void startPlay() {
		if (videoView != null) {
			videoView.createPlayer();
		}
	}

	//ֹͣ������Ƶ
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
	
	//�жϻ�����û�иı�
	public boolean isChange() {
		return change;
	}

	//�������û�����û�иı�
	public void setChange(boolean change) {
		this.change = change;
	}

	

}
