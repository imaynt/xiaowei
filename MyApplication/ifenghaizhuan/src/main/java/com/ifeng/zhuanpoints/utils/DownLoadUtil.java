package com.ifeng.zhuanpoints.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.Environment;
import android.webkit.URLUtil;



public class DownLoadUtil implements Runnable{

	public String strURL = "";
	private String fileEx = "apk";
	private String fileNa = "update";
	private String currentFilePath = "";
	public boolean breakflag = false;
	private DownLoadProgressListener progressUpdate;
	private String updatePath = "";
	// 连接超时时间
	public static final int timeout = 30000;
	// 读取超时时间
	public static final int readtimeout = 60000;

	public DownLoadUtil(String url, DownLoadProgressListener listener) {
		strURL = url;
		progressUpdate = listener;
	}

		
	public void startDown() {
		new Thread(this).start();
	}

	private void getFile(final String strPath) {
		try {
			if (!strPath.equals(currentFilePath)) {

				currentFilePath = strPath;
			}
			getDataSource(strPath);

		} catch (Exception e) {
			e.printStackTrace();			
			notifiyError(DownLoadProgressListener.ERROR_DOWNLOAD);
		}
	}

	void notifiyError(int code) {
		if (progressUpdate != null) {
			progressUpdate.onError(code);
		}
	}

	private void getDataSource(String strPath) throws Exception {
		if (!URLUtil.isNetworkUrl(strPath)) {
			notifiyError(DownLoadProgressListener.ERROR_DOWNLOAD);
		} else {
			// 判断sd卡是否可用
			if (!CommonUtils.hasSdcard()) {
				notifiyError(DownLoadProgressListener.ERROR_INVALID_SDCARD);
				return;
			}

			File myTempFile = null;
			InputStream is = null;
			FileOutputStream fos = null;
			int Length_server = 0;
			try {
				URL myURL = new URL(strPath);

				URLConnection conn = myURL.openConnection();
				conn.setConnectTimeout(timeout);
				conn.setReadTimeout(readtimeout);
				conn.connect();
				// 获取服务端文件大小
				Length_server = conn.getContentLength();
				if (progressUpdate != null) {
					progressUpdate.onTotal(Length_server);					
				}
				is = conn.getInputStream();
				if (is == null) {
					throw new RuntimeException("stream is null");
				}
				String fileName = fileNa + "." + fileEx;
				File filDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CarUnion/apk");
				if (!filDir.exists()) {
					filDir.mkdirs();
				}
				updatePath = Environment.getExternalStorageDirectory() + "/CarUnion/apk/" + fileName;
				myTempFile = new File(updatePath);

				if (myTempFile != null && myTempFile.exists()) {
					myTempFile.delete();
				}

				fos = new FileOutputStream(myTempFile);
				byte buf[] = new byte[1024];

				int readed = -1;
				int downLoadSize = 0;
				while ((readed = is.read(buf)) != -1) {
					if (breakflag) {
						return;
					}
					fos.write(buf, 0, readed);
					downLoadSize += readed;
					if (progressUpdate != null) {						
						float progress = downLoadSize / (float) Length_server;
						progress =  Math.round(progress*100);						
						progressUpdate.onProgressUpdate(progress);// 通知应用当前下载量
					}
				}

				fos.flush();
				// 比较文件大小一致则安装，否则给你错误提示
				if (Length_server != myTempFile.length()) {
					notifiyError(DownLoadProgressListener.ERROR_DOWNLOAD);
					return;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				notifiyError(DownLoadProgressListener.ERROR_DOWNLOAD);
				return;
			} finally {
				if (fos != null) {
					fos.close();

				}
				if (is != null) {
					is.close();

				}
				if (breakflag) {
					myTempFile.delete();
				}
			}
			
			
			if (progressUpdate != null) {
				progressUpdate.onFinish(updatePath);// 通知应用下载完毕
			}
		}
	}

	public void Stop() {
		breakflag = true;
	}

	public void setOnProgressUpdateListener(DownLoadProgressListener onProgressUpdateListener) {
		progressUpdate = onProgressUpdateListener;
	}


	@Override
	public void run() {
		breakflag = false;
		try {
			fileEx = strURL.substring(strURL.lastIndexOf(".") + 1, strURL.length()).toLowerCase();
			fileNa = strURL.substring(strURL.lastIndexOf("/") + 1, strURL.lastIndexOf("."));
		} catch (Exception ex) {
			notifiyError(DownLoadProgressListener.ERROR_DOWNLOAD);
			return;
		}
		getFile(strURL);
		
	}
	
}
