package com.chenpan.commoner;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chenpan.commoner.adapter.ArticleTabAdapter;
import com.chenpan.commoner.adapter.MusicTabAdapter;
import com.chenpan.commoner.adapter.PictureTabAdapter;
import com.chenpan.commoner.base.BaseActivity;
import com.chenpan.commoner.bean.Music;
import com.chenpan.commoner.bean.User;
import com.chenpan.commoner.bean.UserManager;
import com.chenpan.commoner.fragment.MusicFragment;
import com.chenpan.commoner.mvp.presenter.MainPresenter;
import com.chenpan.commoner.mvp.view.MainView;
import com.chenpan.commoner.service.OnPlayerEventListener;
import com.chenpan.commoner.service.PlayService;
import com.chenpan.commoner.utils.CoverLoader;
import com.chenpan.commoner.utils.Extras;
import com.chenpan.commoner.utils.SystemUtils;
import com.example.chenpan.library.AccountHeader;
import com.example.chenpan.library.AccountHeaderBuilder;
import com.example.chenpan.library.Drawer;
import com.example.chenpan.library.DrawerBuilder;
import com.example.chenpan.library.holder.BadgeStyle;
import com.example.chenpan.library.holder.StringHolder;
import com.example.chenpan.library.model.DividerDrawerItem;
import com.example.chenpan.library.model.PrimaryDrawerItem;
import com.example.chenpan.library.model.ProfileDrawerItem;
import com.example.chenpan.library.model.interfaces.IDrawerItem;
import com.example.chenpan.library.model.interfaces.IProfile;
import com.example.chenpan.library.util.RecyclerViewCacheUtil;

import java.util.Arrays;

