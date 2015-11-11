package com.ifeng.zhuanpoints;

import java.util.HashMap;
import java.util.Map;

import net.midi.wall.sdk.AdWall;
import net.midi.wall.sdk.IAdWallShowAppsNotifier;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.dow.android.DOW;
import cn.dow.android.listener.DLoadListener;
import cn.waps.AppConnect;

import com.dlnetwork.DevInit;
import com.ifeng.zhuanpoints.RequestVolley.PostResponseListener;
import com.ifeng.zhuanpoints.utils.ClientInfoConfig;
import com.ifeng.zhuanpoints.utils.ContentUtil;
import com.ifeng.zhuanpoints.utils.DeviceUtil;
import com.ifeng.zhuanpoints.utils.ToastUtils;
import com.ifeng.zhuanpoints.utils.UpdateHelper;
import com.ifeng.zhuanpoints.utils.UpdateHelper.OnCallBack;
import com.ifeng.zhuanpoints.widget.CircleImageView;
import com.yql.dr.sdk.DRSdk;

import dd.ff.aa.AdManager;
import dd.ff.aa.listener.Interface_ActivityListener;
import dd.ff.aa.os.OffersManager;


public class MainActivity extends BaseActivity implements OnClickListener,IAdWallShowAppsNotifier
,PostResponseListener{
	
	private CircleImageView imageView;
	private TextView nickname;
	private TextView nickId;
	private String channelType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i_getvalue = getIntent();  
		String action = i_getvalue.getAction();  
		if(Intent.ACTION_VIEW.equals(action)){  
		    Uri uri = i_getvalue.getData();  
		    if(uri != null){  
		    	channelType = uri.getQueryParameter("id");  
		        if(TextUtils.isEmpty(ClientInfoConfig.getInstance(getBaseContext()).getUserId()))
		        {
		        	startActivity(new Intent(MainActivity.this,SplashActivity.class));
		        	this.finish();
		        	return;
		        }
		    	
		    	if(!TextUtils.isEmpty(channelType))
		    		checkChannel(Integer.parseInt(channelType));
		    }  
		}
		setContentView(R.layout.activity_main);
		findViewById(R.id.main_business).setOnClickListener(this);
		findViewById(R.id.official_btn).setOnClickListener(this);
		imageView = (CircleImageView)findViewById(R.id.avatar_image);
		nickname = (TextView)findViewById(R.id.nickname);
		nickId = (TextView)findViewById(R.id.nick_id);
		RequestVolley.post(request, ContentUtil.Host
				+ "fun=getUserinfo", MainActivity.this);
		updateView();
		checkVersion();
	}
	private void updateView()
	{
		if(!TextUtils.isEmpty(ClientInfoConfig.getInstance(getBaseContext()).getNickName()))
			nickname.setText(""+ClientInfoConfig.getInstance(getBaseContext()).getNickName());
		if(!TextUtils.isEmpty(ClientInfoConfig.getInstance(getBaseContext()).getUserId()))
			nickId.setText("ID:"+ClientInfoConfig.getInstance(getBaseContext()).getUserId());
		if(!TextUtils.isEmpty(ClientInfoConfig.getInstance(getBaseContext()).getAvatar()))
			mImageLoader.loadImage(imageView, ""+ClientInfoConfig.getInstance(getBaseContext()).getAvatar(), true);
		
	}
	private void checkChannel(int key)
	{
		// // 点入操作 没有创建应用
				DRSdk.initialize(this, true, ""+ClientInfoConfig.getInstance(getBaseContext()).getUserId());
				// // 点乐操作
				/** @Description: 2.6新增功能，请在用户登录或者更换账户后立即调用此接口，设置用户ID，这样才能正确的实现点乐非托管货币功能 */
				DevInit.initGoogleContext(this, "95137428e3cfe0a68af2435515bef515");
				DevInit.setCurrentUserID(this, ""+ClientInfoConfig.getInstance(getBaseContext()).getUserId());//
				// // 多盟操作
				// // 初始化多盟积分墙
				DOW.getInstance(this).init(""+ClientInfoConfig.getInstance(getBaseContext()).getUserId(), new DLoadListener() {

					@Override
					public void onSuccess() {
					}

					@Override
					public void onStart() {
					}

					@Override
					public void onLoading() {
					}

					@Override
					public void onFail() {
					}
				});
				// //米迪操作 修改完毕
				// // 初始化积分墙帐号信息
				// AdWall.init(this, "6", "6666666666666666");
				AdWall.init(this, "22867", "jc0iizhfoj14v9ef");
				AdWall.setUserParam(ClientInfoConfig.getInstance(getBaseContext()).getUserId());
				// //万普操作 修改完毕
				// // 初始化统计器，并通过代码设置APP_ID, APP_PID
				AppConnect.getInstance("4d87c8ba2844b7c77c22a7ef70be7dbb", "default",
						this);
				String wx_appid = ClientInfoConfig.getInstance(getBaseContext()).getUserId();//微信开放平台申请审核通过后可获得
				AppConnect.getInstance(this).setWeixinAppId(wx_appid, this);
				// //有米操作 修改完毕
				// // 初始化接口，应用启动的时候调用，参数：appId, appSecret
				// AdManager.getInstance(this)
				// .init("cfdbdd2786ea88ea", "d8edde7d10dd0073");
				AdManager.getInstance(this)
						.init("141aabecd8f7542d", "4eb5cca9097d140c");
				// 如果使用积分广告，请务必调用积分广告的初始化接口:
				OffersManager.getInstance(this).onAppLaunch();
				OffersManager.getInstance(this).setCustomUserId(""+ClientInfoConfig.getInstance(getBaseContext()).getUserId());

		switch (key) {
		case 1:
			DevInit.showOffers(this);
			break;
		case 2:
			Intent intent = new Intent(MainActivity.this, DRActivity.class);
			intent.putExtra(DRSdk.DR_TYPE, DRSdk.DR_OFFER);
			startActivity(intent);
			break;
		case 3:
			OffersManager.getInstance(this).showOffersWall(
					new Interface_ActivityListener() {

						/**
						 * 当积分墙销毁的时候，即积分墙的Activity调用了onDestory的时候回调
						 */
						@Override
						public void onActivityDestroy(Context context) {
//							Toast.makeText(context, "全屏积分墙退出了",
//									Toast.LENGTH_SHORT).show();
						}
					});
			break;
		case 4:
			DOW.getInstance(this).show(this);
			break;
		case 5:
			AppConnect.getInstance(this).showOffers(this,ClientInfoConfig.getInstance(getBaseContext()).getUserId());
			break;
		case 6:

			break;
		case 7:
			AdWall.showAppOffers(MainActivity.this);
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_business:
			startActivity(new Intent(MainActivity.this,HomeActivity.class));
			break;
		case R.id.official_btn:
			Intent intent = new Intent();
			ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setComponent(cmp);
			startActivityForResult(intent, 0);
			break;
		default:
			break;
		}
	}
	@Override
	public void onDismissApps() {
		
	}
	@Override
	public void onShowApps() {
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(!TextUtils.isEmpty(channelType))
			OffersManager.getInstance(this).onAppExit();
	}
	@Override
	public Map<String, String> setParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", "" + ClientInfoConfig.getInstance(getBaseContext()).getUserId());
		map.put("deviceid", "" + DeviceUtil.getDevice(this));
		map.put("platform", "" + android.os.Build.MODEL);
		map.put("oscode", "" + android.os.Build.VERSION.RELEASE);
		String signStr = "userid=" + ClientInfoConfig.getInstance(getBaseContext()).getUserId() + "&deviceid="
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
				updateView();
			} else {
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void failed(String message) {
		
	}
	private void checkVersion() {
		final UpdateHelper helper = new UpdateHelper(this, true,request);


		helper.setCallBack(new OnCallBack() {

			@Override
			public void onSuccess() {
				if (helper.getUpgradeType() == UpdateHelper.UPGRADE_FORCE) {
					helper.showForceUpgradeDialog();
				} else if (helper.getUpgradeType() == UpdateHelper.UPGRADE_NOT_FORCE) {
					helper.showNonUpgradeDialog();
				}
			}

			@Override
			public void onFailed() {

			}
		});

		helper.doCheckUpdate();

	}
}
