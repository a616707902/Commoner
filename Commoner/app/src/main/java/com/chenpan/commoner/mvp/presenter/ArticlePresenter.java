package com.chenpan.commoner.mvp.presenter;

import android.content.Context;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.mvp.modle.ArticleModel;
import com.chenpan.commoner.mvp.modle.imp.IArticleModel;
import com.chenpan.commoner.mvp.view.ArticleView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/17.
 */
public class ArticlePresenter extends BasePresenter<ArticleView> {
    ArticleModel articleModel = new IArticleModel();

    public void getcontentArticle(Context context, String Url) {
        articleModel.parserArticle(context, Url, Url, new ArticleModel.Callback<String>() {
            @Override
            public void onSccuss(String data) {
                getWeakView().setArticle(data);
            }

            @Override
            public void onFaild() {
                getWeakView().showFild();
            }
        });
    }
}
