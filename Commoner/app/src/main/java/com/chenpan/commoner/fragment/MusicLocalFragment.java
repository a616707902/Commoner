package com.chenpan.commoner.fragment;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chenpan.commoner.MainActivity;
import com.chenpan.commoner.R;
import com.chenpan.commoner.adapter.LocalMusicAdapter;
import com.chenpan.commoner.base.BaseFragment;
import com.chenpan.commoner.base.MyApplication;
import com.chenpan.commoner.bean.Music;
import com.chenpan.commoner.mvp.presenter.MusicLocalPresenter;
import com.chenpan.commoner.mvp.view.IMusicLocalView;
import com.chenpan.commoner.receiver.RemoteControlReceiver;
import com.chenpan.commoner.service.OnPlayerEventListener;
import com.chenpan.commoner.service.PlayService;
import com.chenpan.commoner.utils.OnMoreClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/6.
 * 本地音乐
 */
public class MusicLocalFragment extends BaseFragment<IMusicLocalView, MusicLocalPresenter> implements IMusicLocalView {
    @Bind(R.id.lv_local_music)
    ListView lvLocalMusic;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;

    private PlayService mPlayService;


    protected Handler mHandler;

    private LocalMusicAdapter mAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mPlayService = ((MainActivity) activity).getPlayService();
        }
    }

    @Override
    public MusicLocalPresenter createPresenter() {
        return new MusicLocalPresenter();
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        mAdapter = new LocalMusicAdapter();
        mAdapter.setOnMoreClickListener(new OnMoreClickListener() {
            @Override
            public void onMoreClick(int position) {
                final Music music = PlayService.getMusicList().get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle(music.getTitle());
                int itemsId = position == mPlayService.getPlayingPosition() ? R.array.local_music_dialog_without_delete : R.array.local_music_dialog;
                dialog.setItems(itemsId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            /*case 0:// 分享
                                shareMusic(music);
                                break;
                            case 1:// 设为铃声
                                setRingtone(music);
                                break;
                            case 2:// 查看歌曲信息
                                musicInfo(music);
                                break;
                            case 3:// 删除
                                deleteMusic(music);
                                break;*/
                        }
                    }
                });
                dialog.show();
            }
        });
        lvLocalMusic.setAdapter(mAdapter);
        if (mPlayService.getPlayingMusic() != null && mPlayService.getPlayingMusic().getType() == Music.Type.LOCAL) {
            lvLocalMusic.setSelection(mPlayService.getPlayingPosition());
        }
        lvLocalMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getPlayService().play(position);
                mAdapter.updatePlayingPosition(getPlayService());
                mAdapter.notifyDataSetChanged();
            }
        });

        updateView();
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        getActivity().registerReceiver(mDownloadReceiver, filter);

    }



    @Override
    public int getContentLayout() {
        return R.layout.fragment_local_music;
    }

    private void updateView() {
        if (PlayService.getMusicList().isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
        mAdapter.updatePlayingPosition(mPlayService);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {

    }


    private BroadcastReceiver mDownloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            String title = MyApplication.getInstance().getDownloadList().get(id);
            if (TextUtils.isEmpty(title)) {
                return;
            }
            // 由于系统扫描音乐是异步执行，因此延迟刷新音乐列表
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isAdded()) {
                        return;
                    }
                    mPlayService.updateMusicList();
                    updateView();
                }
            }, 500);
        }
    };
    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mDownloadReceiver);
        super.onDestroy();
    }

}
