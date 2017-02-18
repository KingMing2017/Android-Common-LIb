package com.king.android.common;

import android.app.Application;

import com.king.android.common.net.HttpRequest;

public class BaseApplication extends Application {

	public final static int MSG_REQUEST_SUCCESS = 100;
	public final static int MSG_REQUEST_FAILED = 101;
	public final static int MSG_TOKEN_EXPIRED = 102;
	public final static int MSG_NETWORK_DISABLED = 103;
	public final static int MSG_NETWORK_ENABLED = 104;
	public final static int MSG_REFRESH_VIEW = 105;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		// ��ʼ��Log
		AppLog.initialize();
		// ȫ�������������
		mHttpRequest = new HttpRequest(getApplicationContext());

	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	// ����ʽ����
	private static HttpRequest mHttpRequest = null;
	public static HttpRequest getHttpRequest(){
		return mHttpRequest;
	}
}
