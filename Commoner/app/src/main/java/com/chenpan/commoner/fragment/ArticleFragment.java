package com.chenpan.commoner.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chenpan.commoner.R;
import com.chenpan.commoner.base.BaseFragment;
import com.chenpan.commoner.bean.ArticleBean;
import com.chenpan.commoner.mvp.presenter.ArticleFragmentPresenter;
import com.chenpan.commoner.mvp.view.IArticleFragmentView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/2.
 */
public class ArticleFragment extends BaseFragment<IArticleFragmentView, ArticleFragmentPresenter> implements IArticleFragmentView {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    /**
     * 访问的网址  这里盗链开始
     */
    private String url;


    @Override
    public ArticleFragmentPresenter createPresenter() {
        return new ArticleFragmentPresenter();
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        if (TextUtils.isEmpty(url) || mPresenter == null || !(mPresenter instanceof ArticleFragmentPresenter)) {
            return;
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    @Override
    public int getContentLayout() {
        return R.layout.list_fragment;
    }

    @Override
    public void setAdapter(List<ArticleBean> data) {

    }

    @Override
    public void loadMore(List<ArticleBean> list) {

    }

    @Override
    public void onRefreshComplete() {

    }

    @Override
    public void onLoadMoreComplete() {

    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public boolean checkNet() {
        return false;
    }

    @Override
    public void showFaild() {

    }

    @Override
    public void showNoNet() {

    }

    public void setUrl(String url) {
        this.url = url;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
