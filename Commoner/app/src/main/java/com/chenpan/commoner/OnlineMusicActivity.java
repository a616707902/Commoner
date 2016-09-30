package com.chenpan.commoner;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenpan.commoner.adapter.OnlineMusicAdapter;
import com.chenpan.commoner.base.BaseActivity;
import com.chenpan.commoner.bean.Constants;
import com.chenpan.commoner.bean.JOnlineMusic;
import com.chenpan.commoner.bean.JOnlineMusicList;
import com.chenpan.commoner.bean.Music;
import com.chenpan.commoner.bean.SongListInfo;
import com.chenpan.commoner.mvp.presenter.OnlineMusicPresenter;
import com.chenpan.commoner.mvp.view.OnlineMusicView;
import com.chenpan.commoner.network.NetWorkUtil;
import com.chenpan.commoner.playmusic.DownloadOnlineMusic;
import com.chenpan.commoner.playmusic.PlayOnlineMusic;
import com.chenpan.commoner.service.PlayService;
import com.chenpan.commoner.utils.ContextUtils;
import com.chenpan.commoner.utils.Extras;
import com.chenpan.commoner.utils.FileUtils;
import com.chenpan.commoner.utils.ImageUtils;
import com.chenpan.commoner.utils.OnMoreClickListener;
import com.chenpan.commoner.utils.ToastFactory;
import com.chenpan.commoner.widget.load.AutoLoadListView;
import com.chenpan.commoner.widget.load.LoadingState;
import com.chenpan.commoner.widget.load.LoadingView;
import com.chenpan.commoner.widget.load.OnLoadListener;
import com.chenpan.commoner.widget.load.OnRetryListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

