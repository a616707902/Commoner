package com.chenpan.commoner.mvp.modle.imp;

import com.android.volley.VolleyError;
import com.chenpan.commoner.bean.JOnlineMusicList;
import com.chenpan.commoner.mvp.modle.MyCallback;
import com.chenpan.commoner.mvp.modle.OnlineMusicModel;
import com.chenpan.commoner.network.VolleyInterface;
import com.chenpan.commoner.network.VolleyRequest;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/12.
 */
public class IOnlineMusicModel implements OnlineMusicModel {

    Gson mGson;

    @Override
    public void parserMusic(String url, String tag, Map<String, String> param, final MyCallback<JOnlineMusicList> mCallback) {
        VolleyRequest.RequestPostString(url, tag, param, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                mGson = new Gson();
                try {
                    JOnlineMusicList jOnlineMusicList = mGson.fromJson(result, JOnlineMusicList.class);
                    mCallback.onSccuss(jOnlineMusicList);

                } catch (Exception e) {
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
