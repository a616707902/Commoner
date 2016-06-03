package com.chenpan.commoner.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenpan.commoner.base.pbase.BasePresenter;


public interface IBase {


    View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    /**
     * 绑定视图监听
     */
    void bindViewAndAction(Bundle savedInstanceState);

    /**
     * 得到当前的xml布局
     *
     * @return
     */
    int getContentLayout();
}
