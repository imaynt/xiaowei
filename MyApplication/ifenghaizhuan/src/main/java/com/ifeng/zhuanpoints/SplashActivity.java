package com.ifeng.zhuanpoints;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;

import com.ifeng.zhuanpoints.RequestVolley.PostResponseListener;
import com.ifeng.zhuanpoints.utils.ClientInfoConfig;
import com.ifeng.zhuanpoints.utils.ContentUtil;
import com.ifeng.zhuanpoints.utils.DeviceUtil;
import com.ifeng.zhuanpoints.utils.ToastUtils;

public class SplashActivity extends BaseActivity implements
		PostResponseListener {
	private EditText edit;
	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!TextUtils.isEmpty(ClientInfoConfig.getInstance(
						getBaseContext()).getUserId()))
		{
			initLoading();
		}else
		{
		setContentView(R.layout.activity_splash);
		edit = (EditText) findViewById(R.id.splash_edit);
		findViewById(R.id.splash_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(edit.getText().toString())) {
					ToastUtils.show(getBaseContext(), "请输入正确激活码!");
				} else {
					RequestVolley.post(request, ContentUtil.Host
							+ "fun=getUserinfo", SplashActivity.this);
				}

			}
		});
		}

	}
	private void initLoading()
	{
		View view = View.inflate(this, R.layout.act_start, null);
		setContentView(view);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(animation);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    	goMain();
                    }


                }, 300);
            }
        });
	}
	private void goMain()
	{
		startActivity(new Intent(SplashActivity.this,
				MainActivity.class));
		SplashActivity.this.finish();
	}
	@Override
	public Map<String, String> setParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", "" + edit.getText().toString());
		map.put("deviceid", "" + DeviceUtil.getDevice(this));
		map.put("platform", "" + android.os.Build.MODEL);
		map.put("oscode", "" + android.os.Build.VERSION.RELEASE);
		String signStr = "userid=" + edit.getText().toString() + "&deviceid="
				+ DeviceUtil.getDevice(this) + "&platform="
				+ android.os.Build.MODEL + "&oscode="
				+ android.os.Build.VERSION.RELEASE + "||u9Y%)!a1z";
		map.put("sign", DeviceUtil.GetMD5Code(signStr));

		return map;
	}

	@Override
	public Map<String, String> setHeader() {
		return null;
	}

	@Override
	public void success(String result) {

		try {
			JSONObject jsonResult = new JSONObject(result);
			String code = jsonResult.optString("result");
			String msg = jsonResult.optString("msg");
			String content = jsonResult.optString("content");
			if (code.equals("0")) {
				ToastUtils.show(getBaseContext(), "绑定成功");
				JSONObject userResult = new JSONObject(content);
				String userid = userResult.optString("userid");
				String nickname = userResult.optString("nickname");
				String avatar = userResult.optString("avatar");
				String openid = userResult.optString("openid");
				if (!userid.equals(ClientInfoConfig.getInstance(
						getBaseContext()).getUserId()))
					ClientInfoConfig.getInstance(getBaseContext()).setUserId(
							openid);
				if (!nickname.equals(ClientInfoConfig.getInstance(
						getBaseContext()).getNickName()))
					ClientInfoConfig.getInstance(getBaseContext()).setNickName(
							nickname);
				if (!avatar.equals(ClientInfoConfig.getInstance(
						getBaseContext()).getAvatar()))
					ClientInfoConfig.getInstance(getBaseContext()).setAvatar(
							avatar);
				if (!openid.equals(ClientInfoConfig.getInstance(
						getBaseContext()).getOpenId()))
					ClientInfoConfig.getInstance(getBaseContext()).setOpenId(
							openid);
				startActivity(new Intent(SplashActivity.this,
						MainActivity.class));
				SplashActivity.this.finish();
			} else {
				ToastUtils.show(getBaseContext(), "数据异常，请稍后再试!");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void failed(String message) {
		ToastUtils.show(getBaseContext(), "网络连接错误，请稍后再试!");
	}

}
