package com.chenpan.commoner;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chenpan.commoner.base.BaseActivity;
import com.chenpan.commoner.bean.User;
import com.chenpan.commoner.bean.UserManager;
import com.chenpan.commoner.mvp.presenter.MainPresenter;
import com.chenpan.commoner.mvp.view.MainView;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
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

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
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
                            Intent intent = new Intent();

                            return true;
                        }

                    }
                }).withSavedInstance(savedInstanceState)
                .build();
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withHasStableIds(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("视频").withIcon(R.mipmap.ic_launcher).withIdentifier(1).withSelectable(true),
                        new PrimaryDrawerItem().withName("图片").withIcon(R.mipmap.ic_launcher).withIdentifier(2).withSelectable(true).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        new PrimaryDrawerItem().withName("文章").withIcon(R.mipmap.ic_launcher).withIdentifier(3).withSelectable(true),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("设置").withIcon(R.mipmap.ic_launcher).withIdentifier(5).withSelectable(false)

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
                                // setupVideoViewPager();
                                //               intent = new Intent(MainActivity.this, TopicActivity.class);
                            } else if (drawerItem.getIdentifier() == 2 && dId != 2) {
                                dId = 2;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                // setupPictureViewPager();
                            } else if (drawerItem.getIdentifier() == 3 && dId != 3) {
                                dId = 3;
                                mTabLayout.removeAllTabs();
                                mViewPager.removeAllViews();
                                //  setupTextViewPager();
                            } else if (drawerItem.getIdentifier() == 4) {
                                //                intent = new Intent(MainActivity.this, SmallGameActivity.class);
                            } else if (drawerItem.getIdentifier() == 5) {
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
        RecyclerViewCacheUtil.getInstance().withCacheSize(2).init(result);
        if (savedInstanceState == null) {
            // result.setSelection(1, false);

            //set the active profile
            headerResult.setActiveProfile(profile);
        }
        result.updateBadge(4, new StringHolder(10 + ""));
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
        return new MainPresenter();
    }

    @Override
    protected boolean isSetStatusBar() {
        return true;
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        }
    }



}
