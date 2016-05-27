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
	private RecognizerDialog myDialog;//ʶ���߶Ի���
	private RecognizerDialogListener recognizerDialogListener;//ʶ���߶Ի��������
	private SpeechListener listener;//�û���¼�ص�������

	private IFlytekUtil(Context context) {
		this.context = context;
	}

	public static IFlytekUtil getInstance(Context context) {
		if (iflytekUtil == null) {
			iflytekUtil = new IFlytekUtil(context);
		}
		return iflytekUtil;
	}
	
	//��ʼ�������Ի���
	public void init() {
		/**
		 * �û���¼�ص�������.
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
		
		
		//����ʶ���߶Ի���
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
		// ��������Ϊתд
		myDialog.setParameter(SpeechConstant.DOMAIN, "iat");
		// ����ʶ������Ϊ����
		myDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		// ���÷���Ϊ��ͨ��
		myDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
		// ����¼��������Ϊ
		myDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		// ���ü�������
		myDialog.setListener(recognizerDialogListener);
		
	}
	
	//��ʾ�����Ի���
	public void show(){
		myDialog.show();
	}
	
	
	//����������ݣ����Խ�����������ʾ��textview��
	private String printResult(RecognizerResult results) {
		String text = IFlytekJsonParser.parseIatResult(results.getResultString());
		//System.out.println("___________________text"+text);
		Log.d(TAG,text);
		
		String sn = null;
		// ��ȡjson����е�sn�ֶ�
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
		resultString = resultString.replaceAll("��", "");
		return resultString;
	}
	
}
