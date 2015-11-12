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

import java.util.Map;

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

    }

    @Override
    public void failed(String message) {

    }
}
