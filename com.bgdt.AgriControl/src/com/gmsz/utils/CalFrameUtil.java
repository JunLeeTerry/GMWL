package com.gmsz.utils;
/**
 * 
 * Class name:CalFrameUtil
 * Description: �����϶�����
 * @author LiangYan
 */
public class CalFrameUtil {
	
	/**
	 * 
	 * Description: ���㵱ǰ�϶��������ĵڼ���������
	 * @param size  ������
	 * @param x
	 * @param y
	 * @return
	 */
	public static int getCurrItem(int size,int x,int y){
		int i=-1;
		
		if(-610<y&&y<0){
			if(size==8){//8����
				int row=1;
				int column=1;
				if(y<-300){//��1��
					row=-2;
				}else{//��2��
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
			//û�д���3�����������Ӵ���3�������-terry
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
