package com.chenpan.commoner.mvp.presenter;

import android.content.Context;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.NewsDetail;
import com.chenpan.commoner.common.ApiConstans;
import com.chenpan.commoner.mvp.modle.MyCallback;
import com.chenpan.commoner.mvp.modle.NewsDetailModel;
import com.chenpan.commoner.mvp.modle.imp.INewsDetailModel;
import com.chenpan.commoner.mvp.view.INewsDetailView;

/**
 * 作者：chenpan
 * 时间：2016/10/12 16:34
 * 邮箱：616707902@qq.com
 * 描述：
 */

public class NewsDetailPresenter extends BasePresenter<INewsDetailView> {

    private NewsDetailModel mNewsDetailModel = new INewsDetailModel();
    private String mPostId;

    //http://c.m.163.com/nc/article/BG6CGA9M00264N2N/full.html
    public void setPostId(String postId) {
        mPostId = postId;
    }

    public void loadNewsDetail(Context context) {
        StringBuilder url = new StringBuilder();
        url.append(ApiConstans.NEWS_DETAIL).append("/").append(mPostId).append("/").append(ApiConstans.ENDDETAIL_URL);
        mNewsDetailModel.loadNewsDetail(context, url.toString(),mPostId, new MyCallback<NewsDetail>() {
            @Override
            public void onSccuss(NewsDetail data) {
                getWeakView().setNewsDetail(data);
            }

            @Override
            public void onFaild() {
                getWeakView().showFild();
            }
        });
    }
}
