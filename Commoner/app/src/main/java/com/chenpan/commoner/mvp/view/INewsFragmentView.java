package com.chenpan.commoner.mvp.view;

import com.chenpan.commoner.bean.NewsSummary;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public interface INewsFragmentView {

    void setAdapter(List<NewsSummary> data);

    void loadMore(List<NewsSummary> list);

    void onRefreshComplete();

    void onLoadMoreComplete();

    void showSuccess();

    void showEmpty();

    boolean checkNet();

    void showFaild();

    void showNoNet();
}
