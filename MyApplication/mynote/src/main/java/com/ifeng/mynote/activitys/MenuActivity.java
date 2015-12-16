package com.ifeng.mynote.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.freedom.xiaowei.recyclerview.YfListInterface;
import com.freedom.xiaowei.recyclerview.YfListRecyclerView;
import com.ifeng.mynote.BaseActivity;
import com.ifeng.mynote.R;
import com.ifeng.mynote.adapters.MenuAdapter;
import com.ifeng.mynote.bean.AppInfo;
import com.ifeng.mynote.utils.ToastUtils;

import java.util.ArrayList;

public class MenuActivity extends BaseActivity {

    private ImageView mIvShow;
    private Toolbar mTb;
    private CollapsingToolbarLayout mCtl;
    private CoordinatorLayout mCl;
    private YfListRecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mIvShow = (ImageView)findViewById(R.id.iv_show);
        mTb = (Toolbar)findViewById(R.id.tb);
        mCtl = (CollapsingToolbarLayout)findViewById(R.id.ctl);
        mCl = (CoordinatorLayout)findViewById(R.id.cl);
        mList = (YfListRecyclerView)findViewById(R.id.rv);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        initToolbar();
        init();
    }


    private void initToolbar()
    {
        setSupportActionBar(mTb);
        final ActionBar ab = getSupportActionBar();
        if(null != ab)
        {
            ab.setHomeAsUpIndicator(R.drawable.ic_nav_story);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mTb.setTitleTextColor(getResources().getColor((R.color.yf_white)));
        mCtl.setTitle("list recycler view");
        mCtl.setExpandedTitleColor(getResources().getColor(R.color.yf_indigo_accent));
        int height = AppInfo.width/4*3;
        CollapsingToolbarLayout.LayoutParams lp = new CollapsingToolbarLayout.LayoutParams(AppInfo.width,height);
        mIvShow.setLayoutParams(lp);
    }
    private void init()
    {
        ArrayList<String> actions = new ArrayList<>();
        actions.add("Yf list Recycler view");
        actions.add("Recycler List Inside Recycler list");
        actions.add("Yf list Recycler Adapter Demo");
        actions.add("Yf list and Grid Demo");
        MenuAdapter adapter = new MenuAdapter(actions);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mList.setAdapter(adapter);
        adapter.setOnItemClickListener(new YfListInterface.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object o) {
                ToastUtils.show(MenuActivity.this,"helllo");
                if ("Recycler List Inside Recycler list".equals(o.toString())) {
                    startActivity(new Intent(MenuActivity.this, SampleRecyclerInsideRecyclerActivity.class));
                } else if ("Yf list Recycler Adapter Demo".equals(o.toString())) {
                    startActivity(new Intent(MenuActivity.this, SampleAdapterDemoActivity.class));
                } else if ("Yf list and Grid Demo".equals(o.toString())) {
                    startActivity(new Intent(MenuActivity.this, SampleGridActivity.class));
                } else {
                    startActivity(new Intent(MenuActivity.this, SampleActivity.class));
                }
            }
        });



    }

}
