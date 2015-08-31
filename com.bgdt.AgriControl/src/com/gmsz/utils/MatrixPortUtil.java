package com.gmsz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.gmsz.domain.Matrix;
import com.gmsz.domain.MatrixPort;

/**
 * 矩阵端口的工具类
 * 使用单例模式
*/
public class MatrixPortUtil {
	private static MatrixPortUtil matrixPortUtil;

	private MatrixPortUtil() {

	}

	//获取类的实例
	public static MatrixPortUtil getInstance() {
		if (matrixPortUtil == null)
			matrixPortUtil = new MatrixPortUtil();
		return matrixPortUtil;
	}

	//从配置文件中的到矩阵端口对象的列表
	public ArrayList<MatrixPort> getMatrixPorts()
			throws XmlPullParserException, IOException {
		InputStream xml = this.getClass().getClassLoader()
				.getResourceAsStream("matrixport.xml");
		ArrayList<MatrixPort> matrixportList = new ArrayList<MatrixPort>();
		MatrixPort matrixport = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if ("port".equals(pullParser.getName())) {
					String name = pullParser.getAttributeValue(0);
					String id = pullParser.getAttributeValue(1);
					String type = pullParser.getAttributeValue(2);
					Integer position = Integer.parseInt(pullParser
							.getAttributeValue(3));

					// Log.e("", name + ": " + ip);
					// matrix = new Matrix(name,ip,screan,input,output);
					matrixport = new MatrixPort();
					matrixport.setName(name);
					matrixport.setId(id);
					matrixport.setType(type);
					matrixport.setPosition(position);
				}
				if ("content1".equals(pullParser.getName())) {
					String content1 = pullParser.nextText();
					matrixport.setContent1(content1);
				}
				if ("content2".equals(pullParser.getName())) {
					String content2 = pullParser.nextText();
					matrixport.setContent2(content2);
				}

				break;

			case XmlPullParser.END_TAG:
				if ("port".equals(pullParser.getName())) {
					matrixportList.add(matrixport);
					matrixport = null;
				}

				break;
			}
			event = pullParser.next();
		}
		return matrixportList;
	}
	
	//通过矩阵中的类型和位置得到具体的MatrixPort对象
	public MatrixPort getMarixPort(String type, int position) {
		ArrayList<MatrixPort> matrixportlist = null;
		try {
			matrixportlist = getMatrixPorts();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//遍历矩阵端口列表，得到端口位置匹配的矩阵端口对象
		for (MatrixPort matrixport : matrixportlist) {
			if (matrixport.getType().equals(type)
					&& matrixport.getPosition() == position) {
				return matrixport;
			}
		}
		//如何遍历匹配不到的话，返回null
		return null;
	}
	
	//将矩阵对象中的获得byte数组的方法，再次进行一次封装方便进行调用
	public byte[][] getMarixContentbytes(int inputposition,int outputposition){
		byte[][] bytes = new byte[4][];
		bytes[0] = getMarixPort("input",inputposition).getContent1bytes();
		bytes[1] = getMarixPort("input",inputposition).getContent2bytes();
		bytes[2] = getMarixPort("output",outputposition).getContent1bytes();
		bytes[3] = getMarixPort("output",outputposition).getContent2bytes();
		return bytes;
	}
}
