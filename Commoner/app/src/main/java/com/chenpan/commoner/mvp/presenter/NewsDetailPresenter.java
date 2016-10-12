package com.chenpan.commoner.mvp.presenter;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.mvp.modle.NewsDetailModel;
import com.chenpan.commoner.mvp.modle.imp.INewsDetailModel;
import com.chenpan.commoner.mvp.view.INewsDetailView;

/**
 * 作者：chenpan
 * 时间：2016/10/12 16:34
 * 邮箱：616707902@qq.com
 * 描述：
 */

public class NewsDetailPresenter extends BasePresenter<INewsDetailView>{

    private NewsDetailModel mNewsDetailModel=new INewsDetailModel();
    private String mPostId;


    public void setPostId(String postId) {
        mPostId = postId;
    }
}
