package com.terry.oldmanlocator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

public class MessageService extends Service {
	
	private static String phonenumber = "13771717721";
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Thread thread = new Thread() {  
            @Override  
            public void run() {  
               for (int i = 0 ;i<5;i++) {  
                    try {  
                        //if (started) { 
                    		String message = MainActivity.getMessage();
                    		SmsManager sms = SmsManager.getDefault();
                    		sms.sendTextMessage(phonenumber,null,message, null, null);
                            Log.d("sms.service", "send a sms message.");  
                        //}  
                        Thread.sleep(1000 * 60);  
                    } catch (InterruptedException e) {  
                    }  
                }  
            }  
        }; 
  
        thread.start();  
  
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
