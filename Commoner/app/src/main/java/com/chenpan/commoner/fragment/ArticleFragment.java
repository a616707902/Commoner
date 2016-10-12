package com.chenpan.commoner.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chenpan.commoner.R;
import com.chenpan.commoner.adapter.BaseRecyclerAdapter;
import com.chenpan.commoner.base.BaseFragment;
import com.chenpan.commoner.bean.ArticleBean;
import com.chenpan.commoner.holder.ArticleHolder;
import com.chenpan.commoner.mvp.presenter.ArticleFragmentPresenter;
import com.chenpan.commoner.mvp.view.IArticleFragmentView;
import com.chenpan.commoner.network.NetWorkUtil;
import com.chenpan.commoner.widget.load.LoadingState;
import com.chenpan.commoner.widget.load.LoadingView;
import com.chenpan.commoner.widget.load.OnRetryListener;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;

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
    /**
     * 正在加载
     */
    private boolean isLoadingMore;
    private int pageNo = 0;
    private final int pageSize = 30;
    /**
     * 封装传递到网络的数据
     */
    private Map<String, String> params = new TreeMap<>();
    private BaseRecyclerAdapter mAdapter;
    private boolean canLoadMore = true;
    LinearLayoutManager mLayoutManager;

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
                mPresenter.getArticleList(getActivity(), url, url, params);
            }
        }).build();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                params.put("url", url);
                params.put("page", String.valueOf(page));
                mPresenter.getArticleList(getActivity(), url, url, params);
            }
        });
        mLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (canLoadMore) ;
                ArticleFragment.this.onScrolled(recyclerView, dx, dy);
            }
        });

        mPresenter.getArticleList(getActivity(), url, url, params);


    }

    private void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        int totalItemCount = mLayoutManager.getItemCount();
        //lastVisibleItem >= totalItemCount - 2 表示剩下2个item自动加载，各位自由选择
        // dy>0 表示向下滑动
        if (!isLoadingMore && lastVisibleItem >= totalItemCount - 4 && dy > 0) {
            isLoadingMore = true;
            loadPage();//这里多线程也要手动控制isLoadingMore
        }
    }

    @Override
    public int getContentLayout() {
        return R.layout.list_fragment;
    }

    @Override
    public void setAdapter(List<ArticleBean> data) {
        if (recyclerView == null) return;
        pageNo = data.size();
        if (pageNo < pageSize)
            canLoadMore = false;
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter(data, R.layout.fragment_text_item, ArticleHolder.class);
            recyclerView.setAdapter(mAdapter);
        } else {
            if ((mAdapter.getItem(0) == null) && (data.size() == 0))
                return;
            if ((mAdapter.getItem(0) == null) || (data.size() == 0) || (!((ArticleBean) mAdapter.getItem(0)).href.equals(data.get(0).href)))
                mAdapter.setmDatas(data);
        }
    }

    @Override
    public void loadMore(List<ArticleBean> list) {
        if (recyclerView != null && mAdapter != null && list != null) {
            if (list.size() < pageSize)
                canLoadMore = false;
            if (list.size() <= 0) {
                return;
            }
            mAdapter.addAll(list);
            pageNo += list.size();
        }
    }

    @Override
    public void onRefreshComplete() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onLoadMoreComplete() {
        isLoadingMore = false;
    }

    @Override
    public void showSuccess() {
        flLoading.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        swipeRefreshLayout.setVisibility(View.GONE);
        flLoading.setVisibility(View.VISIBLE);
        flLoading.setState(LoadingState.STATE_EMPTY);
    }

    @Override
    public boolean checkNet() {
        return NetWorkUtil.isNetWorkConnected(mContext);
    }

    @Override
    public void showFaild() {
        swipeRefreshLayout.setVisibility(View.GONE);
        flLoading.setVisibility(View.VISIBLE);
        flLoading.setState(LoadingState.STATE_ERROR);
    }

    @Override
    public void showNoNet() {
        swipeRefreshLayout.setVisibility(View.GONE);
        flLoading.setVisibility(View.VISIBLE);
        flLoading.setState(LoadingState.STATE_NO_NET);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void onPause() {
       // flLoading.stop();
        super.onPause();

    }
    private void loadPage() {
       // url= url.replace(".html", "-" + (++page) + ".html");
        params.put("url", url.replace(".html", "-" + (++page) + ".html"));
        params.put("page", String.valueOf(page));
        mPresenter.getArticleList(getActivity(), url,url, params);
    }
}
