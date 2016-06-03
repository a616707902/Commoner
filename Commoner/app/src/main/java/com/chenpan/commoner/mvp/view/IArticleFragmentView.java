package com.chenpan.commoner.mvp.view;

import com.chenpan.commoner.bean.ArticleBean;

import java.util.List;

public interface IArticleFragmentView  {
    void setAdapter(List<ArticleBean> data);

    void loadMore(List<ArticleBean> list);

    void onRefreshComplete();

    void onLoadMoreComplete();

    void showSuccess();

    void showEmpty();

    boolean checkNet();

    void showFaild();

    void showNoNet();
}
