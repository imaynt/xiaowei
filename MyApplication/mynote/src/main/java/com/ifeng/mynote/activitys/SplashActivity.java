package com.ifeng.mynote.activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ifeng.mynote.BaseActivity;
import com.ifeng.mynote.R;
import com.ifeng.mynote.net.BaseRequest;
import com.ifeng.mynote.utils.ToastUtils;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends BaseActivity implements BaseRequest.PostResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
        BaseRequest.get(request, "http://hbhs.sinaapp.com/getjson1/", this);
//        initCachedThread();
//        initFixedThread();
    }
    private void initCachedThread()
    {
        ExecutorService catchThreadPool = Executors.newCachedThreadPool();
        catchThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("hahh");
            }
        });
    }
    private void initScheduledThread()
    {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {

            }
        },3, TimeUnit.SECONDS);//表示延迟3秒执行
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            }
        },1,3,TimeUnit.SECONDS);//表示1秒后每三秒执行一次。ScheduledExecutorService比Timer更安全，功能更强大，后面会有一篇单独进行对比。
    }
    private void initFixedThread()
    {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("fixthreadPool");
            }
        });
    }
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
        ToastUtils.show(this, "result" + result);
    }

    @Override
    public void failed(String message) {

    }
}
