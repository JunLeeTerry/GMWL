package com.terryspace.vlc;

import android.app.Application;
import android.content.Context;

public class VLCApplication extends Application {

	private static VLCApplication sInstance;

	@Override
	public void onCreate() {
		super.onCreate();

		sInstance = this;
	}

	public static Context getAppContext() {
		return sInstance;
	}
}
