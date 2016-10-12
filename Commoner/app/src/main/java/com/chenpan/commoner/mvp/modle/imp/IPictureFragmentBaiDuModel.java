package com.chenpan.commoner.mvp.modle.imp;

import android.content.Context;

import com.android.volley.VolleyError;
import com.chenpan.commoner.bean.PictureBeanBaiDu;
import com.chenpan.commoner.bean.PictureBeanOther;
import com.chenpan.commoner.bean.ResponseImagesListEntity;
import com.chenpan.commoner.mvp.modle.MyCallback;
import com.chenpan.commoner.mvp.modle.PictureFramentModel;
import com.chenpan.commoner.network.VolleyInterface;
import com.chenpan.commoner.network.VolleyRequest;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class IPictureFragmentBaiDuModel implements PictureFramentModel {

    @Override
    public void parserPictureBaidu(Context context, String url, String tag, final MyCallback<List<PictureBeanBaiDu>> mCallback) {
        VolleyRequest.RequestGetStringS(url, tag, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                Gson gson = new Gson();
                ResponseImagesListEntity responseImagesListEntity=   gson.fromJson(result, ResponseImagesListEntity.class);
                if (responseImagesListEntity!=null){
                    mCallback.onSccuss(responseImagesListEntity.getImgs());
                }

            }

            @Override
            public void onMyError(VolleyError result) {
                mCallback.onFaild();
            }
        });

    }

    @Override
    public void parserPictureOther(Context context, String url, String tag, MyCallback<List<PictureBeanOther>> mCallback) {

    }


}
