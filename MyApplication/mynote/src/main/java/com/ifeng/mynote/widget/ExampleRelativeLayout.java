package com.ifeng.mynote.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.freedom.xiaowei.recyclerview.util.LogUtil;

/**
 * Created by liwei5 on 2015/12/17.
 */
public class ExampleRelativeLayout extends RelativeLayout {
    private static final String tag = ExampleRelativeLayout.class.getSimpleName();
    public ExampleRelativeLayout(Context context) {
        super(context);
        LogUtil.i(tag, "ExampleRelativeLayout  context");
    }
    public ExampleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LogUtil.i(tag, "ExampleRelativeLayout  context   attrs");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtil.i(tag,"ExampleRelativeLayout above onMeasure  widthMeasureSpec="+widthMeasureSpec+",heightMeasureSpec="+heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i(tag, "ExampleRelativeLayout below onMeasure  widthMeasureSpec=" + widthMeasureSpec + ",heightMeasureSpec=" + heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtil.i(tag,"ExampleRelativeLayout above onLayout  changed="+changed+",left="+l+",top="+t+",right="+r+",bottom="+b);
        super.onLayout(changed, l, t, r, b);
        LogUtil.i(tag, "ExampleRelativeLayout below onLayout  changed=" + changed + ",left=" + l + ",top=" + t + ",right=" + r + ",bottom=" + b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.i(tag,"ExampleRelativeLayout above onDraw  ");
        super.onDraw(canvas);
        LogUtil.i(tag, "ExampleRelativeLayout below onDraw  ");
    }
}
