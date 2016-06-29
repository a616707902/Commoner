package com.chenpan.commoner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chenpan.commoner.fragment.ArticleFragment;
import com.chenpan.commoner.fragment.PictureFragmentFormBaidu;
import com.chenpan.commoner.fragment.PictureFragmentFromOther;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public class PictureTabAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles;

    public PictureTabAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position > 4) {
            PictureFragmentFromOther fragment = new PictureFragmentFromOther();
            fragment.setUrl(mTitles.get(position).split("@chenpan@")[1]);
            return fragment;
        } else {
            PictureFragmentFormBaidu fragment = new PictureFragmentFormBaidu();
            fragment.setType(mTitles.get(position).split("@chenpan@")[1]);
            return fragment;
  }

    }

    @Override
    public int getCount() {
        return mTitles.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).split("@chenpan@")[0];
    }
}