import butterknife.Bind;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView, OnPlayerEventListener, View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.iv_play_bar_cover)
    ImageView ivPlayBarCover;
    @Bind(R.id.tv_play_bar_title)
    TextView tvPlayBarTitle;
    @Bind(R.id.tv_play_bar_artist)
    TextView tvPlayBarArtist;
    @Bind(R.id.iv_play_bar_play)
    ImageView ivPlayBarPlay;
    @Bind(R.id.iv_play_bar_next)
    ImageView ivPlayBarNext;
    @Bind(R.id.pb_play_bar)
    ProgressBar pbPlayBar;
    @Bind(R.id.fl_play_bar)
    FrameLayout flPlayBar;


    private boolean ISPLAYING = false;
    private MusicFragment mPlayFragment;
    private boolean isPlayFragmentShow = false;

    /**
     * 音乐播放后台服务
     */
    private PlayService mPlayService;
    /**
     * 侧滑头部
     */
    private AccountHeader headerResult = null;
    /**
     * 侧滑布局
     */
    private Drawer result = null;
    /**
     * 头部布局
     */
    private IProfile profile;
    /**
     * 保存identify
     */
    private int dId = 1;

    @Override
    protected void onResume() {
        super.onResume();
        parseIntent(getIntent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mPlayServiceConnection);
        super.onDestroy();
    }


    @Override
    public void showLogin() {

    }


    @Override
    public void setUserInfo(User user) {
        if (headerResult != null) {
            headerResult.removeProfile(profile);
            profile = new ProfileDrawerItem().withName(user.screen_name).withEmail("朋友，欢迎您回来 !").withIcon(Uri.parse(user.profile_image_url)).withIdentifier(100);
            headerResult.addProfiles(profile);
        }
    }

    private boolean checkService() {
        return SystemUtils.isServiceRunning(this, PlayService.class);


    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        boolean istrue = checkService();
        bindService();
        setLisner();
        setupTextViewPager();
//        setupPictureViewPager();
        if (UserManager.getInstance().isLogin()) {
            profile = new ProfileDrawerItem().withName(UserManager.getInstance().getUser().screen_name).withIcon(Uri.parse(UserManager.getInstance().getUser().profile_image_url)).withIdentifier(100);

        } else {
            profile = new ProfileDrawerItem().withName("未登录").withIcon(R.mipmap.ic_launcher).withIdentifier(100);
        }
        headerResult = new AccountHeaderBuilder().withOnlyMainProfileImageVisible(true).withSelectionListEnabled(false)
                .withActivity(this).withHeightDp(230)
                .withHeaderBackground(R.drawable.side_bg)
                .addProfiles(
                        profile
                ).withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        if (UserManager.getInstance().isLogin()) {
                            return true;
                        } else {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);

                            return true;
                        }

                    }
                }).withSavedInstance(savedInstanceState)
                .build();
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.article).withIcon(R.drawable.article).withIdentifier(1).withSelectable(true),
                        new PrimaryDrawerItem().withName(R.string.picture).withIcon(R.drawable.pictureicon).withIdentifier(2).withSelectable(true).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        new PrimaryDrawerItem().withName(R.string.video).withIcon(R.drawable.videoicon).withIdentifier(3).withSelectable(true),
                        new PrimaryDrawerItem().withName(R.string.music).withIcon(R.drawable.musicicon).withIdentifier(4).withSelectable(true),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.skinchange).withIcon(R.drawable.skin).withIdentifier(6).withSelectable(true),
                        new PrimaryDrawerItem().withName(R.string.setting).withIcon(R.drawable.settingicon).withIdentifier(7).withSelectable(false)

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {


                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1 && dId != 1) {
                                dId = 1;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                setupTextViewPager();
                                //               intent = new Intent(MainActivity.this, TopicActivity.class);
                            } else if (drawerItem.getIdentifier() == 2 && dId != 2) {
                                dId = 2;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                setupPictureViewPager();
                            } else if (drawerItem.getIdentifier() == 3 && dId != 3) {
                                dId = 3;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                //  setupTextViewPager();
                            } else if (drawerItem.getIdentifier() == 4) {
                                dId = 4;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                setupMusicViewPager();
                                //                intent = new Intent(MainActivity.this, SmallGameActivity.class);
                            } else if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(MainActivity.this, SkinActivity.class);
                                MainActivity.this.startActivity(intent);
                                return true;
                            } else if (drawerItem.getIdentifier() == 7) {
                                //  intent = new Intent(MainActivity.this, SettingActivity.class);
                                MainActivity.this.startActivity(intent);
                                return true;
                            }
                            if (intent != null) {
                                MainActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();
        setActionBar();
        RecyclerViewCacheUtil.getInstance().withCacheSize(2).init(result);
        if (savedInstanceState == null) {
            // result.setSelection(1, false);

            //set the active profile
            headerResult.setActiveProfile(profile);
        }
        result.updateBadge(7, new StringHolder(10 + ""));
    }

    private void setLisner() {
        if (mPlayService != null && mPlayService.isPlaying()) {
            flPlayBar.setVisibility(View.VISIBLE);
        }
        flPlayBar.setOnClickListener(this);
        ivPlayBarPlay.setOnClickListener(this);
        ivPlayBarNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fl_play_bar:
                showPlayingFragment();
                break;
            case R.id.iv_play_bar_play:
                mPresenter.play();
                break;
            case R.id.iv_play_bar_next:
                mPresenter.next();
                break;
        }
    }

    @Override
    public void setActionBar() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer_home);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    /* private void setupVideoViewPager() {
        String[] titles = getResources().getStringArray(R.array.video_tab);
        VideoTabAdapter adapter =
                new VideoTabAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }*/

    private void setupTextViewPager() {
        mTabLayout.removeAllTabs();
        if (mPlayService != null && mPlayService.isPlaying()) {
            flPlayBar.setVisibility(View.VISIBLE);
        } else {
            flPlayBar.setVisibility(View.GONE);
        }
        String[] titles = getResources().getStringArray(R.array.text_tab);
        // ArticleTabAdapter adapter =
        mAdapter = new ArticleTabAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        new SetAdapterTask().execute();
        /*mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);*/
    }

    private void setupPictureViewPager() {
        if (mPlayService != null && mPlayService.isPlaying()) {
            flPlayBar.setVisibility(View.VISIBLE);
        } else {
            flPlayBar.setVisibility(View.GONE);
        }
        mTabLayout.removeAllTabs();
        String[] titles = getResources().getStringArray(R.array.Picture_tab);
        mAdapter =
                new PictureTabAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        new SetAdapterTask().execute();
      /*  mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);*/
    }

    private void setupMusicViewPager() {
        flPlayBar.setVisibility(View.VISIBLE);
        mTabLayout.removeAllTabs();
        String[] titles = getResources().getStringArray(R.array.Music_tab);
       /* MusicTabAdapter adapter =
                new MusicTabAdapter(getSupportFragmentManager(), Arrays.asList(titles));*/
        mAdapter =
                new MusicTabAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        new SetAdapterTask().execute();
       /* mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);*/
    }

    private FragmentStatePagerAdapter mAdapter;

    private class SetAdapterTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (mAdapter != null) {
                mViewPager.setAdapter(mAdapter);
                mTabLayout.setupWithViewPager(mViewPager);
                mTabLayout.setTabsFromPagerAdapter(mAdapter);
            }
        }
    }

    public PlayService getPlayService() {
        return mPlayService;
    }

    public void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mPlayServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            {
                mPlayService = ((PlayService.PlayBinder) service).getService();
                mPlayService.setOnPlayEventListener(MainActivity.this);
                mPlayService.updateMusicList();
                mPresenter.setPlayService(mPlayService);
                mPresenter.setAudioManger();
                onChange(mPlayService.getPlayingMusic());
                parseIntent(getIntent());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void parseIntent(Intent intent) {
        boolean s = intent.getBooleanExtra(Extras.FROM_NOTIFICATION, false);
        if (intent.hasExtra(Extras.FROM_NOTIFICATION)) {
            showPlayingFragment();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        parseIntent(intent);
        super.onNewIntent(intent);
    }

    private void showPlayingFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mPlayFragment == null) {
            mPlayFragment = new MusicFragment();
            ft.replace(android.R.id.content, mPlayFragment);
        } else {
            ft.show(mPlayFragment);
        }
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = true;
    }

    @Override
    public void onPublish(int progress) {
        pbPlayBar.setProgress(progress);
        if (mPlayFragment != null && mPlayFragment.isResume()) {
            mPlayFragment.onPublish(progress);
        }
    }

    @Override
    public void onChange(Music music) {
        onPlay(music);
        if (mPlayFragment != null && mPlayFragment.isResume()) {
            mPlayFragment.onChange(music);
        }
    }

    @Override
    public void onPlayerPause() {
        ivPlayBarPlay.setSelected(false);
        if (mPlayFragment != null && mPlayFragment.isResume()) {
            mPlayFragment.onPlayerPause();
        }
    }

    @Override
    public void onPlayerResume() {
        ivPlayBarPlay.setSelected(true);
        if (mPlayFragment != null && mPlayFragment.isResume()) {
            mPlayFragment.onPlayerResume();
        }

    }

    @Override
    public void onTimer(long remain) {

    }

    @Override
    public void next(PlayService service) {

    }

    @Override
    public void pause(PlayService service) {

    }

    @Override
    public void start(PlayService service) {

    }

    @Override
    public void change(PlayService service) {

    }


    public void onPlay(Music music) {
        if (music == null) {
            return;
        }
        Bitmap cover;
        if (music.getCover() == null) {
            cover = CoverLoader.getInstance().loadThumbnail(music.getCoverUri());
        } else {
            cover = music.getCover();
        }
        ivPlayBarCover.setImageBitmap(cover);
        tvPlayBarTitle.setText(music.getTitle());
        tvPlayBarArtist.setText(music.getArtist());
        if (getPlayService().isPlaying()) {
            ivPlayBarPlay.setSelected(true);
        } else {
            ivPlayBarPlay.setSelected(false);
        }
        pbPlayBar.setMax((int) music.getDuration());
        pbPlayBar.setProgress(0);


    }

    @Override
    public void onBackPressed() {
        if (mPlayFragment != null && isPlayFragmentShow) {
            hidePlayingFragment();
            return;
        }
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            moveTaskToBack(false);
        }
    }

    /**
     * 隐藏音乐播放界面
     */
    public void hidePlayingFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
        ft.hide(mPlayFragment);
        ft.commit();
        isPlayFragmentShow = false;
    }


}
