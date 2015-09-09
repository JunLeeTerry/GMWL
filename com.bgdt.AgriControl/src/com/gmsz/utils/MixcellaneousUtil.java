package com.gmsz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.gmsz.domain.SplitInfo;

import android.util.Xml;

public class MixcellaneousUtil {
	private static MixcellaneousUtil mixcellaneousUtil;

	private String matrixName;
	private String matrixIp;
	private Integer matrixPort;
	private String scontrollerName;
	private String scontrollerIp;
	private Integer scontrollerPort;
	private String defaultUrl;
	private String hdmiDefaultUrl;
	
	private MixcellaneousUtil() {
		try {
			parseXML();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MixcellaneousUtil getInstance() {
		if (mixcellaneousUtil == null) {
			mixcellaneousUtil = new MixcellaneousUtil();
		}
		return mixcellaneousUtil;
	}

	public String getScontrollerName(){
		return this.scontrollerName;
	}
	
	public String getScontrollerIp(){
		return this.scontrollerIp;
	}
	
	public Integer getScontrollerPort(){
		return this.scontrollerPort;
	}
	
	public String getMatrixName() {
		return this.matrixName;
	}

	public String getMatrixIp() {
		return this.matrixIp;
	}

	public Integer getMatrixPort() {
		return this.matrixPort;
	}

	public String getDefaultUrl() {
		return this.defaultUrl;
	}
	
	public String getHDMIDefaultUrl(){
		return this.hdmiDefaultUrl;
	}

	private void parseXML() throws XmlPullParserException, IOException {
		InputStream xml = this.getClass().getClassLoader()
				.getResourceAsStream("miscellaneous.xml");
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");// 为Pull解析器设置要解析的XML数据
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if ("matrix".equals(pullParser.getName())) {
					this.matrixName = pullParser.getAttributeValue(0);
					this.matrixIp = pullParser.getAttributeValue(1);
					this.matrixPort = Integer.parseInt(pullParser
							.getAttributeValue(2));
				}

				else if("scontroller".equals(pullParser.getName())) {
					this.scontrollerName = pullParser.getAttributeValue(0);
					this.scontrollerIp = pullParser.getAttributeValue(1);
					this.scontrollerPort = Integer.parseInt(pullParser
							.getAttributeValue(2));
				}
				
				else if ("defaulturl".equals(pullParser.getName())) {
					this.defaultUrl = pullParser.nextText();
				}
				
				else if("hdmivgadefaulturl".equals(pullParser.getName())){
					this.hdmiDefaultUrl = pullParser.nextText();
				}
				
				
				
				break;

			case XmlPullParser.END_TAG:
				break;
			}
			event = pullParser.next();
		}
	}
}
