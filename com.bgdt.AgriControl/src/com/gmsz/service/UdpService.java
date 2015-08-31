/*
 *******************************************************************************
 * All rights Reserved, Copyright (C) www.gmly.com 2015
 * FileName: DemoService.java
 * Modify record:
 * NO. |     Date       |    Version      |    Name         |      Content
 * 1   | 2015年6月1日        |   1.0           |  GMSZ)LiangYan  | original version
 *******************************************************************************
 */
package com.gmsz.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.gmsz.domain.BaseDetailInfo;
import com.gmsz.domain.BaseInfo;
import com.gmsz.domain.ControllerPackage;
import com.gmsz.domain.Frame;
import com.gmsz.domain.IpcIp;
import com.gmsz.domain.IpcPackage;
import com.gmsz.domain.IpcScrean;
import com.gmsz.domain.Matrix;
import com.gmsz.domain.Scene;
import com.gmsz.domain.SplitInfo;
import com.gmsz.utils.IpcScreanUtil;
import com.gmsz.utils.MatrixPortUtil;
import com.gmsz.utils.MatrixUtil;
import com.gmsz.utils.MixcellaneousUtil;
import com.gmsz.utils.ResetPackageUtil;

/**
 * Class name:DemoService Description: service相关
 * 
 * @author LiangYan
 */
public class UdpService {

	private static final String TAG = "DemoService";

