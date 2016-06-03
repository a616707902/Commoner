package com.chenpan.commoner.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenpan.commoner.base.pbase.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/2.
 */
public abstract class BaseFragment<V,T extends BasePresenter<V>> extends Fragment implements IBase {
    protected T mPresenter;
    protected Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V)this);
        }
        super.onCreate(savedInstanceState);
    }
    protected View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = createView(inflater, container, savedInstanceState);
        }
        mContext = mRootView.getContext();
        return mRootView;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    /**
     * 在父类里面创建，在子类里面具体实现
     *
     * @return
     */
    public abstract T createPresenter();


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindViewAndAction(savedInstanceState);
    }
    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView((V)this);
            mPresenter = null;
        }
        mContext = null;
        super.onDestroy();
    }
}
