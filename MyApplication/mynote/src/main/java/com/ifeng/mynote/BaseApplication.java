package com.ifeng.mynote;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by liwei5 on 2015/11/10.
 */
public class BaseApplication extends Application {
    public static RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
    }
}
