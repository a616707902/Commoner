package com.chenpan.commoner.mvp.modle;

import android.content.Context;

import com.chenpan.commoner.bean.ArticleBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/17.
 */
public interface ArticleModel {
    void parserArticle(Context context,String url,String tag, Callback<String> mCallback);
    interface Callback<T> {
        public void onSccuss(T data);

        public void onFaild();
    }

}
