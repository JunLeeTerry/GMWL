package com.gmsz.domain;


/**
 * 
 * 控制器udp包模型
 * @author Jun Lee
 *
 */
public class ControllerPackage {
	private String type;//操作类型（e.g. 上墙，开屏，关屏 ）
	private Integer scene_id;//场景id，上墙操作时调用
	public ControllerPackage(String type,Integer scene_id){
		this.type = type;
		this.scene_id = scene_id;
		
	}
	
	//得到组装之后的数据帧内容
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
