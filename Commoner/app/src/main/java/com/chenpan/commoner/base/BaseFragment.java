package com.chenpan.commoner.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenpan.commoner.MainActivity;
import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.service.PlayService;
import com.chenpan.skinlibrary.base.SkinBaseFragment;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/6/2.
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends SkinBaseFragment implements IBase {
    protected T mPresenter;
    protected Context mContext;

    private PlayService mPlayService;
    protected Handler mHandler;
    private boolean mResumed;
    /**
     * 获取每个界面的名字
     */
    protected static String TAG_LOG = null;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mPlayService = ((MainActivity) activity).getPlayService();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TAG_LOG = this.getClass().getSimpleName();
        mPresenter = createPresenter();
        mHandler = new Handler();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
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
        mResumed = true;
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
     //   ButterKnife.unbind(this);
        if (mPresenter != null) {
            mPresenter.detachView((V) this);
            mPresenter = null;
        }
        mContext = null;
        super.onDestroy();
    }

    /**
     * is bind eventBus
     *
     * @return
     */
    protected boolean isBindEventBusHere(

    ) {
        return false;
    }

    public PlayService getPlayService() {
        return mPlayService;
    }
    public boolean isResume() {
        return mResumed;
    }
}
