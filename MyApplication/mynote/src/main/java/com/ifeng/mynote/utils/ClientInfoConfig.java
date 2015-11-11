package com.ifeng.mynote.utils;

import android.content.Context;

/**
 * 客户端配置参数
 * 
 * @author liwei5
 * 
 */
public class ClientInfoConfig extends ConfigPreference {

	/** 文件名 */
	private final static String SP_NAME = ".preference.client";
	/** 写入类型 */
	private final static int MODE = Context.MODE_PRIVATE;

	/** key when the lasttime check client update */
	private final static String KEY_LASTTIME_CHECK_UPDATE = "lasttime_check_update";
	/** time interval of check update */
	private final static int CHECK_UPDATE_INTERVAL = 24 * 60 * 60 * 1000;

	private final static String HAI_USEID = "hai_userid";
	private final static String HAI_USERNAME ="hai_username";
	private final static String HAI_PIC = "avatar";
	private final static String HAI_OPENID = "openid";
	

	/** 静态实例 */
	private static ClientInfoConfig sClientInfoConfig;

	/**
	 * 获取静态实例
	 * 
	 * @param context
	 * @return
	 */
	public static ClientInfoConfig getInstance(Context context) {
		if (sClientInfoConfig == null) {
			sClientInfoConfig = new ClientInfoConfig(context);
		}
		return sClientInfoConfig;
	}

	/**
	 * 构造，及对首次调用的配置参数初始化
	 * 
	 * @param context
	 */
	private ClientInfoConfig(Context context) {
		super(context, SP_NAME, MODE);
	}

	/**
	 * 检查客户端更新
	 * 
	 * @return
	 */
	public boolean shouldCheckUpdate() {
		long currentTime = System.currentTimeMillis();
		long lastTime = getLong(KEY_LASTTIME_CHECK_UPDATE);

		putLong(KEY_LASTTIME_CHECK_UPDATE, currentTime);
		if (currentTime - lastTime > CHECK_UPDATE_INTERVAL) {
			return true;
		}
		return false;
	}


	/**
	 * 获取最新用户id
	 * 
	 * @return
	 */
	public String getUserId() {
		return getString(HAI_USEID);
	}

	/**
	 * 设置最新userid
	 * 
	 * @param userid
	 */
	public void setUserId(String userid) {
		putString(HAI_USEID, userid);
	}
	/**
	 * 获取nickname
	 * 
	 * @return
	 */
	public String getNickName() {
		return getString(HAI_USERNAME);
	}

	/**
	 * 设置最后nickname
	 * 
	 * @param nickName
	 */
	public void setNickName(String nickName) {
		putString(HAI_USERNAME, nickName);
	}
	/**
	 * 获取最近avatar
	 * 
	 * @return
	 */
	public String getAvatar() {
		return getString(HAI_PIC);
	}

	/**
	 * 设置最后avatar
	 * 
	 * @param avatar
	 */
	public void setAvatar(String avatar) {
		putString(HAI_PIC, avatar);
	}
	/**
	 * 获取最近openid
	 * 
	 * @return
	 */
	public String getOpenId() {
		return getString(HAI_OPENID);
	}

	/**
	 * 设置最后openid
	 * 
	 * @param openid
	 */
	public void setOpenId(String openid) {
		putString(HAI_OPENID, openid);
	}
	
	
}
