package com.chenpan.commoner.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chenpan.commoner.MainActivity;
import com.chenpan.commoner.R;
import com.chenpan.commoner.adapter.PlayPagerAdapter;
import com.chenpan.commoner.base.BaseFragment;
import com.chenpan.commoner.bean.Music;
import com.chenpan.commoner.mvp.presenter.MusicPresenter;
import com.chenpan.commoner.mvp.view.MusicView;
import com.chenpan.commoner.playmusic.MusicPlayMode;
import com.chenpan.commoner.playmusic.PlayModeEnum;
import com.chenpan.commoner.utils.ContextUtils;
import com.chenpan.commoner.utils.CoverLoader;
import com.chenpan.commoner.utils.FileUtils;
import com.chenpan.commoner.utils.ImageUtils;
import com.chenpan.commoner.utils.Preferences;
import com.chenpan.commoner.utils.SystemUtils;
import com.chenpan.commoner.utils.ToastFactory;
import com.chenpan.commoner.widget.PlayerDiscView;
import com.chenpan.commoner.widget.musicview.IndicatorLayout;
import com.chenpan.commoner.widget.musicview.LrcView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/1.
 */
public class MusicFragment extends BaseFragment<MusicView, MusicPresenter> {
    PlayerDiscView mPlayerDiscView;

    /**
     * 背景
     */
    @Bind(R.id.iv_play_page_bg)
    ImageView ivPlayPageBg;
    /**
     * 返回
     */
    @Bind(R.id.iv_back)
    ImageView ivBack;
    /**
     * 歌曲
     */
    @Bind(R.id.tv_title)

    TextView tvTitle;
    /**
     * 作者
     */
    @Bind(R.id.tv_artist)
    TextView tvArtist;
    /**
     * 滑动viewpager
     */
    @Bind(R.id.vp_play_page)
    ViewPager vpPlayPage;
    @Bind(R.id.il_indicator)
    IndicatorLayout ilIndicator;
    /**
     * 当前播放时间
     */
    @Bind(R.id.tv_current_time)
    TextView tvCurrentTime;
    /**
     * 进度条
     */
    @Bind(R.id.sb_progress)
    SeekBar sbProgress;
    /**
     * 总时间
     */
    @Bind(R.id.tv_total_time)
    TextView tvTotalTime;
    /**
     * 播放模式
     */
    @Bind(R.id.iv_mode)
    ImageView ivMode;
    /**
     * 上一曲
     */
    @Bind(R.id.iv_prev)
    ImageView ivPrev;
    /**
     * 播放暂停
     */
    @Bind(R.id.iv_play)
    ImageView ivPlay;
    /**
     * 下一曲
     */
    @Bind(R.id.iv_next)
    ImageView ivNext;
    /**
     * 播放控件布局容器
     */
    @Bind(R.id.ll_content)
    LinearLayout llContent;

    private LrcView mLrcViewSingle;
    private LrcView mLrcViewFull;
    private SeekBar sbVolume;
    private AudioManager mAudioManager;
    private List<View> mViewPagerContent;
    private int mLastProgress;

