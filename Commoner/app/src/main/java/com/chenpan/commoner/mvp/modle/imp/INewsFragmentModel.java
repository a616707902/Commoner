package com.chenpan.commoner.mvp.modle.imp;

import android.content.Context;

import com.android.volley.VolleyError;
import com.chenpan.commoner.bean.NewsSummary;
import com.chenpan.commoner.mvp.modle.MyCallback;
import com.chenpan.commoner.mvp.modle.NewFragmentModel;
import com.chenpan.commoner.network.VolleyInterface;
import com.chenpan.commoner.network.VolleyRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class INewsFragmentModel implements NewFragmentModel{
  //  String url="http://v.juhe.cn/toutiao/index";
//http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    @Override
    public void parserNews(Context context,String url, final String tag, final MyCallback<List<NewsSummary>> mCallback) {
        VolleyRequest.RequestGetString(url, tag, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                Gson gson = new Gson();
                JSONObject jsonObject=null;
                try {
                     jsonObject=new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(jsonObject!=null){
                    String newslist=null;
                    try {
                        newslist=jsonObject.getString(tag);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    List<NewsSummary> newsSummaries=gson.fromJson(newslist, new TypeToken<List<NewsSummary>>() {
                }.getType());

                if (newsSummaries!=null){
                    mCallback.onSccuss(newsSummaries);
                }else{
                    mCallback.onFaild();
                }}else{
                    mCallback.onFaild();
                }
            }

            @Override
            public void onMyError(VolleyError result) {
                mCallback.onFaild();
            }
        });
    }
}