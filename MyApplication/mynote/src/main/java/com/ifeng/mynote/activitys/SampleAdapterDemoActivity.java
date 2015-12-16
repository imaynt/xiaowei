package com.ifeng.mynote.activitys;

import android.os.Bundle;

import com.freedom.xiaowei.recyclerview.YfListRecyclerView;
import com.ifeng.mynote.BaseActivity;
import com.ifeng.mynote.R;
import com.ifeng.mynote.adapters.DemoAdapter;

import java.util.ArrayList;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by liwei5 on 2015/12/16.
 */
public class SampleAdapterDemoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_adapter_demo);

        ArrayList<String> mData = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mData.add("item  " + i);
        }

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(mToolbar);

        final ActionBar ab = getSupportActionBar();
        if (null != ab) {
            ab.setHomeAsUpIndicator(R.drawable.ic_action_back);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        YfListRecyclerView mList = (YfListRecyclerView) findViewById(R.id.recycler);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DemoAdapter adapter = new DemoAdapter(mData);
        mList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
