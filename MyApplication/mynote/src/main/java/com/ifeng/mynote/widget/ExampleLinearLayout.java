package com.ifeng.mynote.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.freedom.xiaowei.recyclerview.util.LogUtil;

/**
 * Created by liwei5 on 2015/12/17.
 */
public class ExampleLinearLayout extends LinearLayout {
    private static final String tag = ExampleLinearLayout.class.getSimpleName();
    public ExampleLinearLayout(Context context) {
        super(context);
        LogUtil.i(tag, "ExampleLinearLayout  context  ");
    }
    public ExampleLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LogUtil.i(tag, "ExampleLinearLayout  context   attrs");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtil.i(tag,"ExampleLinearLayout above onMeasure  widthMeasureSpec="+widthMeasureSpec+",heightMeasureSpec="+heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i(tag, "ExampleLinearLayout below onMeasure  widthMeasureSpec=" + widthMeasureSpec + ",heightMeasureSpec=" + heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtil.i(tag,"ExampleLinearLayout above onLayout  changed="+changed+",left="+l+",top="+t+",right="+r+",bottom="+b);
        super.onLayout(changed, l, t, r, b);
        LogUtil.i(tag, "ExampleLinearLayout below onLayout  changed=" + changed + ",left=" + l + ",top=" + t + ",right=" + r + ",bottom=" + b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.i(tag,"ExampleLinearLayout above onDraw  ");
        super.onDraw(canvas);
        LogUtil.i(tag, "ExampleLinearLayout below onDraw  ");
    }
}
