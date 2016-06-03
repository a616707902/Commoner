package com.chenpan.commoner.mvp.modle.imp;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.VolleyError;
import com.chenpan.commoner.R;
import com.chenpan.commoner.bean.User;
import com.chenpan.commoner.mvp.modle.LoginModel;
import com.chenpan.commoner.network.VolleyInterface;
import com.chenpan.commoner.network.VolleyRequest;
import com.chenpan.commoner.network.volleyOK.VolleyHelper;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/2.
 */
public class ILoginModel implements LoginModel {
    String url="";
    String resultFormWork="";


    @Override
    public void login(Context context, String tag, Map<String, String> map, final LoginLisener loginLisener) {
        VolleyRequest.RequestPostString(url,tag, map, new VolleyInterface() {
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

    @Override
    public int getBackgroundImageResID() {
        int resId;
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour <= 12) {
            resId = R.drawable.morning;
        } else if (hour > 12 && hour <= 18) {
            resId = R.drawable.afternoon;
        } else {
            resId = R.drawable.night;
        }
        return resId;
    }

    @Override
    public Animation getBackgroundImageAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.splash);
    }
}
