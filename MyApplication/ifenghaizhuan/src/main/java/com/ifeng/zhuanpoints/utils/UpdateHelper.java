package com.ifeng.zhuanpoints.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.ifeng.zhuanpoints.MainActivity;
import com.ifeng.zhuanpoints.R;
import com.ifeng.zhuanpoints.RequestVolley;
import com.ifeng.zhuanpoints.RequestVolley.PostResponseListener;



public class UpdateHelper implements PostResponseListener{

	public static int UPGRADE_NONE = 0;
	public static int UPGRADE_NOT_FORCE = 1;
	public static int UPGRADE_FORCE = 2;
	
	public final static int DISPLAY_COUNT = 3; 
	
	private ProgressDialog mProgressDlg;

	protected AlertDialog simpleMessageDialog;

	private Activity mActivity;

//	private WebApiImpl mWebApi;
	private RequestQueue request;

	private int isForce;

	private Handler mHandler = new Handler();

//	private MyApplication mApp;

	/**
	 * True indicates update in backgroud, or invoked in maunally
	 */
	private boolean isAuto = false;

	private DownLoadUtil mDownloadUtil;
	
//	private UpdateInfo mUpdateInfo;

	public static UpdateHelper mMyself;
	private String officialid;
	private String minVersion;
	private String VersionUrl;
	private String explanation;
	
	private OnCallBack mCallback;
//	private OnUpgradeCancelListener mCancelListener;

	/**
	 * 
	 * @param activity
	 * @param isAuto
	 *            True indicates update in backgroud, or invoked in maunally
	 */
	public UpdateHelper(Activity activity, boolean isAuto,RequestQueue request) {
		mActivity = activity;
//		mApp = (MyApplication) mActivity.getApplication();
//		mWebApi = WebApi.getImplInstance(activity);
		this.request = request;
		this.isAuto = isAuto;
	}
	
//	public void setOncancelListener(OnUpgradeCancelListener listener) {
//		mCancelListener = listener;
//	}

	public void setCallBack(OnCallBack callback){
		mCallback = callback;
	}
	public void doCheckUpdate() {
		if (!isAuto) {
			showSimpleMessageDialog("getString(R.string.check_update_ing)");
		}
		RequestVolley.post(request, ContentUtil.Host
				+ "fun=versionInfo", this);
		
	}

	public int getUpgradeType(){
		return isForce;
	}
	
