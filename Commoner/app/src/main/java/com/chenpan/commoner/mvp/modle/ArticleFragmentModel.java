package com.chenpan.commoner.mvp.modle;

import android.content.Context;

import com.chenpan.commoner.bean.ArticleBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/3.
 */
public interface ArticleFragmentModel {

    void parserArticle(Context  context,String url,String tag, MyCallback<List<ArticleBean>> mCallback);


}
