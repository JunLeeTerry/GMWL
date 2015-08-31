package com.terryspace.vlc.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {
	
	private static final String action_boot = "android.intent.action.BOOT_COMPLETED";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction() == action_boot){
			Intent new_intent = new Intent(context,VlcVideoActivity.class);
			new_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(new_intent);
		}
	}

}
