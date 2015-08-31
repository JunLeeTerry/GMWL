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
	private String tableInsertDefaultUrl;

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

	public String getMatrixName() {
		return this.matrixName;
	}

	public String getMatrixIp() {
		return this.matrixIp;
	}

	public Integer getMatrixPort() {
		return this.matrixPort;
	}

	public String getTableInsertDefaultUrl() {
		return this.tableInsertDefaultUrl;
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

				else if ("defaulturl".equals(pullParser.getName())) {
					this.tableInsertDefaultUrl = pullParser.nextText();
				}

				break;

			case XmlPullParser.END_TAG:
				break;
			}
			event = pullParser.next();
		}
	}
}
