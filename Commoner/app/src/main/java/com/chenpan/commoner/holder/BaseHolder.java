package com.chenpan.commoner.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/3.
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {
    protected View mView;
    protected T mData;
    protected Context mContext;

    public View getView() {
        return mView;
    }

    public BaseHolder(View view) {
        super(view);
        this.mView = view;
        mContext = this.mView.getContext();
        ButterKnife.bind(this, mView);
        init();
    }
    public void init() {

    }

    public void setData(T mData,int position) {
        this.mData = mData;
    }
}