    @Override
    public MusicPresenter createPresenter() {
        return new MusicPresenter();
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        initSystemBar();
        initViewPager();
        ilIndicator.create(mViewPagerContent.size());
        initPlayMode();
        onChange(getPlayService().getPlayingMusic());
        ivBack.setOnClickListener(listener);
        ivMode.setOnClickListener(listener);
        ivPlay.setOnClickListener(listener);
        ivPrev.setOnClickListener(listener);
        ivNext.setOnClickListener(listener);
        sbProgress.setOnSeekBarChangeListener(seekBarChangeListener);
        sbVolume.setOnSeekBarChangeListener(seekBarChangeListener);
        vpPlayPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ilIndicator.setCurrent(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
private  SeekBar.OnSeekBarChangeListener seekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == sbProgress) {
            if (getPlayService().isPlaying() || getPlayService().isPause()) {
                int progress = seekBar.getProgress();
                getPlayService().seekTo(progress);
                mLrcViewSingle.onDrag(progress);
                mLrcViewFull.onDrag(progress);
                tvCurrentTime.setText(formatTime(progress));
                mLastProgress = progress;
            } else {
                seekBar.setProgress(0);
            }
        } else if (seekBar == sbVolume) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(),
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        }
    }
};

    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    onBackPressed();
                    break;
                case R.id.iv_mode:
                    switchPlayMode();
                    break;
                case R.id.iv_play:
                    play();
                    break;
                case R.id.iv_next:
                    next();
                    break;
                case R.id.iv_prev:
                    prev();
                    break;
            }
        }
    };
    public void onChange(Music music) {
        onPlay(music);
    }

    private void onPlay(Music music) {
        if (music == null) {
            return;
        }
        tvTitle.setText(music.getTitle());
        tvArtist.setText(music.getArtist());
        sbProgress.setMax((int) music.getDuration());
        sbProgress.setProgress(0);
        mLastProgress = 0;
        tvCurrentTime.setText(R.string.play_time_start);
        tvTotalTime.setText(formatTime(music.getDuration()));
        setCoverAndBg(music);
        setLrc(music);
        if (getPlayService().isPlaying()) {
            ivPlay.setSelected(true);
            mPlayerDiscView.startPlay();
        } else {
            ivPlay.setSelected(false);
            mPlayerDiscView.pause();
        }
    }

    /**
     * 更新播放进度
     */
    public void onPublish(int progress) {
        sbProgress.setProgress(progress);
        if (mLrcViewSingle.hasLrc()) {
            mLrcViewSingle.updateTime(progress);
            mLrcViewFull.updateTime(progress);
        }
        //更新当前播放时间
        if (progress - mLastProgress >= 1000) {
            tvCurrentTime.setText(formatTime(progress));
            mLastProgress = progress;
        }
    }


    public void onPlayerPause() {
        ivPlay.setSelected(false);
        mPlayerDiscView.pause();
    }

    public void onPlayerResume() {
        ivPlay.setSelected(true);
        mPlayerDiscView.startPlay();
    }
    private void setCoverAndBg(Music music) {

        if (music.getType() == Music.Type.LOCAL) {
           // mAlbumCoverView.setCoverBitmap(CoverLoader.getInstance().loadRound(music.getCoverUri()));
            ivPlayPageBg.setImageBitmap(CoverLoader.getInstance().loadBlur(music.getCoverUri()));
        } else {
            if (music.getCover() == null) {
               // mAlbumCoverView.setCoverBitmap(CoverLoader.getInstance().loadRound(null));
                ivPlayPageBg.setImageResource(R.drawable.play_page_default_bg);
            } else {
                Bitmap cover = ImageUtils.resizeImage(music.getCover(), ContextUtils.getScreenWidth() / 2, ContextUtils.getScreenWidth() / 2);
                cover = ImageUtils.createCircleImage(cover);
              //  mAlbumCoverView.setCoverBitmap(cover);
                Bitmap bg = ImageUtils.blur(music.getCover(), ImageUtils.BLUR_RADIUS);
                ivPlayPageBg.setImageBitmap(bg);
            }
        }
      /*  Bitmap bitmap = ImageBlurManager.doBlurJniArray(BitmapFactory.decodeResource(getResources(),
                        R.drawable.player_bg),
                BLUR_RADIUS,
                false);
        mBackgroundImage.setImageBitmap(bitmap);

        mPlayerCtrlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mMusicsPresenter.onPausePlay();
                } else {
                    mMusicsPresenter.onRePlay();
                }
            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(MusicPlayMode.VOLUME_CHANGED_ACTION);
        getActivity().registerReceiver(mVolumeReceiver, filter);
    }
    /**
     * 沉浸式状态栏
     */
    private void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int top = ContextUtils.getSystemBarHeight(getActivity());
            llContent.setPadding(0, top, 0, 0);
        }
    }
    private void initPlayMode() {
        int mode = Preferences.getPlayMode();
        ivMode.setImageLevel(mode);
    }

    private void initViewPager() {
        View coverView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_play_page_cover, null);
        View lrcView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_play_page_lrc, null);
        mPlayerDiscView = (PlayerDiscView) coverView.findViewById(R.id.album_cover_view);
        mLrcViewSingle = (LrcView) coverView.findViewById(R.id.lrc_view_single);
        mLrcViewFull = (LrcView) lrcView.findViewById(R.id.lrc_view_full);
        sbVolume = (SeekBar) lrcView.findViewById(R.id.sb_volume);
       // mAlbumCoverView.initNeedle(getPlayService().isPlaying());
        initVolume();

        mViewPagerContent = new ArrayList<>(2);
        mViewPagerContent.add(coverView);
        mViewPagerContent.add(lrcView);
        vpPlayPage.setAdapter(new PlayPagerAdapter(mViewPagerContent));
    }
    private void initVolume() {
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        sbVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_musics;
    }
    private void play() {
        getPlayService().playPause();
    }

    private void next() {
        getPlayService().next();
    }

    private void prev() {
        getPlayService().prev();
    }
    private void switchPlayMode() {
        PlayModeEnum mode = PlayModeEnum.valueOf(Preferences.getPlayMode());
        switch (mode) {
            case LOOP:
                mode = PlayModeEnum.SHUFFLE;
                ToastFactory.show(R.string.mode_shuffle);
                break;
            case SHUFFLE:
                mode = PlayModeEnum.ONE;
                ToastFactory.show(R.string.mode_one);
                break;
            case ONE:
                mode = PlayModeEnum.LOOP;
                ToastFactory.show(R.string.mode_loop);
                break;
        }
        Preferences.savePlayMode(mode.value());
        initPlayMode();
    }
   /* private void setCoverAndBg(Music music) {
        if (music.getType() == Music.Type.LOCAL) {
            mAlbumCoverView.setCoverBitmap(CoverLoader.getInstance().loadRound(music.getCoverUri()));
            ivPlayingBg.setImageBitmap(CoverLoader.getInstance().loadBlur(music.getCoverUri()));
        } else {
            if (music.getCover() == null) {
                mAlbumCoverView.setCoverBitmap(CoverLoader.getInstance().loadRound(null));
                ivPlayingBg.setImageResource(R.drawable.play_page_default_bg);
            } else {
                Bitmap cover = ImageUtils.resizeImage(music.getCover(), ScreenUtils.getScreenWidth() / 2, ScreenUtils.getScreenWidth() / 2);
                cover = ImageUtils.createCircleImage(cover);
                mAlbumCoverView.setCoverBitmap(cover);
                Bitmap bg = ImageUtils.blur(music.getCover(), ImageUtils.BLUR_RADIUS);
                ivPlayingBg.setImageBitmap(bg);
            }
        }
    }*/
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
    private void setLrc(final Music music) {
        if (music.getType() == Music.Type.LOCAL) {
            String lrcPath = FileUtils.getLrcFilePath(music);
            if (new File(lrcPath).exists()) {
                loadLrc(lrcPath);
            } else {
              /*  new SearchLrc(music.getArtist(), music.getTitle()) {
                    @Override
                    public void onPrepare() {
                        mLrcViewSingle.searchLrc();
                        mLrcViewFull.searchLrc();
                        // 设置tag防止歌词下载完成后已切换歌曲
                        mLrcViewSingle.setTag(music);
                    }

                    @Override
                    public void onFinish(@Nullable String lrcPath) {
                        if (mLrcViewSingle.getTag() == music) {
                            loadLrc(lrcPath);
                        }
                    }
                }.execute();*/
            }
        } else {
            String lrcPath = FileUtils.getLrcDir() + FileUtils.getLrcFileName(music.getArtist(), music.getTitle());
            loadLrc(lrcPath);
        }
    }
    private void loadLrc(String path) {
        mLrcViewSingle.loadLrc(path);
        mLrcViewFull.loadLrc(path);
        // 清除tag
        mLrcViewSingle.setTag(null);
    }
    private String formatTime(long time) {
        return SystemUtils.formatTime("mm:ss", time);
    }

    private BroadcastReceiver mVolumeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            sbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        }
    };

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mVolumeReceiver);
        super.onDestroy();
    }
    private void onBackPressed() {
        ((MainActivity)getActivity()).hidePlayingFragment();
       // ivBack.setEnabled(false);
    }
}
