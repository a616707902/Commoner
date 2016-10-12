package com.chenpan.commoner;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenpan.commoner.adapter.PhotoPagerAdapter;
import com.chenpan.commoner.base.BaseActivity;
import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.NewsPhotoDetail;
import com.chenpan.commoner.common.Constants;
import com.chenpan.commoner.fragment.PhotoDetailFragment;
import com.chenpan.commoner.mvp.presenter.NewsFragmentPresenter;
import com.chenpan.commoner.widget.PhotoViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class NewsPhotoDetailActivity extends BaseActivity {

    @Bind(R.id.album_back)
    ImageView mAlbumBack;
    @Bind(R.id.album_title)
    TextView mAlbumTitle;
    @Bind(R.id.album_title_bar)
    RelativeLayout mAlbumTitleBar;
    @Bind(R.id.viewpager)
    PhotoViewPager mViewpager;
    @Bind(R.id.photo_detail_title_tv)
    TextView mPhotoDetailTitleTv;
    private List<PhotoDetailFragment> mPhotoDetailFragmentList = new ArrayList<>();
    private NewsPhotoDetail mNewsPhotoDetail;

    @Override
    public BasePresenter createPresenter() {
        return new NewsFragmentPresenter();
    }

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public boolean isSetStatusBar() {
        return false;
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        createFragment(mNewsPhotoDetail);
        initViewPager();
        setPhotoDetailTitle(0);
    }
    @Override
    public void setActionBar() {
        getToolbar().setVisibility(View.GONE);
    }
    @Override
    public int getContentLayout() {
        return R.layout.activity_news_photo_detail;
    }

    @Override
    public void getIntentValue() {
        mNewsPhotoDetail = getIntent().getParcelableExtra(Constants.PHOTO_DETAIL);
    }

    private void createFragment(NewsPhotoDetail newsPhotoDetail) {
        mPhotoDetailFragmentList.clear();
        for (NewsPhotoDetail.Picture picture : newsPhotoDetail.getPictures()) {
            PhotoDetailFragment fragment = new PhotoDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.PHOTO_DETAIL_IMGSRC, picture.getImgSrc());
            fragment.setArguments(bundle);
            mPhotoDetailFragmentList.add(fragment);
        }
    }

    private void initViewPager() {
        PhotoPagerAdapter photoPagerAdapter = new PhotoPagerAdapter(getSupportFragmentManager(), mPhotoDetailFragmentList);
        mViewpager.setAdapter(photoPagerAdapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPhotoDetailTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setPhotoDetailTitle(int position) {
        String title = getTitle(position);
        mPhotoDetailTitleTv.setText(getString(R.string.photo_detail_title, position + 1,
                mPhotoDetailFragmentList.size(), title));
    }

    private String getTitle(int position) {
        String title = mNewsPhotoDetail.getPictures().get(position).getTitle();
        if (title == null) {
            title = mNewsPhotoDetail.getTitle();
        }
        return title;
    }



    @OnClick(R.id.album_back)
    public void onClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }
}