	private String message;
	private int port;
	private IpcScrean ipcScrean;
	private String host;

	
	/**
	 * 获取场景数据
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public List<Scene> getScenes() throws Exception {
		// 从文件中读取场景的信息
		InputStream xml = this.getClass().getClassLoader()
				.getResourceAsStream("scene.xml");
		List<Scene> scenes = null;
		Scene scene = null;
		Frame frame = null;
		List<Frame> frames = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");// 为Pull解析器设置要解析的XML数据
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				scenes = new ArrayList<Scene>();
				frames = new ArrayList<Frame>();
				break;
			case XmlPullParser.START_TAG:
				if ("scene".equals(pullParser.getName())) {
					int id = Integer.parseInt(pullParser.getAttributeValue(0));
					String name = pullParser.getAttributeValue(1);
					scene = new Scene();
					scene.setId(id);
					scene.setName(name);
				}
				if ("frame".equals(pullParser.getName())) {
					int id = Integer.parseInt(pullParser.getAttributeValue(0));
					frame = new Frame();
					frame.setId(id);
				}
				if ("type".equals(pullParser.getName())) {
					String type = pullParser.nextText();
					frame.setType(type);
				}
				if ("url".equals(pullParser.getName())) {
					String url = pullParser.nextText();
					frame.setUrl(url);
				}
				if ("data".equals(pullParser.getName())) {
					String data = pullParser.nextText();
					frame.setData(data);
				}
				if ("name".equals(pullParser.getName())) {
					String name = pullParser.nextText();
					frame.setName(name);
				}
				break;

			case XmlPullParser.END_TAG:
				if ("scene".equals(pullParser.getName())) {
					scene.setFrameList(frames);
					scenes.add(scene);
					scene = null;
					frames = new ArrayList<Frame>();
				}
				if ("frame".equals(pullParser.getName())) {
					frames.add(frame);
					frame = null;
				}
				break;
			}
			event = pullParser.next();
		}
		Log.i(TAG, scenes.get(0).getFrameList().get(0).toString());
		return scenes;
	}

	// 获取工控机name对应的ip地址
	public ArrayList<IpcIp> getIpcIp() throws XmlPullParserException,
			IOException {
		InputStream xml = this.getClass().getClassLoader()
				.getResourceAsStream("IpcIpConfig.xml");
		ArrayList<IpcIp> ipcipList = new ArrayList<IpcIp>();
		IpcIp ipcIp = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if ("address".equals(pullParser.getName())) {
					String name = pullParser.getAttributeValue(0);
					String ip = pullParser.getAttributeValue(1);
					Log.e(">>>>>>>address>>>>>>>", name + ": " + ip);
					ipcIp = new IpcIp();
					ipcIp.setIp(ip);
					ipcIp.setName(name);

				}

				break;

			case XmlPullParser.END_TAG:
				if ("address".equals(pullParser.getName())) {
					ipcipList.add(ipcIp);
					ipcIp = null;
				}

				break;
			}
			event = pullParser.next();
		}
		return ipcipList;
	}

	// 获得矩阵的对应关系
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
					matrix = new Matrix();
					matrix.setName(name);
					matrix.setIp(ip);
					matrix.setScrean(screan);
					matrix.setInputport(input);
					matrix.setOutputport(output);
				}

				break;

			case XmlPullParser.END_TAG:
				if ("address".equals(pullParser.getName())) {
					matrixList.add(matrix);
					matrix = null;
				}

				break;
			}
			event = pullParser.next();
		}
		return matrixList;
	}

	/**
	 * 获取分屏列表信息
	 * 
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public List<SplitInfo> getSplitInfos() throws XmlPullParserException,
			IOException {

		InputStream xml = this.getClass().getClassLoader()
				.getResourceAsStream("splits.xml");
		List<SplitInfo> splitInfoList = null;
		SplitInfo splitInfo = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");// 为Pull解析器设置要解析的XML数据
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				splitInfoList = new ArrayList<SplitInfo>();
				break;
			case XmlPullParser.START_TAG:
				if ("split".equals(pullParser.getName())) {
					int id = Integer.parseInt(pullParser.getAttributeValue(0));
					String name = pullParser.getAttributeValue(1);
					int splitValue = Integer.parseInt(pullParser
							.getAttributeValue(2));
					splitInfo = new SplitInfo();
					splitInfo.setId(id);
					splitInfo.setName(name);
					splitInfo.setSplitValue(splitValue);
				}

				break;

			case XmlPullParser.END_TAG:
				if ("split".equals(pullParser.getName())) {
					splitInfoList.add(splitInfo);
					splitInfo = null;

				}

				break;
			}
			event = pullParser.next();
		}
		return splitInfoList;

	}

	/**
	 * 获取场景数据
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public List<BaseInfo> getBaseInfos() throws Exception {
		InputStream xml = this.getClass().getClassLoader()
				.getResourceAsStream("baseinfo.xml");
		List<BaseInfo> baseInfos = null;
		BaseInfo baseInfo = null;
		BaseDetailInfo baseDetailInfo = null;
		List<BaseDetailInfo> detailList = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");// 为Pull解析器设置要解析的XML数据
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				baseInfos = new ArrayList<BaseInfo>();
				detailList = new ArrayList<BaseDetailInfo>();
				break;
			case XmlPullParser.START_TAG:
				if ("info".equals(pullParser.getName())) {
					int id = Integer.parseInt(pullParser.getAttributeValue(0));
					String name = pullParser.getAttributeValue(1);
					baseInfo = new BaseInfo();
					baseInfo.setId(id);
					baseInfo.setName(name);
				}
				if ("detail".equals(pullParser.getName())) {
					int id = Integer.parseInt(pullParser.getAttributeValue(0));
					baseDetailInfo = new BaseDetailInfo();
					baseDetailInfo.setId(id);
				}
				if ("type".equals(pullParser.getName())) {
					String type = pullParser.nextText();
					baseDetailInfo.setType(type);
				}
				if ("url".equals(pullParser.getName())) {
					String url = pullParser.nextText();
					baseDetailInfo.setUrl(url);
				}
				if ("name".equals(pullParser.getName())) {
					String name = pullParser.nextText();
					baseDetailInfo.setName(name);
				}
				if ("data".equals(pullParser.getName())) {
					String data = pullParser.nextText();
					baseDetailInfo.setData(data);
				}
				break;

			case XmlPullParser.END_TAG:
				if ("info".equals(pullParser.getName())) {
					baseInfo.setDetailList(detailList);
					baseInfos.add(baseInfo);
					baseInfo = null;
					detailList = new ArrayList<BaseDetailInfo>();
				}
				if ("detail".equals(pullParser.getName())) {
					detailList.add(baseDetailInfo);
					baseDetailInfo = null;
				}
				break;
			}
			event = pullParser.next();
		}
		return baseInfos;
	}

	/**
	 * 
	 * Description: 上墙操作
	 * 
	 * @param scene
	 */
	public void onWall(Scene scene) {
		List<Frame> frames = scene.getFrameList();

		// 取得屏幕与工控机关系
		List<IpcScrean> ipcScreans = IpcScreanUtil.getScrean(frames.size());

		for (IpcScrean ipcScrean : ipcScreans) {
			Log.i(TAG, ipcScrean.toString());
		}

		/**
		 * 发送矩阵重置的数据包
		 * 通常使用对矩阵中数据的清除作用
		 */
		sendResetPackage();
		
		// 发送数据包给大屏控制器
		sendUdp("CALLSCENE");
		// Udp广播让工控机播放视频或网页
		udpToIpc(frames, ipcScreans);

	}

