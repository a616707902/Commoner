package com.chenpan.commoner.mvp.presenter;

import android.content.Context;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.ArticleBean;
import com.chenpan.commoner.mvp.modle.ArticleFragmentModel;
import com.chenpan.commoner.mvp.modle.imp.IArticleFragmentModel;
import com.chenpan.commoner.mvp.view.IArticleFragmentView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/3.
 */
public class ArticleFragmentPresenter extends BasePresenter<IArticleFragmentView> {

    private ArticleFragmentModel articleFragmentModel=new IArticleFragmentModel();

    private void getArticleList(Context context,String url,String tag,Map<String,String> map){
        articleFragmentModel.parserArticle(context,url,tag ,new ArticleFragmentModel.Callback<List<ArticleBean>>() {
            @Override
            public void onSccuss(List<ArticleBean> data) {

            }

            @Override
            public void onFaild() {

            }
        });
    }
}
