package com.chenpan.commoner.mvp.modle;

import android.content.Context;

import com.chenpan.commoner.bean.ArticleBean;
import com.chenpan.commoner.bean.JOnlineMusic;
import com.chenpan.commoner.bean.JOnlineMusicList;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface OnlineMusicModel {
    void parserMusic(String url,String tag,Map<String,String>  param, Callback<JOnlineMusicList> mCallback);

    interface Callback<T> {
        public void onSccuss(T data);

        public void onFaild();
    }
}
