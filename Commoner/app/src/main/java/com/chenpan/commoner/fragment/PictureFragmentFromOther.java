package com.chenpan.commoner.fragment;

import android.os.Bundle;

import com.chenpan.commoner.base.BaseFragment;
import com.chenpan.commoner.base.pbase.BasePresenter;

/**
 * Created by Administrator on 2016/6/22.
 */
public class PictureFragmentFromOther extends BaseFragment {
    private  String  url;

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

    public void setUrl(String url) {
        this.url = url;
    }
}
