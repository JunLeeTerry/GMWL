package com.bigdata.bgis;

import java.util.HashMap;
import java.util.Map;

//设备类型和图标资源的对应关系
public class TypeImageMap {
	private static TypeImageMap typeImageMap;
	private Map<Integer,Integer> map;
	
	private TypeImageMap(){
		this.map = new HashMap<Integer,Integer>(){{
			put(1,R.drawable.showicon);
			put(2,R.drawable.cameraicon);
			put(3,R.drawable.projectoricon);
			put(4,R.drawable.curtainicon);
			put(5,R.drawable.lightsicon);
			put(6,R.drawable.micphoneicon);
			put(7,R.drawable.speakericon);
		}};
	}
	
	public static TypeImageMap instance(){
		if(typeImageMap == null){
			typeImageMap = new TypeImageMap();
		}
		return typeImageMap;
	}
	
	public int getResource(int typeid){
		int resourceid = this.map.get(typeid);
		return resourceid;
	}
	
}
