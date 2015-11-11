package com.ifeng.mynote.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @description toast utils
 * @author lizb
 * @time 下午7:26:41
 * 
 */
public class ToastUtils {

	public static void show(Context mContext, String message) {
		Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();

	}

}
