package com.ifeng.mynote.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by liwei5 on 2015/12/17.
 */
public class ExampleViewGroup extends ViewGroup{
    public ExampleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ExampleViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
