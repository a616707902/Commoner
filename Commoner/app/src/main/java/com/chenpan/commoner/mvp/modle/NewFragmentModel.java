package com.chenpan.commoner.mvp.modle;

import android.content.Context;

import com.chenpan.commoner.bean.NewsSummary;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public interface NewFragmentModel {
    void parserNews(Context context,String url,String tag, MyCallback<List<NewsSummary>> mCallback);


}
