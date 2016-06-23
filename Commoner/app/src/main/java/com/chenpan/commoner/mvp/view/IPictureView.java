package com.chenpan.commoner.mvp.view;

import com.chenpan.commoner.bean.PictureBean;
import com.chenpan.commoner.bean.PictureBeanBaiDu;
import com.chenpan.commoner.bean.PictureBeanOther;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface IPictureView {

    void setAdapterBaidu(List<PictureBeanBaiDu> list);
    void setAdapterother(List<PictureBeanOther> list);
    void loadMoreBaidu(List<PictureBeanBaiDu> list);
    void loadMoreOther(List<PictureBeanOther> list);

    void onRefreshComplete();

    void onLoadMoreComplete();

    void showSuccess();

    void showEmpty();

    boolean checkNet();

    void showFaild();

    void showNoNet();
}
