package com.chenpan.commoner.mvp.modle.imp;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.chenpan.commoner.bean.NewsDetail;
import com.chenpan.commoner.mvp.modle.MyCallback;
import com.chenpan.commoner.mvp.modle.NewsDetailModel;
import com.chenpan.commoner.network.VolleyInterface;
import com.chenpan.commoner.network.VolleyRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：chenpan
 * 时间：2016/10/12 16:35
 * 邮箱：616707902@qq.com
 * 描述：
 */

public class INewsDetailModel implements NewsDetailModel {
    //http://c.m.163.com/nc/article/BG6CGA9M00264N2N/full.html
    @Override
    public void loadNewsDetail(Context context, String url, final String tag, final MyCallback<NewsDetail> callback) {

        VolleyRequest.RequestGetString(url, url, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                Gson gson = new Gson();
                JSONObject jsonObject = null;
                String detail = null;
                try {
                    jsonObject = new JSONObject(result);
                    if (jsonObject != null) {
                        detail = jsonObject.getString(tag);
                    } else {
                        callback.onFaild();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(detail)) {
                    NewsDetail newsDetail = gson.fromJson(detail, NewsDetail.class);
                    callback.onSccuss(newsDetail);
                } else {
                    callback.onFaild();
                }

            }

            @Override
            public void onMyError(VolleyError result) {
                callback.onFaild();
            }
        });
    }
}