	/**
	 * 
	 * Description: UDP广播给工控机执行命令
	 * 
	 * @param frame
	 * @param ipcScrean
	 */
	private void udpToIpc(final List<Frame> frames,
			final List<IpcScrean> ipcScreans) {
		new Thread(new Runnable() {
			public void run() {
				Log.i(">>>>>", "" + frames.size());
				for (int i = 0; i < frames.size(); i++) {
					Frame frame = frames.get(i);
					Log.i(">>>>>", "" + frame.toString());

					ipcScrean = ipcScreans.get(i);
					host = "";// 广播地址
					port = 12345;// 广播的目的端口
					// 组成广播发送的内容
					IpcPackage ipcPackage = new IpcPackage();
					ipcPackage.setName(ipcScrean.getName());
					ipcPackage.setScreen(ipcScrean.getGraphics());
					ipcPackage.setType(frame.getType());
					ipcPackage.setUrl(frame.getUrl());

					ArrayList<Matrix> matrixList = new ArrayList<Matrix>();
					try {
						// ipcIpList = getIpcIp();
						matrixList = MatrixUtil.getInstance().getMatrixInfos();
					} catch (XmlPullParserException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if ("WEB".equals(frame.getType().toUpperCase())
							|| "VIDEO".equals(frame.getType().toUpperCase())) {
						// web或者video类型才需要发广播
						for (Matrix matrix : matrixList) {
							Log.e("HOST#####",
									"matrixname: " + matrix.getName()
											+ "ipcScreamname: "
											+ ipcScrean.getName());
							if (matrix.getName().equals(ipcScrean.getName())) {
								host = matrix.getIp();
								Log.i("HOST---", "host： " + host);
							}
						}

						message = ipcPackage.toString();
						Log.i(TAG, "投影屏幕:" + ipcScrean.getSc() + ";广播内容:"
								+ message + ";目标地址:");
						sendudpPackage(host, message.getBytes(), port);
					}
					// 如果是HDMI-VGA类型的，直接发送数据包给矩阵
					else {
						// 模拟矩阵的ip和端口
						host = MixcellaneousUtil.getInstance().getMatrixIp();
						//host = "192.168.18.20";
						//port = 12345;
						port = MixcellaneousUtil.getInstance().getMatrixPort();
						
						Integer output = null;
						Integer input = null;
						ipcScrean = ipcScreans.get(i);
						for (Matrix matrix : matrixList) {
							String matrixname = matrix.getName();
							if (matrixname.equals(ipcScrean.getName())) {
								output = matrix.getOutputport();
							}
						}
						input = Integer.parseInt(frame.getData());

						// 调用HDMI-VGA生成数据包的方法，将数据包发送给矩阵
						byte[][] bytes = MatrixPortUtil.getInstance()
								.getMarixContentbytes(input, output);
						for (byte[] bytearray : bytes) {
							sendudpPackage(host, bytearray, port);
							//发送给矩阵的udp数据包需要每隔一秒钟发送
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}

			}
		}).start();
	}

	/**
	 * 
	 * Description: udp发送指令开屏
	 */
	public void open() {
		String type = "OPENSCREEN";
		sendUdp(type);
	}

	/**
	 * 
	 * Description: udp发送指令关屏
	 */
	public void close() {
		String type = "CLOSESCREEN";
		sendUdp(type);
	}

	/**
	 * 发送udp指令
	 * 
	 * @param type
	 */
	private void sendUdp(final String type) {
		new Thread(new Runnable() {
			public void run() {
				String host = "255.255.255.255";// 广播地址
				int port = 12345;// 广播的目的端口
				// 组成广播发送的内容
				/*
				 * IpcPackage ipcPackage = new IpcPackage();
				 * ipcPackage.setName("I0"); ipcPackage.setScreen(0);
				 * ipcPackage.setType(type); ipcPackage.setUrl("http");
				 */
				// String message = ipcPackage.toString();

				ControllerPackage conPackage = new ControllerPackage(type, 1);
				String message = conPackage.getUdpContent();

				sendudpPackage(host, message.getBytes(), port);
			}
		}).start();
	}

	//给矩阵发送重置的命令
	private void sendResetPackage(){
		new Thread(new Runnable() {
			public void run() {
				//String host = "255.255.255.255";
				//int port = 12345;
				String host = MixcellaneousUtil.getInstance().getMatrixIp();
				int port = MixcellaneousUtil.getInstance().getMatrixPort();
				
				//发送给矩阵重置代码的数据包
				ResetPackageUtil.getInstance().sendResetIpcPackage(host,port);
				
			}		
		}).start();
	}
	
	// 发送数据包
	private void sendudpPackage(String hostaddr, byte[] bytes, int port) {
		InetAddress adds;
		try {
			adds = InetAddress.getByName(hostaddr);
			DatagramSocket ds = new DatagramSocket();
			DatagramPacket dp = new DatagramPacket(bytes, bytes.length, adds,
					port);
			ds.send(dp);
			ds.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
