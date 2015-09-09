package com.gmsz.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.gmsz.domain.Frame;
import com.gmsz.domain.IpcScrean;
import com.gmsz.domain.Matrix;

/**
 * 
 * Class name:IpcScreanUtil Description: 屏幕与工控机
 * 
 * @author Jun Lee
 */
public class IpcScreanUtil {
	private static final List<IpcScrean> IPC_SCREANS_LIST = new ArrayList<IpcScrean>();

	private static ArrayList<Matrix> matrixList = null;
	//private static IpcScrean ipcScrean;
	
	private static ArrayList<Matrix> getMatrixList(){
		try {
			matrixList = MatrixUtil.getInstance().getMatrixInfos();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matrixList;
	}
	//set ipc name like I1-1 I1-2 I2-1
	static {
		if (IPC_SCREANS_LIST.size() < 1) {// 初始化最初八个工控机与屏幕的关系
			int sc=1;//屏幕
			for (int i = 0; i < 8; i++) {
					IpcScrean ipcScrean = new IpcScrean();
					//ipcScrean.setName("I" + (i%4+1));
					//ipcScrean.setGraphics(i/4 +1);
					ipcScrean.setSc(sc);
					int graNum = i/4+1;
					ipcScrean.setName("I" + (i%4+1) + "-" + graNum);
					//get graphics from config file
					for(Matrix matrix : getMatrixList()){
						if(matrix.getName().equals(ipcScrean.getName())){
							ipcScrean.setGraphics(matrix.getScrean());
						}
					}
					
					IPC_SCREANS_LIST.add(ipcScrean);
					sc++;
					Log.e("IpcScreanUtil",ipcScrean.getName()+":"+ipcScrean.getGraphics());
			}

		}
	}

	/**
	 * 
	 * Description: 取得屏幕与工控机关系
	 * 
	 * @param screan
	 *            几分屏 8 4 3 2
	 * @return
	 */
	public static List<IpcScrean> getScrean(int screan) {
		List<IpcScrean> list = new ArrayList<IpcScrean>();
		switch (screan) {
		case 8:
			list.addAll(IPC_SCREANS_LIST);
			break;
		case 4:
			for(int i=0;i<4;i++){
				list.add(IPC_SCREANS_LIST.get(i));
			}
			break;
		case 3:
			list.add(IPC_SCREANS_LIST.get(0));
			list.add(IPC_SCREANS_LIST.get(1));
			list.add(IPC_SCREANS_LIST.get(3));
			break;
		case 2:
			list.add(IPC_SCREANS_LIST.get(0));
			list.add(IPC_SCREANS_LIST.get(2));
			break;
		default:
			break;
		}
		return list;
	}
}
