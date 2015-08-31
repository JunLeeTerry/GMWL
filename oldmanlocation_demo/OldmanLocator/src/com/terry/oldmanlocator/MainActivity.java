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
	//�α�绰
	private static final String PHONENUMBER = "15195686262";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationListener = new LocationListener() {
			// λ�÷����ı�����
			public void onLocationChanged(Location location) {
				// location) Ϊ��Ҫ��λ����Ϣ
				// ��ȡά����Ϣ
				double latitude = location.getLatitude();
				// ��ȡ������Ϣ
				double longitude = location.getLongitude();
				String message = "�ҵ����꣺  γ��=" + latitude + " ��  ����="
						+ longitude;
				textView.setText(message);
				// sendSMS(PHONENUMBER,message);
			}

			// provider ���û��رպ����
			public void onProviderDisabled(String provider) {

			}

			// provider ���û����������
			public void onProviderEnabled(String provider) {

			}

			// provider ״̬�仯ʱ����
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}
		};
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.location);
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// ����������֪��λ���ṩ�ߵ������б�����δ��׼���ʻ���ûĿǰ��ͣ�õġ�
		List<String> lp = lm.getAllProviders();
		for (String item : lp) {
			Log.i("8023", "����λ�÷���" + item);
		}

		Criteria criteria = new Criteria();
		criteria.setCostAllowed(false);
		// ����λ�÷������
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); // ����ˮƽλ�þ���
		// getBestProvider ֻ��������ʵ��û��λ�ù�Ӧ�̽�������
		providerName = lm.getBestProvider(criteria, true);
		Log.i("8023", "------λ�÷���" + providerName);

		if (providerName != null) {
			Location location = lm
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			int i = 1;
			while (location == null && i++ > 10) {
				Toast.makeText(this, "��λʧ���������������綨λ...", Toast.LENGTH_LONG);
				location = lm
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (location == null) {
				Toast.makeText(this, "��λʧ������������GPS��λ...", Toast.LENGTH_LONG);
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
				Toast.makeText(this, "��λʧ��", Toast.LENGTH_LONG);
				return;
			}

			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000,
					10, mLocationListener);

			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10,
					mLocationListener);

			Log.i("8023", "-------" + location);

			// ��ȡά����Ϣ
			double latitude = location.getLatitude();

			// ��ȡ������Ϣ
			double longitude = location.getLongitude();
			message = "��λ��ʽ�� " + providerName + "�ҵ�����  γ�ȣ�" + latitude
					+ " ��  ���ȣ�" + longitude;
			textView.setText(message);
			// sendSMS(PHONENUMBER,message);
		} else {
			Toast.makeText(this, "1.������������ \n2.���GPS", Toast.LENGTH_SHORT)
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
