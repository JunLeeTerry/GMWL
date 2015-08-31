package com.gmsz.domain;


/**
 * 
 * ������udp��ģ��
 * @author Jun Lee
 *
 */
public class ControllerPackage {
	private String type;//�������ͣ�e.g. ��ǽ������������ ��
	private Integer scene_id;//����id����ǽ����ʱ����
	public ControllerPackage(String type,Integer scene_id){
		this.type = type;
		this.scene_id = scene_id;
		
	}
	
	//�õ���װ֮�������֡����
	public String getUdpContent(){
		String content = "";
		switch (type){
			case "OPENSCREEN":
				content = "J 99 9999 43690 43690 43690 55439\n";
				break;
			case "CLOSESCREEN":
				content = "J 99 9999 21845 21845 21845 55438\n";
				break;
			case "CALLSCENE":
				content = "<call, "+scene_id.toString()+" >";
				break;
			default:
				break;
		}
		
		return content;
	}
}
