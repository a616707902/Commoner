package com.chenpan.commoner.base.pbase;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/5/24.
 */
public abstract class BasePresenter<T> {
    /**
     * 使用弱引用，当内存不足时，垃圾回收机制回收对象
     */
    //public WeakReference<T> mWeakView;
    public SoftReference<T> mWeakView;
    /**
     * 绑定View
     *
     * @param view
     */
    public void attachView(T view) {
        mWeakView = new SoftReference<T>(view);
    }

    /**
     * 解绑view，当activity销毁时调用
     *
     * @param view
     */
    public void detachView(T view) {
        if (mWeakView != null) {
            mWeakView.clear();
            mWeakView = null;
        }
    }

    public T getWeakView() {
        return mWeakView.get();
    }

    public void  init(){};

}
