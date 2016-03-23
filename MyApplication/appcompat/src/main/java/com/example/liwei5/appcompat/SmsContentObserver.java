package com.example.liwei5.appcompat;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liwei5 on 2016/3/22.
 */
public class SmsContentObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    private Context mContext;
    private Handler mHandler;
    public SmsContentObserver(Context context,Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    String code = "";

    @Override
    public void onChange(boolean selfChange,Uri uri) {
        super.onChange(selfChange);
        if (uri.toString().equals("content://sms/raw"))
            return;
        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor c = mContext.getContentResolver().query(inboxUri,null,null,null,"date desc");
        if (null!=c)
        {
            if (c.moveToFirst())
            {
                String address = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));
                Log.e("Debug","helloaddress= "+address+"   body=="+body);
                Pattern pattern = Pattern.compile("(\\d{6})");
                Matcher matcher = pattern.matcher(body);
                if (matcher.find())
                    code = matcher.group(0);
            }
            c.close();
        }

    }
}