public class OnlineMusicActivity extends BaseActivity<OnlineMusicView, OnlineMusicPresenter> implements OnlineMusicView {
    @Bind(R.id.lv_online_music_list)
    AutoLoadListView lvOnlineMusic;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fl_loading)
    LoadingView flLoading;

    private View vHeader;
    private SongListInfo mListInfo;
    private JOnlineMusicList mJOnlineMusicList;
    private List<JOnlineMusic> mMusicList;
    private OnlineMusicAdapter mAdapter;
    private PlayService mPlayService;
    private ProgressDialog mProgressDialog;
    private int mOffset = 0;


    @Override
    public void getIntentValue() {
        mListInfo = (SongListInfo) getIntent().getSerializableExtra(Extras.MUSIC_LIST_TYPE);
    }
    @Override
    public void setActionBar() {
        getSupportActionBar().setTitle(mListInfo.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public OnlineMusicPresenter createPresenter() {
        return new OnlineMusicPresenter();
    }

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    private void init() {
        flLoading.withLoadedEmptyText("≥﹏≤ , 连条毛都没有 !").withEmptyIco(R.drawable.note_empty).withBtnEmptyEnnable(false)
                .withErrorIco(R.drawable.ic_chat_empty).withLoadedErrorText("(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ,我家程序猿跑路了 !").withbtnErrorText("去找回她!!!")
                .withLoadedNoNetText("你挡着信号啦o(￣ヘ￣o)☞ᗒᗒ 你走").withNoNetIco(R.drawable.ic_chat_empty).withbtnNoNetText("网弄好了，重试")
                .withLoadingText("加载中...").withOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                onLoad();
            }
        }).build();
        vHeader = LayoutInflater.from(this).inflate(R.layout.activity_online_music_list_header, null);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ContextUtils.dp2px(150));
        vHeader.setLayoutParams(params);
        lvOnlineMusic.addHeaderView(vHeader, null, false);
        mMusicList = new ArrayList<>();
        mAdapter = new OnlineMusicAdapter(mMusicList);
        lvOnlineMusic.setAdapter(mAdapter);
        lvOnlineMusic.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                OnlineMusicActivity.this.onLoad();
            }
        });
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        setListener();
        bindService();
    }

    protected void setListener() {
        lvOnlineMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                play(mMusicList.get(position - 1));
            }
        });
        mAdapter.setOnMoreClickListener(new OnMoreClickListener() {
            @Override
            public void onMoreClick(int position) {
                final JOnlineMusic jOnlineMusic = mMusicList.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(OnlineMusicActivity.this);
                dialog.setTitle(mMusicList.get(position).getTitle());
                String path = FileUtils.getMusicDir() + FileUtils.getMp3FileName(jOnlineMusic.getArtist_name(), jOnlineMusic.getTitle());
                File file = new File(path);
                int itemsId = file.exists() ? R.array.online_music_dialog_without_download : R.array.online_music_dialog;
                dialog.setItems(itemsId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case 0:// 分享
                                share(jOnlineMusic);
                                break;
                            case 1:// 下载
                                download(jOnlineMusic);
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mPlayServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayService = ((PlayService.PlayBinder) service).getService();
            onLoad();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

  /*  private void getMusic(final int offset) {
        OkHttpUtils.get().url(Constants.BASE_URL)
                .addParams(Constants.PARAM_METHOD, Constants.METHOD_GET_MUSIC_LIST)
                .addParams(Constants.PARAM_TYPE, mListInfo.getType())
                .addParams(Constants.PARAM_SIZE, String.valueOf(Constants.MUSIC_LIST_SIZE))
                .addParams(Constants.PARAM_OFFSET, String.valueOf(offset))
                .build()
                .execute(new JsonCallback<JOnlineMusicList>(JOnlineMusicList.class) {
                    @Override
                    public void onResponse(JOnlineMusicList response) {
                        lvOnlineMusic.onLoadComplete();
                        mJOnlineMusicList = response;
                        if (offset == 0 && response == null) {
                            ViewUtils.changeViewState(lvOnlineMusic, llLoading, llLoadFail, LoadStateEnum.LOAD_FAIL);
                            return;
                        } else if (offset == 0) {
                            initHeader();
                            ViewUtils.changeViewState(lvOnlineMusic, llLoading, llLoadFail, LoadStateEnum.LOAD_SUCCESS);
                        }
                        if (response == null || response.getSong_list() == null || response.getSong_list().size() == 0) {
                            lvOnlineMusic.setEnable(false);
                            return;
                        }
                        mOffset += Constants.MUSIC_LIST_SIZE;
                        mMusicList.addAll(response.getSong_list());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        lvOnlineMusic.onLoadComplete();
                        if (e instanceof RuntimeException) {
                            // 歌曲全部加载完成
                            lvOnlineMusic.setEnable(false);
                            return;
                        }
                        if (offset == 0) {
                            ViewUtils.changeViewState(lvOnlineMusic, llLoading, llLoadFail, LoadStateEnum.LOAD_FAIL);
                        } else {
                            ToastUtils.show(R.string.load_fail);
                        }
                    }
                });
    }*/


    private void share(final JOnlineMusic jOnlineMusic) {
//        new ShareOnlineMusic(this, jOnlineMusic.getTitle(), jOnlineMusic.getSong_id()) {
//            @Override
//            public void onPrepare() {
//                mProgressDialog.show();
//            }
//
//            @Override
//            public void onSuccess() {
//                mProgressDialog.cancel();
//            }
//
//            @Override
//            public void onFail(Call call, Exception e) {
//                mProgressDialog.cancel();
//            }
//        }.execute();
    }
    public void onLoad() {
       mPresenter.onLoad(Constants.BASE_URL,mOffset,mListInfo.getType());
    }

    @Override
    public  void initHeader(JOnlineMusicList jOnlineMusicList) {
        mJOnlineMusicList=jOnlineMusicList;
        final ImageView ivHeaderBg = (ImageView) vHeader.findViewById(R.id.iv_header_bg);
        final ImageView ivCover = (ImageView) vHeader.findViewById(R.id.iv_cover);
        TextView tvTitle = (TextView) vHeader.findViewById(R.id.tv_title);
        TextView tvUpdateDate = (TextView) vHeader.findViewById(R.id.tv_update_date);
        TextView tvComment = (TextView) vHeader.findViewById(R.id.tv_comment);
        tvTitle.setText(mJOnlineMusicList.getBillboard().getName());
        tvUpdateDate.setText(getString(R.string.recent_update, mJOnlineMusicList.getBillboard().getUpdate_date()));
        tvComment.setText(mJOnlineMusicList.getBillboard().getComment());
        ImageSize imageSize = new ImageSize(200, 200);
        ImageLoader.getInstance().loadImage(mJOnlineMusicList.getBillboard().getPic_s640(), imageSize,
                ImageUtils.getCoverDisplayOptions(), new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        ivCover.setImageBitmap(loadedImage);
                        ivHeaderBg.setImageBitmap(ImageUtils.blur(loadedImage, ImageUtils.BLUR_RADIUS));
                    }
                });
    }

    @Override
    public void setAdapter(List<JOnlineMusic> musicList) {
        mOffset += Constants.MUSIC_LIST_SIZE;
        mMusicList.addAll(musicList);
        mAdapter.notifyDataSetChanged();
    }

    private void play(JOnlineMusic jOnlineMusic) {
        new PlayOnlineMusic(this, jOnlineMusic) {

            @Override
            public void onPrepare() {
                mProgressDialog.show();
            }

            @Override
            public void onSuccess(Music music) {
                mProgressDialog.cancel();
                mPlayService.play(music);
                ToastFactory.show(getString(R.string.now_play, music.getTitle()));
            }

            @Override
            public void onFail(Call call, Exception e) {
                mProgressDialog.cancel();
                ToastFactory.show(R.string.unable_to_play);
            }
        }.execute();
    }

  /*  private void share(final JOnlineMusic jOnlineMusic) {
        new ShareOnlineMusic(this, jOnlineMusic.getTitle(), jOnlineMusic.getSong_id()) {
            @Override
            public void onPrepare() {
                mProgressDialog.show();
            }

            @Override
            public void onSuccess() {
                mProgressDialog.cancel();
            }

            @Override
            public void onFail(Call call, Exception e) {
                mProgressDialog.cancel();
            }
        }.execute();
    }*/

 /*   private void artistInfo(JOnlineMusic jOnlineMusic) {
        ArtistInfoActivity.start(this, jOnlineMusic.getTing_uid());
    }*/

    private void download(final JOnlineMusic jOnlineMusic) {
        new DownloadOnlineMusic(this, jOnlineMusic) {
            @Override
            public void onPrepare() {
                mProgressDialog.show();
            }

            @Override
            public void onSuccess() {
                mProgressDialog.cancel();
                ToastFactory.show(getString(R.string.now_download, jOnlineMusic.getTitle()));
            }

            @Override
            public void onFail(Call call, Exception e) {
                mProgressDialog.cancel();
                ToastFactory.show(R.string.unable_to_download);
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        unbindService(mPlayServiceConnection);
        super.onDestroy();
    }

    @Override
    public boolean isSetStatusBar() {
        return false;
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        init();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_online_music;
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
    public void showSuccess() {
        lvOnlineMusic.onLoadComplete();
        flLoading.setVisibility(View.GONE);
        lvOnlineMusic.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        lvOnlineMusic.onLoadComplete();
        lvOnlineMusic.setVisibility(View.GONE);
        flLoading.setVisibility(View.VISIBLE);
        flLoading.setState(LoadingState.STATE_EMPTY);
    }

    @Override
    public boolean checkNet() {
        return  NetWorkUtil.isNetWorkConnected(this);
    }

    @Override
    public void showFaild() {
        lvOnlineMusic.onLoadComplete();
        lvOnlineMusic.setVisibility(View.GONE);
        flLoading.setVisibility(View.VISIBLE);
        flLoading.setState(LoadingState.STATE_ERROR);
    }

    @Override
    public void showNoNet() {
        lvOnlineMusic.onLoadComplete();
        lvOnlineMusic.setVisibility(View.GONE);
        flLoading.setVisibility(View.VISIBLE);
        flLoading.setState(LoadingState.STATE_NO_NET);
    }
}
