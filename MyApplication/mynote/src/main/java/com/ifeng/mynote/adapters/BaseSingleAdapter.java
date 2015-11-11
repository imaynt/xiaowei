package com.ifeng.mynote.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by liwei5 on 2015/11/10.
 */
public class BaseSingleAdapter<T> extends BaseAdapter {
    private ArrayList<T> mList;
    public BaseSingleAdapter(ArrayList<T> list)
    {
        this.mList = list;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

