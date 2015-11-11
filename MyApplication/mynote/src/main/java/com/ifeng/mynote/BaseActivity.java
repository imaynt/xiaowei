package com.ifeng.mynote;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ifeng.mynote.utils.AsyImageLoader;
/*
* 所有activity的父类
* */
public class BaseActivity extends AppCompatActivity {

    protected RequestQueue request;
    public AsyImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = Volley.newRequestQueue(getApplicationContext());
        mImageLoader = new AsyImageLoader(BaseActivity.this);
    }
}
