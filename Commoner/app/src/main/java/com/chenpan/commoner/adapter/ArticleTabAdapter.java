package com.chenpan.commoner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chenpan.commoner.fragment.ArticleFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public class ArticleTabAdapter extends FragmentPagerAdapter {
    private List<String> mTitles;

    public ArticleTabAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        ArticleFragment fragment = new ArticleFragment();
        fragment.setUrl(mTitles.get(position).split("@panjichang@")[1]);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).split("@panjichang@")[0];
    }
}
