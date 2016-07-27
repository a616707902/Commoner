package com.chenpan.commoner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chenpan.commoner.fragment.MusicLocalFragment;
import com.chenpan.commoner.fragment.MusicOnlineFragment;
import com.chenpan.commoner.fragment.PictureFragmentFormBaidu;
import com.chenpan.commoner.fragment.PictureFragmentFromOther;

import java.util.List;

public class MusicTabAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles;

    public MusicTabAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position > 0) {//本地音乐
            MusicOnlineFragment fragment = new MusicOnlineFragment();
          //  fragment.setUrl(mTitles.get(position).split("@chenpan@")[1]);
            return fragment;
        } else {//在线音乐
            MusicLocalFragment fragment = new MusicLocalFragment();

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
