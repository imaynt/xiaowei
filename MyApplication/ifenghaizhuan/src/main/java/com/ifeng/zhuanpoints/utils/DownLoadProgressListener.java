package com.ifeng.zhuanpoints.utils;


public interface DownLoadProgressListener {
	
	public static final int ERROR_DOWNLOAD = 100;
	public static final int ERROR_INVALID_SDCARD = 101;
	
	
	void onTotal(int total);
	void onProgressUpdate(double progress);
	void onFinish(String path);
	void onError(int code);
}
