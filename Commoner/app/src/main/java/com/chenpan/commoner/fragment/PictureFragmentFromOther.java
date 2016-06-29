package com.chenpan.commoner.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenpan.commoner.R;
import com.chenpan.commoner.adapter.BaseRecyclerAdapter;
import com.chenpan.commoner.base.BaseFragment;
import com.chenpan.commoner.bean.PictureBeanBaiDu;
import com.chenpan.commoner.bean.PictureBeanOther;
import com.chenpan.commoner.holder.PictureBaiduHolder;
import com.chenpan.commoner.holder.PictureOtherHolder;
import com.chenpan.commoner.mvp.presenter.PicturePresenterOther;
import com.chenpan.commoner.mvp.view.IPictureView;
import com.chenpan.commoner.network.NetWorkUtil;
import com.chenpan.commoner.widget.PLAImageView;
import com.chenpan.commoner.widget.load.LoadingState;
import com.chenpan.commoner.widget.load.LoadingView;
import com.chenpan.commoner.widget.load.OnRetryListener;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/22.
 */
public class PictureFragmentFromOther extends BaseFragment<IPictureView, PicturePresenterOther> implements IPictureView {

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public PicturePresenterOther createPresenter() {
        return new PicturePresenterOther();
    }

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.fl_loading)
    LoadingView flLoading;
    /**
     * 网络请求数据
     */
    private Map<String, String> params = new TreeMap<>();

    private int page = 0;
    private StaggeredGridLayoutManager mLayoutManager;
    private boolean canLoadMore = true;
    private boolean isLoadingMore;

    private BaseRecyclerAdapter mAdapter;
    private int pageNo;
    private int pageSize = 21;


    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        params.put("url", url);
        params.put("page", String.valueOf(page));
        if (TextUtils.isEmpty(url) || mPresenter == null || !(mPresenter instanceof PicturePresenterOther)) {
            return;
        }
        flLoading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.drawable.note_empty).withBtnEmptyEnnable(false)
                .withErrorIco(R.drawable.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.drawable.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                mPresenter.getPicture(getActivity(), url, params);
            }
        }).build();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                params.put("url", url);
                params.put("page", String.valueOf(page));
                mPresenter.getPicture(getActivity(), url, params);
            }
        });
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//2是列数或者行数，第二个参数是横向还是纵向

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (canLoadMore) ;
                PictureFragmentFromOther.this.onScrolled(recyclerView, dx, dy);
            }
        });

        mPresenter.getPicture(getActivity(), url, params);
    }

    /**
     * 滚动时加载
     *
     * @param recyclerView
     * @param dx
     * @param dy
     */
    private void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int[] visibleItem = mLayoutManager.findLastVisibleItemPositions(null);
        int totalItemCount = mLayoutManager.getItemCount();
        //lastVisibleItem >= totalItemCount - 2 表示剩下2个item自动加载，各位自由选择
        // dy>0 表示向下滑动
        int lastitem = Math.max(visibleItem[0], visibleItem[1]);

        if (!isLoadingMore && lastitem >= totalItemCount - 4 && dy > 0) {
            isLoadingMore = true;
            loadPage();//这里多线程也要手动控制isLoadingMore
        }
    }

    private void loadPage() {

        params.put("page", String.valueOf(++page));
        mPresenter.getPicture(getActivity(), url, params);

    }

    @Override
    public int getContentLayout() {
        return R.layout.list_fragment;
    }

    public void setType(String url) {
        this.url = url;
    }


    @Override
    public void setAdapterBaidu(List<PictureBeanBaiDu> list) {


    }

    @Override
    public void setAdapterother(List<PictureBeanOther> list) {

        if (recyclerView == null) return;
        pageNo = list.size();
        if (pageNo < pageSize)
            canLoadMore = false;
        if (mAdapter == null) {
            mAdapter = new BaseRecyclerAdapter(list, R.layout.fragment_picture_item, PictureOtherHolder.class);
            recyclerView.setAdapter(mAdapter);
        } else {
            if ((mAdapter.getItem(0) == null) && (list.size() == 0))
                return;
            if ((mAdapter.getItem(0) == null) || (list.size() == 0) || (!((PictureBeanBaiDu) mAdapter.getItem(0)).getThumbnailUrl().equals(((PictureBeanOther) list.get(0)).getUrl())))
                mAdapter.setmDatas(list);
        }

    }

    @Override
    public void loadMoreBaidu(List<PictureBeanBaiDu> list) {

    }

    @Override
    public void loadMoreOther(List<PictureBeanOther> list) {
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




}
