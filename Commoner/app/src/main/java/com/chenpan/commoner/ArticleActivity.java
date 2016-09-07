package com.chenpan.commoner;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chenpan.commoner.base.BaseActivity;
import com.chenpan.commoner.bean.ArticleBean;
import com.chenpan.commoner.mvp.presenter.ArticlePresenter;
import com.chenpan.commoner.mvp.view.ArticleView;
import com.chenpan.commoner.widget.load.LoadingState;
import com.chenpan.commoner.widget.load.LoadingView;
import com.chenpan.commoner.widget.load.OnRetryListener;
import com.chenpan.commoner.widget.scrollview.OverScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleActivity extends BaseActivity<ArticleView, ArticlePresenter> implements ArticleView {
    ArticleBean mArticle;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.fl_loading)
    LoadingView flLoading;
    @Bind(R.id.text)
    OverScrollView text;

    @Override
    public ArticlePresenter createPresenter() {
        return new ArticlePresenter();
    }

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        flLoading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.drawable.note_empty).withBtnEmptyEnnable(false)
                .withErrorIco(R.drawable.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.drawable.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {

            }
        }).build();
        if (mPresenter == null) return;
        mPresenter.getcontentArticle(this, mArticle.href);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_article;
    }

    @Override
    public void getIntentValue() {
        mArticle = getIntent().getParcelableExtra("article");
    }

    @Override
    public void setActionBar() {
        getSupportActionBar().setTitle(mArticle.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
      //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

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

    @Override
    public void showFild() {
        text.setVisibility(View.GONE);
        flLoading.setVisibility(View.VISIBLE);
        flLoading.setState(LoadingState.STATE_EMPTY);
    }

    @Override
    public void setArticle(String article) {
        flLoading.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);

        if (tvContent == null) return;
        tvContent.setText(Html.fromHtml(article));
    }


}
