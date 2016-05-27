package com.bigdata.bgis.iflytek;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechUser;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class IFlytekUtil {
	private static final String TAG = "bgis/IFlytekUtil";
	
	private HashMap<String,String> mIatResults = new LinkedHashMap<String,String>();
	private static final String APP_ID = "534e3fe2";
	
	private static IFlytekUtil iflytekUtil;
	private Context context;
	private RecognizerDialog myDialog;//识别者对话框
	private RecognizerDialogListener recognizerDialogListener;//识别者对话框监听器
	private SpeechListener listener;//用户登录回调监听器

	private IFlytekUtil(Context context) {
		this.context = context;
	}

	public static IFlytekUtil getInstance(Context context) {
		if (iflytekUtil == null) {
			iflytekUtil = new IFlytekUtil(context);
		}
		return iflytekUtil;
	}
	
	//初始化语音对话框
	public void init() {
		/**
		 * 用户登录回调监听器.
		 */
		SpeechListener listener = new SpeechListener()
		{

			@Override
			public void onData(byte[] arg0) {
			}

			@Override
			public void onCompleted(SpeechError error) {
				if(error != null) {
					//System.out.println("user login success");
					Log.d(TAG,"user login success");
				}			
			}

			@Override
			public void onEvent(int arg0, Bundle arg1) {
			}		
		};
		
		SpeechUser.getUser().login(context, null, null
				, "appid=" + APP_ID, listener);
		
		
		//语音识别者对话框
		recognizerDialogListener = new RecognizerDialogListener() {
			@Override
			public void onError(SpeechError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onResult(RecognizerResult results, boolean isLast) {
				// TODO Auto-generated method stub
				Log.d(TAG,"recognizer result:"+results.getResultString());
				String resultString = printResult(results);
				Log.d(TAG,"resultString:"+resultString);
				IFlytekCommand.getInstance().execCommand(resultString);
			}
				
			

		};

		myDialog = new RecognizerDialog(this.context);
		// 设置引擎为转写
		myDialog.setParameter(SpeechConstant.DOMAIN, "iat");
		// 设置识别语言为中文
		myDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		// 设置方言为普通话
		myDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
		// 设置录音采样率为
		myDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		// 设置监听对象
		myDialog.setListener(recognizerDialogListener);
		
	}
	
	//显示语音对话框
	public void show(){
		myDialog.show();
	}
	
	
	//获得语音内容，可以将语音内容显示到textview中
	private String printResult(RecognizerResult results) {
		String text = IFlytekJsonParser.parseIatResult(results.getResultString());
		//System.out.println("___________________text"+text);
		Log.d(TAG,text);
		
		String sn = null;
		// 读取json结果中的sn字段
		try {
			JSONObject resultJson = new JSONObject(results.getResultString());
			sn = resultJson.optString("sn");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mIatResults.put(sn, text);

		StringBuffer resultBuffer = new StringBuffer();
		String resultString=null;
		for (String key : mIatResults.keySet()) {
			resultBuffer.append(mIatResults.get(key));
			resultString=resultBuffer.toString();
		}
		resultString = resultString.replaceAll("。", "");
		return resultString;
	}
	
}
