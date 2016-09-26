package com.chenpan.commoner.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.utils.ContextUtils;
import com.chenpan.skinlibrary.base.SkinBaseActivity;

import butterknife.ButterKnife;

/**
 * V,T未指定类型，这里我们让子类去定义
 * Created by Administrator on 2016/5/24.
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends SkinBaseActivity implements IBase {
    public T mPresenter;
    /**
     * 主线程
     */
    private long mUIThreadId;
    /**
     * 导航栏
     */
    private Toolbar mToolbar;

    private View mRootView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getAppManager().addActivity(this);

        changeStatusColor();
     //  initWindow();
        getIntentValue();
        mRootView = createView(null, null, savedInstanceState);
        setContentView(mRootView);
        //创建presenter
        mPresenter = createPresenter();
        //内存泄漏,当Activity销毁，P，M都还在运行 ，就出现内存泄漏
        //关联View
        mPresenter.attachView((V) this);
        mToolbar = (Toolbar) findViewById(getToolBarId());
        setSupportActionBar(mToolbar);//这里要用到主题必须是隐藏了action的
        setActionBar();

        bindViewAndAction(savedInstanceState);
    }

    public void setActionBar() {
    }

    public void getIntentValue() {
        
    }

    /**
     * 在父类里面创建，在子类里面具体实现
     *
     * @return
     */
   public abstract T createPresenter();


    /**
     * 绑定视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = ContextUtils.inflate(this, getContentLayout());
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 获取UI线程ID
     *
     * @return UI线程ID
     */
    public long getUIThreadId() {
        return mUIThreadId;
    }

    /**
     * 得到各个界面的tools
     *
     * @return
     */
    public abstract int getToolBarId();

    @Override
    protected void onDestroy() {
        //解除关联
        mPresenter.detachView((V) this);
        AppManager.getAppManager().finishActivity(this);//弹出栈
        super.onDestroy();
    }



    /**
     * 是否设置沉浸式
     *
     * @return
     */
    protected boolean isSetStatusBar() {
        return true;
    }

    /**
     * 将模式设为singletask，跳转时系统调用
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        mUIThreadId = android.os.Process.myTid();
        super.onNewIntent(intent);
    }
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }




}
