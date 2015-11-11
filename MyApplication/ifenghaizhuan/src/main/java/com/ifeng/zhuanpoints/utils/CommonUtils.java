package com.ifeng.zhuanpoints.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;


public class CommonUtils {

	private final static String DEFAULT_VERSION = "1.0.0";

	public static boolean isMobileNumber(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(14[5,7])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static boolean isCarNumber(String carNumber) {
		String vehicleNoStyle = "[\u4e00-\u9fa5]{1}[A-Z_a-z]{1}[A-Z_a-z_0-9]{5}";
		Pattern pattern = Pattern.compile(vehicleNoStyle);
		Matcher matcher = pattern.matcher(carNumber);
		return matcher.matches();
	}

	public static boolean checkNickName(String nickname) {
		String vehicleNoStyle = "^[\u4e00-\u9fa5A-Za-z0-9]*$";
		Pattern pattern = Pattern.compile(vehicleNoStyle);
		Matcher matcher = pattern.matcher(nickname);
		return matcher.matches();
	}

	public static int getVersionCode(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		int version = 1;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			version = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	public static String getVersionName(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		String version = DEFAULT_VERSION;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	public static boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	public static double round(Double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal b = null == v ? new BigDecimal("0.0") : new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private static int sdkVersion = -1;

	// 根据4.0和2.x两种不同版本生成sortkey

	public static int getSDKVersion() {
		if (sdkVersion == -1) {
			sdkVersion = Build.VERSION.SDK_INT;
		}
		return sdkVersion;
	}

	public static boolean checkEmail(String email) {
		Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static void installApk(Activity activity, String path) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			String type = "application/vnd.android.package-archive";
			intent.setDataAndType(Uri.parse("file://" + path), type);
			activity.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File getExternalCacheDir(Context context) {
		String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
	}

	/**
	 * Get a meta data ,which defined in the mainfest file.
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getMetaDataFromApp(Context context, String key) {
		String channel = "";
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);
			channel = appInfo.metaData.getString(key);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return channel;
	}

	public static ComponentName getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
		if (runningTaskInfos != null)
			return (runningTaskInfos.get(0).topActivity);
		else
			return null;
	}
	
	public static void showShareWindow(Context context,String message) {	
//		Intent intent = new Intent(Intent.ACTION_SEND);
//		intent.setType("text/plain");
//		intent.putExtra(Intent.EXTRA_TEXT, message);		  	      
//		intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share));
//	    intent.putExtra(Intent.EXTRA_TITLE,R.string.share);	    
//	    context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));        
	}

	public static boolean isAppAlive(Context context) {	
		return getAppTaskInfo(context) != null;
	}
	
	/**
	 * Get a task to run my app.
	 * @param context
	 * @return
	 */
	public static RunningTaskInfo getAppTaskInfo(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(100);
		if (runningTaskInfos != null) {
			for (RunningTaskInfo info : runningTaskInfos) {
				if (context.getPackageName().equals(info.topActivity.getPackageName())) {
					return info;
				}
			}
		}
		return null;
	}
	
	public static PendingIntent createPengdingIntent(Context context, Class<? extends Activity> mainClass, Intent data) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setClass(context, mainClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (data != null) {
			intent.putExtras(data);
		}		
		PendingIntent pi = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pi;
	}
	
	
	public static File getDownloadFile(String fileName) {
		if (!Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return null;
		}
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
				"dingzhi/video");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("HuaChuang", "failed to create directory");
				return null;
			}
		}

		File file = new File(mediaStorageDir.getPath(), fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return file;
	}
	
	public static void recycleImageView(ImageView view){
		if (view != null) {
			BitmapDrawable bd = (BitmapDrawable) view.getDrawable();
			view.setImageDrawable(null);
			if (bd != null) {
				Bitmap b = bd.getBitmap();
				if (b != null && !b.isRecycled()) {
					b.recycle();
					b = null;
				}
			}
		}
	}
	public static String getVersion(Context context)//获取版本号versionName
	{
		try {
			PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "未知版本";
		}
	}
	
	
//	public static int getVersionCode(Context context)//获取版本号(内部识别号)versionCode
//	{
//		try {
//			PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//			return pi.versionCode;
//		} catch (NameNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return 0;
//		}
//	}
	
}
