package com.chenpan.commoner.mvp.view;

import com.chenpan.commoner.bean.AbsVideoRes;

import java.util.List;

public interface IVideoView  {

    void setAdapter(List<? extends AbsVideoRes> list);

    void loadMore(List<? extends AbsVideoRes> list);

    void onRefreshComplete();

    void onLoadMoreComplete();

    void showSuccess();

    void showFaild();

    void showEmpty();

    boolean checkNet();

    void showNoNet();
}