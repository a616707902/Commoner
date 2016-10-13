package com.chenpan.commoner;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chenpan.commoner.base.BaseActivity;
import com.chenpan.commoner.bean.NewsDetail;
import com.chenpan.commoner.common.Constants;
import com.chenpan.commoner.mvp.presenter.NewsDetailPresenter;
import com.chenpan.commoner.mvp.view.INewsDetailView;
import com.chenpan.commoner.utils.CommonUtils;
import com.chenpan.commoner.utils.SystemUtils;
import com.chenpan.commoner.widget.URLImageGetter;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;

public class NewsDetailActivity extends BaseActivity<INewsDetailView, NewsDetailPresenter> implements INewsDetailView {


    @Bind(R.id.news_detail_photo_iv)
    ImageView mNewsDetailPhotoIv;
    @Bind(R.id.mask_view)
    View mMaskView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout mAppBar;
    @Bind(R.id.news_detail_from_tv)
    TextView mNewsDetailFromTv;
    @Bind(R.id.news_detail_body_tv)
    TextView mNewsDetailBodyTv;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    String postId;

    private URLImageGetter mUrlImageGetter;
    private String mNewsTitle;
    private String mShareLink;


    @Override
    public NewsDetailPresenter createPresenter() {
        return new NewsDetailPresenter();
    }

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public boolean isSetStatusBar() {
        return false;
    }

    @Override
    public void getIntentValue() {
        postId = getIntent().getStringExtra(Constants.NEWS_POST_ID);
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        mPresenter.setPostId(postId);
        mPresenter.loadNewsDetail(this);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void setNewsDetail(NewsDetail newsDetail) {
        mShareLink = newsDetail.getShareLink();
        mNewsTitle = newsDetail.getTitle();
        String newsSource = newsDetail.getSource();
        String newsTime = SystemUtils.formatDate(newsDetail.getPtime());
        String newsBody = newsDetail.getBody();
        String NewsImgSrc = getImgSrcs(newsDetail);


        setToolBarLayout(mNewsTitle);
//        mNewsDetailTitleTv.setText(newsTitle);
        mNewsDetailFromTv.setText(getString(R.string.news_from, newsSource, newsTime));
        setNewsDetailPhotoIv(NewsImgSrc);
        setBody(newsDetail, newsBody);
    }

    @Override
    public void showFild() {
        mNewsDetailBodyTv.setText("加载失败");
    }

    private void setNewsDetailPhotoIv(String imgSrc) {
        if (!CommonUtils.isEmpty(imgSrc)) {
            ImageLoader.getInstance().displayImage(imgSrc,mNewsDetailPhotoIv);
        }

    }


    private void setBody(NewsDetail newsDetail, String newsBody) {
        mProgressBar.setVisibility(View.GONE);
        mFab.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.RollIn).playOn(mFab);
        int imgTotal = newsDetail.getImg().size();
      //  if (isShowBody(newsBody, imgTotal)) {
//              mNewsDetailBodyTv.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效,实测经常卡机崩溃
            mUrlImageGetter = new URLImageGetter(mNewsDetailBodyTv, newsBody, imgTotal);
            mNewsDetailBodyTv.setText(Html.fromHtml(newsBody, mUrlImageGetter, null));
//        } else {
//            mNewsDetailBodyTv.setText(Html.fromHtml(newsBody));
//        }
    }


    private void setToolBarLayout(String newsTitle) {

        mToolbarLayout.setTitle(newsTitle);
        mToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        mToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_white));
    }
    private String getImgSrcs(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        String imgSrc;
        if (imgSrcs != null && imgSrcs.size() > 0) {
            imgSrc = imgSrcs.get(0).getSrc();
        } else {
            imgSrc = getIntent().getStringExtra(Constants.NEWS_IMG_RES);
        }
        return imgSrc;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
