package com.chenpan.commoner.widget;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.chenpan.commoner.base.MyApplication;

public class URLImageGetter implements Html.ImageGetter {
    private TextView mTextView;
    private int mPicWidth;
    private String mNewsBody;
    private int mPicCount;
    private int mPicTotal;
    private static final String mFilePath = MyApplication.getInstance().getCacheDir().getAbsolutePath();

    public URLImageGetter(TextView textView, String newsBody, int picTotal) {
        mTextView = textView;
        mPicWidth = mTextView.getWidth();
        mNewsBody = newsBody;
        mPicTotal = picTotal;
    }


    @Override
    public Drawable getDrawable(String source) {
        return null;
    }
}
