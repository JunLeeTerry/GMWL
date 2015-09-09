package com.gmsz.utils;

import android.util.Log;

public class PingTestUtil {
	private static final String SCONTROLLER_HOST = MixcellaneousUtil
			.getInstance().getScontrollerIp();

	private static PingTestUtil pingTestUtil;

	private PingTestUtil() {

	}

	public static PingTestUtil getInstance() {
		if (pingTestUtil == null) {
			pingTestUtil = new PingTestUtil();
		}
		return pingTestUtil;
	}

	public boolean pingSControllerHost() {
		String result = "";
		int status = -1;
		Process p;
		try {
			p = Runtime.getRuntime().exec(
					"ping -c 1 -w 500 " + SCONTROLLER_HOST + "\n");
			// p.wait(500);
			// status = p.waitFor();
			Thread.sleep(500);
			status = p.exitValue();
			Log.e("ping status", status + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (status == 0) {
			return true;
		} else {
			return false;
		}

	}
}
