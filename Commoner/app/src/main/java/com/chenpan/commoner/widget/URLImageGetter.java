package com.chenpan.commoner.widget;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.chenpan.commoner.base.MyApplication;
import com.chenpan.commoner.utils.CommonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

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
        Drawable drawable=null;

        if (!CommonUtils.isEmpty(source)) {
            Bitmap bitmap = ImageLoader.getInstance().loadImageSync(source);
            if(bitmap!=null){
               drawable = new BitmapDrawable(bitmap);
            }

        }
        return drawable;
    }
}
