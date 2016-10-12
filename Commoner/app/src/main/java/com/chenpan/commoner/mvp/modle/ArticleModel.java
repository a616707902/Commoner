package com.chenpan.commoner.mvp.modle;

import android.content.Context;

/**
 * Created by Administrator on 2016/6/17.
 */
public interface ArticleModel {
    void parserArticle(Context context,String url,String tag, MyCallback<String> mCallback);


}
