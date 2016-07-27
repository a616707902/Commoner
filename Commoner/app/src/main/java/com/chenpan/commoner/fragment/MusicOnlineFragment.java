package com.chenpan.commoner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chenpan.commoner.OnlineMusicActivity;
import com.chenpan.commoner.R;
import com.chenpan.commoner.adapter.SongListAdapter;
import com.chenpan.commoner.base.BaseFragment;
import com.chenpan.commoner.bean.SongListInfo;
import com.chenpan.commoner.mvp.presenter.MusicOnlinePresenter;
import com.chenpan.commoner.mvp.view.IMusicOnlineView;
import com.chenpan.commoner.network.NetWorkUtil;
import com.chenpan.commoner.utils.Extras;
import com.chenpan.commoner.widget.load.LoadingState;
import com.chenpan.commoner.widget.load.LoadingView;
import com.chenpan.commoner.widget.load.OnRetryListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/6.
 * <p/>
 * 在线音乐
 */
public class MusicOnlineFragment extends BaseFragment<IMusicOnlineView, MusicOnlinePresenter> implements IMusicOnlineView {
    @Bind(R.id.lv_song_list)
    ListView lvSongList;
    @Bind(R.id.fl_loading)
    LoadingView flLoading;
    private List<SongListInfo> mSongLists;

    @Override
    public MusicOnlinePresenter createPresenter() {
        return new MusicOnlinePresenter();
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        flLoading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.drawable.note_empty).withBtnEmptyEnnable(false)
                .withErrorIco(R.drawable.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.drawable.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                mPresenter.getAdapterOnline(getActivity(), getPlayService());
            }
        }).build();
        lvSongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SongListInfo songListInfo = mSongLists.get(position);
                Intent intent = new Intent(getActivity(), OnlineMusicActivity.class);
                intent.putExtra(Extras.MUSIC_LIST_TYPE, songListInfo);
                startActivity(intent);
            }
        });
        mPresenter.getAdapterOnline(getActivity(), getPlayService());
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_song_list;
    }


    @Override
    public void setAdapter(List<SongListInfo> data) {
        mSongLists = data;
        SongListAdapter adapter = new SongListAdapter(mSongLists);
        lvSongList.setAdapter(adapter);
    }

    @Override
    public void showSuccess() {
        flLoading.setVisibility(View.GONE);
        lvSongList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        lvSongList.setVisibility(View.GONE);
        flLoading.setVisibility(View.VISIBLE);
        flLoading.setState(LoadingState.STATE_EMPTY);
    }

    @Override
    public boolean checkNet() {
        return NetWorkUtil.isNetWorkConnected(mContext);
    }

    @Override
    public void showFaild() {
        lvSongList.setVisibility(View.GONE);
        flLoading.setVisibility(View.VISIBLE);
        flLoading.setState(LoadingState.STATE_ERROR);
    }

    @Override
    public void showNoNet() {
        lvSongList.setVisibility(View.GONE);
        flLoading.setVisibility(View.VISIBLE);
        flLoading.setState(LoadingState.STATE_NO_NET);
    }
}
