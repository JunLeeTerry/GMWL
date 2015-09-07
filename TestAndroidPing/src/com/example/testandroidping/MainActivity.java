package com.example.testandroidping;

import java.io.IOException;

import com.example.testandroidping.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button pingButton;
	private TextView textView;
	
	private static final String HOST = "192.168.18.20";
	
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
					textView.setText("����pingͨ����");
					break;
				
				case "failed":
					textView.setText("����pingͨ����");
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
			p = Runtime.getRuntime().exec("ping -c 4 -w 100 " + host);
			
			status = p.waitFor();
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