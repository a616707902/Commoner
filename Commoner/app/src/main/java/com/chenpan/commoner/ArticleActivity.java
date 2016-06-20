package com.chenpan.commoner;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.chenpan.commoner.base.BaseActivity;
import com.chenpan.commoner.bean.ArticleBean;
import com.chenpan.commoner.mvp.presenter.ArticlePresenter;
import com.chenpan.commoner.mvp.view.ArticleView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleActivity extends BaseActivity<ArticleView, ArticlePresenter> implements ArticleView {
    ArticleBean mArticle;
    @Bind(R.id.tv_content)
    TextView tvContent;

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

    }

    @Override
    public void setArticle(String article) {
        if (tvContent == null) return;
        tvContent.setText(Html.fromHtml(article));
    }


}
