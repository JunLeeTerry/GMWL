package com.terry.oldmanlocator;

import java.util.Date;
import java.util.List;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static String message = null;
	private TextView textView = null;
	private Button start_button;
	private Button stop_button;

	private Intent intent;

	private String providerName;

	private LocationListener mLocationListener;

	// ----phone num-----
	//宋斌电话
	private static final String PHONENUMBER = "15195686262";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationListener = new LocationListener() {
			// 位置发生改变后调用
			public void onLocationChanged(Location location) {
				// location) 为需要的位置信息
				// 获取维度信息
				double latitude = location.getLatitude();
				// 获取经度信息
				double longitude = location.getLongitude();
				String message = "我的坐标：  纬度=" + latitude + " ，  经度="
						+ longitude;
				textView.setText(message);
				// sendSMS(PHONENUMBER,message);
			}

			// provider 被用户关闭后调用
			public void onProviderDisabled(String provider) {

			}

			// provider 被用户开启后调用
			public void onProviderEnabled(String provider) {

			}

			// provider 状态变化时调用
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}
		};
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.location);
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
		List<String> lp = lm.getAllProviders();
		for (String item : lp) {
			Log.i("8023", "可用位置服务：" + item);
		}

		Criteria criteria = new Criteria();
		criteria.setCostAllowed(false);
		// 设置位置服务免费
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); // 设置水平位置精度
		// getBestProvider 只有允许访问调用活动的位置供应商将被返回
		providerName = lm.getBestProvider(criteria, true);
		Log.i("8023", "------位置服务：" + providerName);

		if (providerName != null) {
			Location location = lm
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			int i = 1;
			while (location == null && i++ > 10) {
				Toast.makeText(this, "定位失败正在重新用网络定位...", Toast.LENGTH_LONG);
				location = lm
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (location == null) {
				Toast.makeText(this, "定位失败正在重新用GPS定位...", Toast.LENGTH_LONG);
					location = lm
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (location == null) {
				Toast.makeText(this, "定位失败", Toast.LENGTH_LONG);
				return;
			}

			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000,
					10, mLocationListener);

			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10,
					mLocationListener);

			Log.i("8023", "-------" + location);

			// 获取维度信息
			double latitude = location.getLatitude();

			// 获取经度信息
			double longitude = location.getLongitude();
			message = "定位方式： " + providerName + "我的坐标  纬度：" + latitude
					+ " ，  经度：" + longitude;
			textView.setText(message);
			// sendSMS(PHONENUMBER,message);
		} else {
			Toast.makeText(this, "1.请检查网络连接 \n2.请打开GPS", Toast.LENGTH_SHORT)
					.show();
		}

		// -------------button part---------------------
	/*	start_button = (Button) findViewById(R.id.startbutton);
		stop_button = (Button) findViewById(R.id.stopbutton);

		start_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				 * intent = new Intent(MainActivity.this, MessageService.class);
				 * MainActivity.this.startService(intent);
				 
			}
		});
		stop_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				 * // TODO Auto-generated method stub stopService(intent);
				 
			}
		});
*/
	}

	private long lastTime = System.currentTimeMillis() - 600000;

	private void sendSMS(String phonenumber, String message) {
		try {
			long nowTime = System.currentTimeMillis();
			if (nowTime - lastTime > 60000) {
				lastTime = nowTime;
				SmsManager sms = SmsManager.getDefault();
				sms.sendTextMessage(phonenumber, null, message, null, null);
			}
		} catch (Exception e) {
		}
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
	 */

	public static String getMessage() {
		return message;
	}
}
