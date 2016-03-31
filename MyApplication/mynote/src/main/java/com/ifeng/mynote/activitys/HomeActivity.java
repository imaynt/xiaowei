package com.ifeng.mynote.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ifeng.mynote.BaseActivity;
import com.ifeng.mynote.R;
import com.ifeng.mynote.net.BaseRequest;
import com.ifeng.mynote.utils.DeviceUtil;
import com.ifeng.mynote.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends BaseActivity implements BaseRequest.PostResponseListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);
//        BaseRequest.post(request, "http://211.151.175.158/himoney/api/interface.php?fun=getUserinfo", this);
        startActivity(new Intent(HomeActivity.this, SlideActivity.class));
        BaseRequest.get(request, "http://hbhs.sinaapp.com/getjson1/", this);





    }


    private void initView()
    {

    }

    private void initData()
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Map<String, String> setHeader() {
        return null;
    }

    @Override
    public Map<String, String> setParams() {
        Map<String, String> map = new HashMap<String, String>();
//        map.put("userid", "100029" );
//        map.put("deviceid", "" + DeviceUtil.getDevice(this));
//        map.put("platform", "" + android.os.Build.MODEL);
//        map.put("oscode", "" + android.os.Build.VERSION.RELEASE);
//        String signStr = "userid=100029" + "&deviceid="
//                + DeviceUtil.getDevice(this) + "&platform="
//                + android.os.Build.MODEL + "&oscode="
//                + android.os.Build.VERSION.RELEASE + "||u9Y%)!a1z";
//        map.put("sign", DeviceUtil.GetMD5Code(signStr));
        map.put("page","1");
        return map;
    }

    @Override
    public void success(String result) {
        Log.e("tag",""+result);
        ToastUtils.show(this,"result"+result);
    }

    @Override
    public void failed(String message) {
        ToastUtils.show(this,"result"+message);
    }
}
