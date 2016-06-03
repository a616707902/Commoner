package com.chenpan.commoner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chenpan.commoner.fragment.VideoFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public class VideoTabAdapter extends FragmentPagerAdapter {
    private List<String> mTitle=null;
    public VideoTabAdapter(FragmentManager fm,List<String> mTitle) {
        super(fm);
        this.mTitle=mTitle;
    }

    @Override
    public Fragment getItem(int position) {
        VideoFragment fragment = new VideoFragment();
        fragment.setType(Integer.parseInt(mTitle.get(position).split("@panjichang@")[1]));
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