	public void setUpgradeType(int type){
		isForce = type;
	}
	
	
	public void showNonUpgradeDialog(){
		new AlertDialog.Builder(mActivity).setPositiveButton(R.string.upgrade_now, new OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(!TextUtils.isEmpty(VersionUrl))
					downloadNewAPK(VersionUrl);				
				}
			}).setNegativeButton(R.string.upgrade_next, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
		}).setCancelable(false)
		.setMessage(""+explanation)
		.show();				
	}
	
	public void showForceUpgradeDialog(){
			
		new AlertDialog.Builder(mActivity).setPositiveButton(R.string.upgrade_now, new OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(!TextUtils.isEmpty(VersionUrl))
					downloadNewAPK(VersionUrl);				
				}
			}).setNegativeButton(R.string.exit_app, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
//					mApp.exit();
					mActivity.finish();
			}
		}).setCancelable(false)
		.setMessage(""+explanation)
		.show();		
	}
	
	private void downloadNewAPK(String url) {
		mProgressDlg = new ProgressDialog(mActivity);
		mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, mActivity.getString(R.string.cancel), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mDownloadUtil.Stop();
				if (isForce == UPGRADE_FORCE) {
//					mApp.exit();
					mActivity.finish();
				} else {
//					if (mCancelListener != null) {
//						mCancelListener.onUpgradeCancel();
//					}
				}					
			}
		});
		
		mProgressDlg.setMessage(mActivity.getString(R.string.downloading_progress, "0.0%"));							
		mProgressDlg.setCancelable(false);
		mProgressDlg.show();
		mDownloadUtil = new DownLoadUtil(url, new DownLoadProgressListener() {

			@Override
			public void onTotal(int total) {

			}

			@Override
			public void onProgressUpdate(final double progress) {
				mHandler.post(new Runnable() {
					public void run() {
						String str = mActivity.getString(R.string.downloading_progress, progress + "%");
						mProgressDlg.setMessage(str);
					}
				});

			}

			@Override
			public void onFinish(final String path) {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						mProgressDlg.dismiss();
						showInstallDialog(path);
					}
				});

			}

			@Override
			public void onError(final int code) {				
				mHandler.post(new Runnable() {
					public void run() {
						mProgressDlg.dismiss();
						if (code == DownLoadProgressListener.ERROR_INVALID_SDCARD) {
							showSimpleMessageDialog("getString(R.string.invalid_sdcard)");
						} else {
							showSimpleMessageDialog("getString(R.string.download_error)");
						}
					}
				});
			}
		});
		mDownloadUtil.startDown();
	}

	protected void showInstallDialog(final String path) {
		new AlertDialog.Builder(mActivity).setMessage(R.string.download_apk_done).setPositiveButton(R.string.ok, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CommonUtils.installApk(mActivity, path);
				if (isForce == UPGRADE_FORCE) {
//					mApp.exit();
					mActivity.finish();
				} else {
//					if (mCancelListener != null) {
//						mCancelListener.onUpgradeCancel();
//					}
				}				
			}
		}).setNegativeButton(R.string.cancel, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (isForce == UPGRADE_FORCE) {
//					mApp.exit();
					mActivity.finish();
				} else {
//					if (mCancelListener != null) {
//						mCancelListener.onUpgradeCancel();
//					}
				}
				
			}
		}).setCancelable(false).show();
		
	}

	public void showUpdateDlg(final String url, String content) {
		if (mActivity.isFinishing()) {
			return;
		}				
         new AlertDialog.Builder(mActivity).setPositiveButton(R.string.upgrade_now, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				downloadNewAPK(url);				
			}
		}).setNegativeButton(R.string.upgrade_next, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				if (mCancelListener != null) { 
//					mCancelListener.onUpgradeCancel(); 
//				} 
//				
			}
		}).setCancelable(false)
		.setMessage(content)
		.show();				
	}

	private String getString(int resId) {
		return mActivity.getString(resId);
	}

	private void dismissSimpleMessageDialog() {
		if (simpleMessageDialog != null && simpleMessageDialog.isShowing()) {
			simpleMessageDialog.dismiss();
		}
	}

	private void showSimpleMessageDialog(String message) {
		if (mActivity.isFinishing()) {
			return;
		}
		
		simpleMessageDialog = new AlertDialog.Builder(mActivity).setMessage(message)
				.setPositiveButton(R.string.ok, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// if (mCancelListener != null) {
						// mCancelListener.onUpgradeCancel();
						// }
					}
				}).show();
		
	}

	public interface OnCallBack{
		void onSuccess();
		void onFailed();
	}

	@Override
	public Map<String, String> setParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "2");
		return map;
	}

	@Override
	public Map<String, String> setHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void success(String result) {

		dismissSimpleMessageDialog();
		int versionCode = CommonUtils
				.getVersionCode(mActivity);
		try {
			JSONObject jsonResult = new JSONObject(result);
			String code = jsonResult.optString("result");
			String content = jsonResult.optString("content");
			if (code.equals("0")) {
				JSONObject contentResult = new JSONObject(content);
				officialid = contentResult.optString("officialid");
				minVersion = contentResult.optString("minVersion");
				VersionUrl = contentResult.optString("url");
				contentResult.optString("versionid");
				explanation = contentResult.optString("explanation");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(!TextUtils.isEmpty(officialid))
		{
			if(Integer.parseInt(minVersion)>versionCode)
			{
				isForce = UPGRADE_FORCE;
			}else if(Integer.parseInt(officialid)>versionCode)
			{
				isForce = UPGRADE_NOT_FORCE;
			}
		}
		
		if(mCallback != null)
			mCallback.onSuccess();
	
		
	}

	@Override
	public void failed(String message) {

		if (!isAuto) {
			ToastUtils.show(mActivity, message);
			dismissSimpleMessageDialog();
		}
		if(mCallback != null)
			mCallback.onFailed();
	
		
	}
	
	
}
