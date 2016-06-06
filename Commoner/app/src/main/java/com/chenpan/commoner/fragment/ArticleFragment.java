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
import com.chenpan.commoner.network.NetWorkUtil;
import com.chenpan.commoner.widget.load.LoadingView;
import com.chenpan.commoner.widget.load.OnRetryListener;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    @Bind(R.id.fl_loading)
    LoadingView flLoading;
    /**
     * 访问的网址  这里盗链开始
     */
    private String url;
    /**
     * 网页上的当前页数
     */
    private int page = 0;
    private int pageNo = 0;
    private final int pageSize = 30;
    /**
     * 封装传递到网络的数据
     */
    private Map<String, String> params = new TreeMap<>();


    @Override
    public ArticleFragmentPresenter createPresenter() {
        return new ArticleFragmentPresenter();
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        params.put("url", url);
        params.put("page", String.valueOf(page));
        if (TextUtils.isEmpty(url) || mPresenter == null || !(mPresenter instanceof ArticleFragmentPresenter)) {
            return;
        }
        flLoading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.drawable.note_empty).withBtnEmptyEnnable(false)
                .withErrorIco(R.drawable.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.drawable.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                mPresenter.getArticleList(getActivity(), url, "getarticle", params);
            }
        }).build();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                params.put("url", url);
                params.put("page", String.valueOf(page));
                mPresenter.getArticleList(getActivity(), url, "getarticle", params);
            }
        });

        mPresenter.getArticleList(getActivity(), url, "getarticle", params);

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
        return NetWorkUtil.isNetWorkConnected(mContext);
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




}
