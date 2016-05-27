package com.bigdata.bgis.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bigdata.bgis.domain.Device;

public class JsonUtil {
	private final static String TAG = "bgis/JsonUtil";

	public final static String MESSAGE = "finishparse";

	public final static String ALLDEVICELIST = "http://192.168.18.187:8080/omf/do/middleware/testservice/";

	private static JsonUtil jsonUtil;
	private Handler handler;

	private JsonUtil(Handler handler) {
		this.handler = handler;
	}

	public static JsonUtil getInstance(Handler handler) {
		if (jsonUtil == null) {
			jsonUtil = new JsonUtil(handler);
		}
		return jsonUtil;
	}

	// 获取解析json数据
	public void doPost(String addr) throws Exception {
		final URL url = new URL(addr);
		/*
		 * Thread thread = new Thread(new Runnable() {
		 * 
		 * 
		 * @Override public void run() { try { HttpURLConnection connection =
		 * (HttpURLConnection)url.openConnection();
		 * connection.setConnectTimeout(5000);
		 * connection.setRequestMethod("GET");
		 * 
		 * if(connection.getResponseCode() == 200){ InputStream inputStream =
		 * connection.getInputStream(); parseJson(inputStream); } } catch
		 * (IOException e) { e.printStackTrace(); } } }); thread.start();
		 */
		new JsonTask(url).execute();
	}

	// 解析json数据格式
	private void parseJson(InputStream inputStream) {
		byte[] bytes = convertIntoBytes(inputStream);
		/*String jsondata = new String(bytes);
		String tempjsondata = jsondata.replace("\\", "");*/
		//Log.d(TAG,tempjsondata);
		//String tempjsondata1 = tempjsondata.substring(1,tempjsondata.length()-1);
		//Log.d(TAG,tempjsondata1);
		ArrayList<Device> devices = new ArrayList<Device>();
		try {
			// JSONObject jsonObj = new JSONObject(jsondata);
			JSONArray jsonArray = new JSONArray(
					"[{\"devicename\":\"1号电视机\",\"devicetype\":1,\"deviceid\":1},{\"devicename\":\"2号电视机\",\"devicetype\":1,"
					+ "\"deviceid\":2},{\"devicename\":\"1号摄像头\",\"devicetype\":2,\"deviceid\":3},{\"devicename\":\"2号摄像头\","
					+ "\"devicetype\":2,\"deviceid\":4},{\"devicename\":\"1号投影幕\",\"devicetype\":3,\"deviceid\":5},{\"devicename\""
					+ ":\"2号投影幕\",\"devicetype\":3,\"deviceid\":6},{\"devicename\""
					+ ":\"外窗帘\",\"devicetype\":4,\"deviceid\":7},{\"devicename\""
					+ ":\"内窗帘\",\"devicetype\":4,\"deviceid\":8},{\"devicename\""
					+ ":\"1号电灯\",\"devicetype\":5,\"deviceid\":9},{\"devicename\""
					+ ":\"2号电灯\",\"devicetype\":5,\"deviceid\":10},{\"devicename\""
					+ ":\"3号电灯\",\"devicetype\":5,\"deviceid\":11},{\"devicename\""
					+ ":\"4号电灯\",\"devicetype\":5,\"deviceid\":12},{\"devicename\""
					+ ":\"5号电灯\",\"devicetype\":5,\"deviceid\":13},{\"devicename\""
					+ ":\"1号麦克风\",\"devicetype\":6,\"deviceid\":14},{\"devicename\""
					+ ":\"2号麦克风\",\"devicetype\":6,\"deviceid\":15},{\"devicename\""
					+ ":\"3号麦克风\",\"devicetype\":6,\"deviceid\":16},{\"devicename\""
					+ ":\"4号麦克风\",\"devicetype\":6,\"deviceid\":17},{\"devicename\""
					+ ":\"5号麦克风\",\"devicetype\":6,\"deviceid\":18},{\"devicename\""
					+ ":\"喇叭\",\"devicetype\":7,\"deviceid\":19}]");
			//JSONArray jsonArray = new JSONArray(tempjsondata);
			
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				int id = jsonObject.getInt("deviceid");// 获取设备列表中设备的id
				String name = jsonObject.getString("devicename");// 获取设备列表中设备的名称
				int type = jsonObject.getInt("devicetype");

				Device device = new Device(id, type, name);

				devices.add(device);
			}
			DeviceManager.getInstance().setDevices(devices);
			this.handler.sendMessage(prepareMessage());

		} catch (JSONException e) {
			Log.e(TAG, e.toString());
			e.printStackTrace();
		}
	}

	// 将带有json数据的流数据转换成字节类型
	private byte[] convertIntoBytes(InputStream inputStream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte buffer[] = new byte[1024];
		int length = 0;
		try {
			while ((length = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			inputStream.close();
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	// 准备返回到主线程消息
	private Message prepareMessage() {
		Message msg = Message.obtain(this.handler);
		Bundle data = new Bundle();
		data.putString(MESSAGE, "finish");
		msg.setData(data);
		return msg;
	}

	private class JsonTask extends AsyncTask {
		private URL url;

		public JsonTask(URL url) {
			this.url = url;
		}

		@Override
		protected Object doInBackground(Object... params) {
			try {
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setConnectTimeout(5000);
				connection.setRequestMethod("GET");

				if (connection.getResponseCode() == 200) {
					InputStream inputStream = connection.getInputStream();
					parseJson(inputStream);
				}
			} catch (ProtocolException e) {
				Log.e(TAG, e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				Log.e(TAG, e.toString());
				e.printStackTrace();
			}
			return "";
		}

	}
}
