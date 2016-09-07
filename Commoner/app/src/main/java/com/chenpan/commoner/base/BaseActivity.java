package com.chenpan.commoner.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import com.chenpan.commoner.R;
import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.utils.ContextUtils;
import com.example.chenpan.library.skinmanager.entity.DynamicAttr;
import com.example.chenpan.library.skinmanager.listener.IDynamicNewView;
import com.example.chenpan.library.skinmanager.listener.ISkinUpdate;
import com.example.chenpan.library.skinmanager.loader.SkinInflaterFactory;
import com.example.chenpan.library.skinmanager.loader.SkinManager;
import com.example.chenpan.library.skinmanager.loader.StatusBarBackground;
import com.example.chenpan.library.skinmanager.loader.SystemBarTintManager;

import java.util.List;

import butterknife.ButterKnife;

/**
 * V,T未指定类型，这里我们让子类去定义
 * Created by Administrator on 2016/5/24.
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity implements IBase,ISkinUpdate, IDynamicNewView {
    public T mPresenter;
    /**
     * 主线程
     */
    private long mUIThreadId;
    /**
     * 导航栏
     */
    private Toolbar mToolbar;
    /**
     * 管理通知栏的对象
     */
    private SystemBarTintManager tintManager;

    private View mRootView;
    /**
     * Whether response to skin changing after create
     */
    private boolean isResponseOnSkinChanging = true;

    private SkinInflaterFactory mSkinInflaterFactory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mSkinInflaterFactory = new SkinInflaterFactory();
        getLayoutInflater().setFactory(mSkinInflaterFactory);
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
        SkinManager.getInstance().detach(this);
        mSkinInflaterFactory.clean();
        super.onDestroy();
    }


    /**
     * 设置通知栏颜色
     */
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isSetStatusBar()) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.toolbar_color);
        }
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
        SkinManager.getInstance().attach(this);
    }

    /**
     * dynamic add a skin view
     *
     * @param view
     * @param attrName
     * @param attrValueResId
     */
    protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId){
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs){
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    final protected void enableResponseOnSkinChanging(boolean enable){
        isResponseOnSkinChanging = enable;
    }

    @Override
    public void onThemeUpdate() {
        if(!isResponseOnSkinChanging){
            return;
        }
        mSkinInflaterFactory.applySkin();
        changeStatusColor();
    }
    public void changeStatusColor() {
        //如果当前的Android系统版本大于4.4则更改状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.i("SkinBaseActivity", "changeStatus");
            int color = SkinManager.getInstance().getColorPrimaryDark();
            StatusBarBackground statusBarBackground = new StatusBarBackground(
                    this, color);
            if (color != -1)
                statusBarBackground.setStatusBarbackColor();
        }
    }
    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }
}
