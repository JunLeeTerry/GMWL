package com.example.testandroidxml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import com.example.testandroidxml.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button start_button;
	private Button show_button;
	private TextView text_view;
	
	private StringBuilder stringbuilder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start_button = (Button)findViewById(R.id.start_button);
		show_button = (Button)findViewById(id.show_button);
		text_view = (TextView)findViewById(id.text_view);
		
		start_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				save();
			}
		});
		
		show_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					FileInputStream inputstream = openFileInput("test.xml");
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
					stringbuilder = new StringBuilder();
					String line = null;
					while((line = reader.readLine())!=null){
						stringbuilder.append(line);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				text_view.setText(stringbuilder);
			}
		});
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	
	private String save(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
	    try {  
            serializer.setOutput(writer);  
            serializer.startDocument("utf-8", true);  
            serializer.startTag("", "users");  
  
            serializer.startTag("", "userName");  
            serializer.text("test");  
            //serializer.attribute("", "name", "value");
            serializer.endTag("", "userName");  
  
            serializer.startTag("", "userEmail");  
            serializer.text("test");  
            serializer.endTag("", "userEmail");  
  
            serializer.startTag("", "passWord");  
            serializer.text("test");  
            serializer.endTag("", "passWord");  
  
            serializer.endTag("", "users");  
            serializer.endDocument();  
            
            String string  = writer.toString();
            OutputStream out = openFileOutput("test.xml", MODE_PRIVATE);
            
            OutputStreamWriter outWriter = new OutputStreamWriter(out);
            outWriter.write(writer.toString());  
            outWriter.close();  
            out.close();  
  
        } catch (IllegalArgumentException e) {  
            // TODO: handle exception  
            e.printStackTrace();  
        } catch (IllegalStateException e) {  
            // TODO: handle exception  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO: handle exception  
            e.printStackTrace();  
        }  
        return writer.toString();  
    }  
	
}
