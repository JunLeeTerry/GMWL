package com.gmsz.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.gmsz.domain.Matrix;
import com.gmsz.domain.MatrixPort;
import com.gmsz.domain.ResetPackage;

public class ResetPackageUtil {
	private static ResetPackageUtil resetPackageUtil;

	private MatrixPortUtil matrixPortUtil = MatrixPortUtil.getInstance();
	
	private ResetPackageUtil() {
	}

	public static ResetPackageUtil getInstance() {
		if (resetPackageUtil == null) {
			resetPackageUtil = new ResetPackageUtil();
		}
		return resetPackageUtil;
	}

	public void sendResetIpcPackage(String host,int port) {
		ArrayList<ResetPackage> resetPackageList = getResetPortList();
		for(ResetPackage resetPackage:resetPackageList){
			for(byte[] bytes:resetPackage.getCommand()){
				sendudpPackage(host, bytes, port);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

/*	// 获得矩阵中对应端口和命令的列表
	private ArrayList<MatrixPort> getMatrixPortContent() {
		ArrayList<MatrixPort> matrixPortList = new ArrayList<MatrixPort>();
		try {
			matrixPortList = matrixPortUtil.getMatrixPorts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return matrixPortList;
	}*/

	// 获得矩阵中输入输出口对应的列表
	private ArrayList<ResetPackage> getResetPortList() {
		ArrayList<ResetPackage> resetPackageList = new ArrayList<ResetPackage>();
		ArrayList<Matrix> matrixes = new ArrayList<Matrix>();
		
		try {
			matrixes = MatrixUtil.getInstance().getMatrixInfos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Matrix matrix : matrixes) {
			byte[][] tmpbyte = matrixPortUtil.getMarixContentbytes(matrix.getInputport(),matrix.getOutputport());
			ResetPackage resetPackage = new ResetPackage(tmpbyte);
			resetPackageList.add(resetPackage);
		}
		return resetPackageList;
	}
	
/*	private void getResetIpcContent(){
		ArrayList<MatrixPort> matrixPorts = getMatrixPortContent();
		
	}*/
	
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
