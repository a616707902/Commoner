package com.chenpan.commoner.fragment;

import android.os.Bundle;

import com.chenpan.commoner.base.BaseFragment;
import com.chenpan.commoner.base.pbase.BasePresenter;

/**
 * Created by Administrator on 2016/6/2.
 */
public class VideoFragment extends BaseFragment{
    private int type=0;


    public void setType(int type) {
        this.type = type;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {

    }

    @Override
    public int getContentLayout() {
        return 0;
    }
}
