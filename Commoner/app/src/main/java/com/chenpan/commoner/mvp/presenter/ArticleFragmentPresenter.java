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

    public void getArticleList(Context context,String url,String tag, final Map<String,String> params){
        if (!getWeakView().checkNet()) {
            getWeakView().onRefreshComplete();
            getWeakView().onLoadMoreComplete();
            getWeakView().showNoNet();
            return;
        }
        articleFragmentModel.parserArticle(context,url,tag ,new ArticleFragmentModel.Callback<List<ArticleBean>>() {
            @Override
            public void onSccuss(List<ArticleBean> data) {
                if (getWeakView() == null) return;
                getWeakView().onRefreshComplete();
                getWeakView().onLoadMoreComplete();
                if ("0".equals(params.get("page"))) {
                    if (data.size() == 0) {
                        getWeakView().showEmpty();
                    } else {
                        getWeakView().setAdapter(data);
                        getWeakView().showSuccess();
                    }
                } else {
                    getWeakView().loadMore(data);
                }
            }

            @Override
            public void onFaild() {
                if (getWeakView() == null) return;
                getWeakView().onRefreshComplete();
                getWeakView().onLoadMoreComplete();
                if ("0".equals(params.get("page"))){
                    getWeakView().showFaild();
                }
            }
        });
    }
}
