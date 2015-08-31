package com.gmsz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.gmsz.domain.Matrix;

/**
 * 矩阵工具类
 * 使用单例模式
 * @author JunLee
 *
 */
public class MatrixUtil {

	private static MatrixUtil matrixUtil;
	private ArrayList<Matrix> matrixes;

	private MatrixUtil() {

	}
	
	//获得矩阵工具类实例
	public static MatrixUtil getInstance() {
		if (matrixUtil == null)
			matrixUtil = new MatrixUtil();

		return matrixUtil;
	}

	//从配置文件中的到矩阵对象列表
	public ArrayList<Matrix> getMatrixInfos() throws XmlPullParserException,
			IOException {
		InputStream xml = this.getClass().getClassLoader()
				.getResourceAsStream("matrix.xml");
		ArrayList<Matrix> matrixList = new ArrayList<Matrix>();
		Matrix matrix = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if ("display".equals(pullParser.getName())) {
					String name = pullParser.getAttributeValue(0);
					String ip = pullParser.getAttributeValue(1);
					Integer screan = Integer.parseInt(pullParser
							.getAttributeValue(2));
					Integer input = Integer.parseInt(pullParser
							.getAttributeValue(3));
					Integer output = Integer.parseInt(pullParser
							.getAttributeValue(4));

					Log.e(">>>>>>>address>>>>>>>", name + ": " + ip);
					// matrix = new Matrix(name,ip,screan,input,output);
					matrix = new Matrix();
					matrix.setName(name);
					matrix.setIp(ip);
					matrix.setScrean(screan);
					matrix.setInputport(input);
					matrix.setOutputport(output);
				}

				break;

			case XmlPullParser.END_TAG:
				if ("display".equals(pullParser.getName())) {
					matrixList.add(matrix);
					matrix = null;
				}

				break;
			}
			event = pullParser.next();
		}
		return matrixList;
	}

	
	
	
}
