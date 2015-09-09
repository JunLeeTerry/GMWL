package com.example.testandroidping;

import java.io.IOException;

import com.example.testandroidping.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button pingButton;
	private TextView textView;
	
	private static final String HOST = "192.168.1.20";
	//private static final String HOST = "www.baidu.com";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pingButton = (Button) findViewById(id.pingbutton);
		textView = (TextView) findViewById(id.textview);
		
		pingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				String message = pingHost(HOST);
				switch (message){
				case "success":
					textView.setText("可以ping通！！");
					break;
				
				case "failed":
					textView.setText("不能ping通！！");
					break;
				}	
			}
		});
	}

	private String pingHost(String host) {
		String result = "";
		int status = -1;
		Process p;
		try {
			p = Runtime.getRuntime().exec("ping -c 1 -w 500 " + host + "\n");
			//p.wait(500);
			//status = p.waitFor();
			Thread.sleep(500);
			status = p.exitValue();
			Log.e("ping status",status+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (status == 0) {
			result = "success";
		} else {
			result = "failed";
		}

		return result;
	}

}
