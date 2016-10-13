package com.chenpan.commoner.mvp.modle;

import android.content.Context;

import com.chenpan.commoner.bean.NewsDetail;

/**
 * 作者：chenpan
 * 时间：2016/10/12 16:35
 * 邮箱：616707902@qq.com
 * 描述：
 */

public interface NewsDetailModel {
    void  loadNewsDetail(Context context,String url,String tag,MyCallback<NewsDetail> callback);
}
