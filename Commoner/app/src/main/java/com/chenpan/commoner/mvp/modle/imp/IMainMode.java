package com.chenpan.commoner.mvp.modle.imp;

import android.content.Context;

import com.android.volley.VolleyError;
import com.chenpan.commoner.bean.User;
import com.chenpan.commoner.mvp.modle.MainMode;
import com.chenpan.commoner.network.VolleyInterface;
import com.chenpan.commoner.network.VolleyRequest;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/1.
 */
public class IMainMode implements MainMode {
    String url="";
    String resultFormWork="";


    @Override
    public void login(Context context, Map<String, String> map, final LoginLisener loginLisener) {
        VolleyRequest.RequestPostString(url, "postTel", map, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                resultFormWork = result;
                if (loginLisener != null) {
                    loginLisener.saveUser(new User());
                }
            }

            @Override
            public void onMyError(VolleyError result) {
                resultFormWork = "数据加载出错";
                if (loginLisener != null) {
                    loginLisener.saveUser(null);
                }
            }
        });
    }
}
