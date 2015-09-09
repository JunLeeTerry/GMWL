package com.gmsz.utils;
/**
 * 
 * Class name:CalFrameUtil
 * Description: 计算拖动坐标
 * @author LiangYan
 */
public class CalFrameUtil {
	
	/**
	 * 
	 * Description: 计算当前拖动到场景的第几个画面上
	 * @param size  几分屏
	 * @param x
	 * @param y
	 * @return
	 */
	public static int getCurrItem(int size,int x,int y){
		int i=-1;
		
		if(-610<y&&y<0){
			if(size==8){//8分屏
				int row=1;
				int column=1;
				if(y<-300){//第1行
					row=-2;
				}else{//第2行
					row=2;  
				}
				//x  -341-1688
				//
				if(x<213){
					column=1;
				}else if(213<=x&&x<853){
					column=2;
				}else if(853<=x&&x<1493){
					column=3;
				}else {
					column=4;
				}
				i=row+1+column;
				
			}else if(size==4){
				if(x<213){
					i=0;
				}else if(213<=x&&x<853){
					i=1;
				}else if(853<=x&&x<1493){
					i=2;
				}else {
					i=3;
				}
			}
			//没有处理3屏的情况，添加处理3屏的情况-terry
			else if(size == 3){
				if(x<426){
					i = 0;
				}
				else if(426<=x&&x<1279){
					i = 1;
				}
				else{
					i = 2;
				}
			}
			else {
				if(x<853){
					i=0;
				}else {
					i=1;
				}
			}
		}
		
		
		return i;
	}

}
