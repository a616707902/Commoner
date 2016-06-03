package com.chenpan.commoner.mvp.modle;

import android.content.Context;
import android.view.animation.Animation;

import com.chenpan.commoner.bean.User;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/2.
 */
public interface LoginModel {

    public  void login(Context context,String tag,Map<String ,String> map,LoginLisener loginLisener);
    int getBackgroundImageResID();
    Animation getBackgroundImageAnimation(Context context);
    interface LoginLisener{

        /**
         * 加载数据的监听
         * @param result
         */
        void saveUser(User result);
    }
}
