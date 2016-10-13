package com.chenpan.commoner.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chenpan.commoner.NewsDetailActivity;
import com.chenpan.commoner.NewsPhotoDetailActivity;
import com.chenpan.commoner.R;
import com.chenpan.commoner.adapter.NewsListAdapter;
import com.chenpan.commoner.base.BaseFragment;
import com.chenpan.commoner.bean.NewsPhotoDetail;
import com.chenpan.commoner.bean.NewsSummary;
import com.chenpan.commoner.common.Constants;
import com.chenpan.commoner.mvp.presenter.NewsFragmentPresenter;
import com.chenpan.commoner.mvp.view.INewsFragmentView;
import com.chenpan.commoner.network.NetWorkUtil;
import com.chenpan.commoner.widget.load.LoadingState;
import com.chenpan.commoner.widget.load.LoadingView;
import com.chenpan.commoner.widget.load.OnRetryListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;

/**@类名: NewsFragment
* @功能描述:
* @作者:chepan
* @时间: 2016/10/10
* @版权申明:陈攀
* @最后修改者:
* @最后修改内容:
*/
public class NewsListFragment extends BaseFragment<INewsFragmentView, NewsFragmentPresenter> implements INewsFragmentView {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.fl_loading)
    LoadingView flLoading;
    private String type;
    private NewsListAdapter mAdapter;
    private boolean canLoadMore = true;
    LinearLayoutManager mLayoutManager;
    /**
     * 正在加载
     */
    private boolean isLoadingMore;
    private int pageNo = 0;
    private int page=0;
    private final int pageSize = 20;
    /**
     * 封装传递到网络的数据
     */
    private Map<String, String> params = new TreeMap<>();
    /**
     * @return
     */
    @Override
    public NewsFragmentPresenter createPresenter() {
        return new NewsFragmentPresenter();
    }

  
    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        params.put("pageNO", String.valueOf(pageNo));
        params.put("page", String.valueOf(page));
        if (TextUtils.isEmpty(type) || mPresenter == null || !(mPresenter instanceof NewsFragmentPresenter)) {
            return;
        }
        flLoading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.drawable.note_empty).withBtnEmptyEnnable(false)
                .withErrorIco(R.drawable.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.drawable.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                mPresenter.getNewsList(getActivity(), type, params);
            }
        }).build();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                params.put("pageNO", String.valueOf(0));
                params.put("page", String.valueOf(0));
                mPresenter.getNewsList(getActivity(), type, params);
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
                NewsListFragment.this.onScrolled(recyclerView, dx, dy);
            }
        });

        mPresenter.getNewsList(getActivity(), type, params);
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

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }


    @Override
    public void setAdapter(List<NewsSummary> data) {
        pageNo += 20;
        if (recyclerView == null) return;
        if (mAdapter == null) {
            mAdapter = new NewsListAdapter(data);
            mAdapter.setOnItemClickListener(new NewsListAdapter.OnNewsListItemClickListener() {
                @Override
                public void onItemClick(View view, int position, boolean isPhoto) {
                    if (isPhoto) {
                        NewsPhotoDetail newsPhotoDetail = getPhotoDetail(position);
                        goToPhotoDetailActivity(newsPhotoDetail);
                    } else {
                        goToNewsDetailActivity(view, position);
                    }
                }

                @Override
                public void onItemClick(View view, int position) {

                }
            });
            recyclerView.setAdapter(mAdapter);
        } else {
            if ((mAdapter.getItem(0) == null) && (data.size() == 0))
                return;
            if ((mAdapter.getItem(0) == null) || (data.size() == 0) || (!((NewsSummary) mAdapter.getItem(0)).getTitle().equals(data.get(0).getTitle())))
                mAdapter.setmDatas(data);
        }
    }
    private void goToPhotoDetailActivity(NewsPhotoDetail newsPhotoDetail) {
        Intent intent = new Intent(getActivity(), NewsPhotoDetailActivity.class);
        intent.putExtra(Constants.PHOTO_DETAIL, newsPhotoDetail);
        startActivity(intent);
    }

    private void goToNewsDetailActivity(View view, int position) {
        Intent intent = setIntent(position);
        NstartActivity(view, intent);
    }
    private Intent setIntent(int position) {
        List<NewsSummary> newsSummaryList = mAdapter.getList();

        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra(Constants.NEWS_POST_ID, newsSummaryList.get(position).getPostid());
        intent.putExtra(Constants.NEWS_IMG_RES, newsSummaryList.get(position).getImgsrc());
        return intent;
    }

    private void NstartActivity(View view, Intent intent) {
        ImageView newsSummaryPhotoIv = (ImageView) view.findViewById(R.id.news_summary_photo_iv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), newsSummaryPhotoIv, Constants.TRANSITION_ANIMATION_NEWS_PHOTOS);
            ActivityCompat.startActivity(getActivity(),intent, options.toBundle());
        } else {
/*            ActivityOptionsCompat.makeCustomAnimation(this,
                    R.anim.slide_bottom_in, R.anim.slide_bottom_out);
            这个我感觉没什么用处，类似于
            overridePendingTransition(R.anim.slide_bottom_in, android.R.anim.fade_out);*/

/*            ActivityOptionsCompat.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY)
            这个方法可以用于4.x上，是将一个小块的Bitmpat进行拉伸的动画。*/

            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    }
    @Override
    public void loadMore(List<NewsSummary> list) {
        if (recyclerView != null && mAdapter != null && list != null) {
            if (list.size() < pageSize)
                canLoadMore = false;
            if (list.size() <= 0) {
                return;
            }
            mAdapter.addMore(list);
            pageNo += 20;
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
    private void loadPage() {
        params.put("page", String.valueOf(++page));
        params.put("pageNO", String.valueOf(pageNo));
        mPresenter.getNewsList(getActivity(), type, params);
    }
    private NewsPhotoDetail getPhotoDetail(int position) {
        NewsSummary newsSummary = mAdapter.getList().get(position);
        NewsPhotoDetail newsPhotoDetail = new NewsPhotoDetail();
        newsPhotoDetail.setTitle(newsSummary.getTitle());
        setPictures(newsSummary, newsPhotoDetail);
        return newsPhotoDetail;
    }
    private void setPictures(NewsSummary newsSummary, NewsPhotoDetail newsPhotoDetail) {
        List<NewsPhotoDetail.Picture> pictureList = new ArrayList<>();

        if (newsSummary.getAds() != null) {
            for (NewsSummary.AdsBean entity : newsSummary.getAds()) {
                setValuesAndAddToList(pictureList, entity.getTitle(), entity.getImgsrc());
            }
        } else if (newsSummary.getImgextra() != null) {
            for (NewsSummary.ImgextraBean entity : newsSummary.getImgextra()) {
                setValuesAndAddToList(pictureList, null, entity.getImgsrc());
            }
        } else {
            setValuesAndAddToList(pictureList, null, newsSummary.getImgsrc());
        }

        newsPhotoDetail.setPictures(pictureList);
    }
    private void setValuesAndAddToList(List<NewsPhotoDetail.Picture> pictureList, String title, String imgsrc) {
        NewsPhotoDetail.Picture picture = new NewsPhotoDetail.Picture();
        if (title != null) {
            picture.setTitle(title);
        }
        picture.setImgSrc(imgsrc);

        pictureList.add(picture);
    }
}
