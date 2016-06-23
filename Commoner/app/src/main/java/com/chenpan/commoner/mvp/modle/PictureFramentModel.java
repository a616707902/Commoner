package com.chenpan.commoner.mvp.modle;

import android.content.Context;

import com.chenpan.commoner.bean.ArticleBean;
import com.chenpan.commoner.bean.PictureBean;
import com.chenpan.commoner.bean.PictureBeanBaiDu;
import com.chenpan.commoner.bean.PictureBeanOther;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface PictureFramentModel {

    void parserPictureBaidu(Context context,String url,String tag, Callback<List<PictureBeanBaiDu>> mCallback);
    void parserPictureOther(Context context,String url,String tag, Callback<List<PictureBeanOther>> mCallback);
    interface Callback<T> {
        public void onSccuss(T data);

        public void onFaild();
    }
}
