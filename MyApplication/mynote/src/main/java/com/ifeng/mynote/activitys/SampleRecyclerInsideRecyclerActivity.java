package com.ifeng.mynote.activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.freedom.xiaowei.recyclerview.YfListRecyclerView;
import com.ifeng.mynote.BaseActivity;
import com.ifeng.mynote.R;
import com.ifeng.mynote.adapters.RcyInRcyOutAdapter;

import java.util.ArrayList;
import java.util.LinkedList;

public class SampleRecyclerInsideRecyclerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_inside_recycler);
        ArrayList<String> mData = new ArrayList<String>();
        for (int i=0;i<5;i++)
        {
            mData.add("item"+i);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (null != ab) {
            ab.setHomeAsUpIndicator(R.drawable.ic_action_back);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        YfListRecyclerView list = (YfListRecyclerView) findViewById(R.id.recycler);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        RcyInRcyOutAdapter adapter = new RcyInRcyOutAdapter(mData);
        list.setAdapter(adapter);
    /*    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
