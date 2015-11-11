package com.ifeng.zhuanpoints;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ifeng.zhuanpoints.utils.AsyImageLoader;

public class BaseActivity extends Activity {
	protected RequestQueue request;
	public AsyImageLoader mImageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		request = Volley.newRequestQueue(getApplicationContext());
		mImageLoader = new AsyImageLoader(BaseActivity.this);
		
	}

}
