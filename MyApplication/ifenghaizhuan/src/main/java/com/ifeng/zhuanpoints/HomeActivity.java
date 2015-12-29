package com.ifeng.zhuanpoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.midi.wall.sdk.AdWall;
import net.midi.wall.sdk.IAdWallShowAppsNotifier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.dow.android.DOW;
import cn.dow.android.listener.DLoadListener;
import cn.waps.AppConnect;

import com.dlnetwork.DevInit;
import com.ifeng.zhuanpoints.RequestVolley.PostResponseListener;
import com.ifeng.zhuanpoints.adapter.HomeAdapter;
import com.ifeng.zhuanpoints.beans.PointsItem;
import com.ifeng.zhuanpoints.utils.ClientInfoConfig;
import com.ifeng.zhuanpoints.utils.ContentUtil;
import com.ifeng.zhuanpoints.utils.ToastUtils;
import com.yql.dr.sdk.DRSdk;

import dd.ff.aa.AdManager;
import dd.ff.aa.listener.Interface_ActivityListener;
import dd.ff.aa.os.OffersManager;

public class HomeActivity extends BaseActivity implements OnClickListener,
		IAdWallShowAppsNotifier, PostResponseListener, OnItemClickListener {
	private static final String TAG = HomeActivity.class.getSimpleName();

	private ListView mlistview;
	private ArrayList<PointsItem> mList;
	private TextView money;
	private View empty_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mlistview = (ListView) findViewById(R.id.listview);
		money = (TextView) findViewById(R.id.home_money);
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
		//
		// //万普操作 修改完毕
		// // 初始化统计器，并通过代码设置APP_ID, APP_PID
		AppConnect.getInstance("4d87c8ba2844b7c77c22a7ef70be7dbb", "default",
				this);
		// //有米操作 修改完毕
		// // 初始化接口，应用启动的时候调用，参数：appId, appSecret
		// AdManager.getInstance(this)
		// .init("cfdbdd2786ea88ea", "d8edde7d10dd0073");
		AdManager.getInstance(this)
				.init("141aabecd8f7542d", "4eb5cca9097d140c");
		OffersManager.getInstance(this).setCustomUserId(""+ClientInfoConfig.getInstance(getBaseContext()).getUserId());
		OffersManager.setUsingServerCallBack(true);
		// 如果使用积分广告，请务必调用积分广告的初始化接口:
		OffersManager.getInstance(this).onAppLaunch();
		
		// (可选)注册积分监听-随时随地获得积分的变动情况
		// PointsManager.getInstance(this).registerNotify(this);

		// (可选)注册积分订单赚取监听（sdk v4.10版本新增功能）
		// PointsManager.getInstance(this).registerPointsEarnNotify(this);

		findViewById(R.id.back_btn).setOnClickListener(this);
		RequestVolley.post(request, ContentUtil.Host + "fun=channelList",
				HomeActivity.this);
		empty_view = findViewById(R.id.empty_view);
		empty_view.setVisibility(View.GONE);
		mlistview.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_btn:
			finish();
			break;
		case R.id.empty_iv:
			RequestVolley.post(request, ContentUtil.Host + "fun=channelList",
					HomeActivity.this);
			break;
		default:
			break;
		}
	}

	private void updateView() {
		HomeAdapter adapter = new HomeAdapter(getBaseContext(), mList,mImageLoader);
		mlistview.setVisibility(View.VISIBLE);
		empty_view.setVisibility(View.GONE);
		mlistview.setAdapter(adapter);
		mlistview.setOnItemClickListener(this);
	}

	@Override
	public void onShowApps() {
	}

	@Override
	public void onDismissApps() {
	}

	@Override
	public Map<String, String> setParams() {
		Map<String, String> map = new HashMap<String, String>();
		// map.put("os", "1");
		map.put("os", "2");
		return map;
	}

	@Override
	public Map<String, String> setHeader() {
		return null;
	}

	@Override
	public void success(String result) {
		try {
			if (null == mList)
				mList = new ArrayList<PointsItem>();
			JSONObject jsonResult = new JSONObject(result);
			String code = jsonResult.optString("result");
			String msg = jsonResult.optString("msg");
			String exchange = jsonResult.optString("exchange");
			JSONArray content = jsonResult.optJSONArray("content");
			if (code.equals("0")) {
				for (int i = 0; i < content.length(); i++) {
					PointsItem item = new PointsItem();
					JSONObject obj = content.getJSONObject(i);
					item.id = obj.optString("id");
					item.name = obj.optString("name");
					item.icon = obj.optString("icon");
					item.detail = obj.optString("introduce");
					mList.add(item);
				}
				money.setText(exchange + "金币=1元");
				updateView();
			} else {
				ToastUtils.show(getBaseContext(), "" + msg);
				emptyView();
			}

		} catch (JSONException e) {
			e.printStackTrace();
			ToastUtils.show(getBaseContext(), "数据异常");
			emptyView();
		}
	}
	
	@Override
	public void failed(String message) {
		ToastUtils.show(getBaseContext(), "网络错误，请稍后再试！");
		emptyView();
	}
	private void emptyView()
	{
		findViewById(R.id.empty_iv).setOnClickListener(this);
		empty_view.setVisibility(View.VISIBLE);
		mlistview.setVisibility(View.GONE);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		PointsItem item = mList.get(position);
		switch (Integer.parseInt(item.id)) {
		case 1:
			DevInit.showOffers(this);
			break;
		case 2:
			Intent intent = new Intent(HomeActivity.this, DRActivity.class);
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
			AdWall.showAppOffers(HomeActivity.this);
			break;

		default:
			break;

		}
		RequestVolley.post(request, ContentUtil.Host + "fun=accessNotify",
				new PostResponseListener(){

					@Override
					public Map<String, String> setParams() {
						return null;
					}

					@Override
					public Map<String, String> setHeader() {
						return null;
					}

					@Override
					public void success(String result) {

					}

					@Override
					public void failed(String message) {

					}
				});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		OffersManager.getInstance(this).onAppExit();
	}

}
