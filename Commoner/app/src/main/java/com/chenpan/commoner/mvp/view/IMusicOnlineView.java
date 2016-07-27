package com.chenpan.commoner.mvp.view;

import com.chenpan.commoner.bean.ArticleBean;
import com.chenpan.commoner.bean.SongListInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6.
 */
public interface IMusicOnlineView {
    void setAdapter( List<SongListInfo> data);



    void showSuccess();

    void showEmpty();

    boolean checkNet();

    void showFaild();

    void showNoNet();
}
