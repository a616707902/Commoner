package com.chenpan.commoner.mvp.modle;

import android.content.Context;
import android.content.Intent;

import com.chenpan.commoner.bean.User;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/1.
 */
public interface MainMode {
public  void login(Context context,Map<String ,String> map,LoginLisener loginLisener);
    interface LoginLisener{

        /**
         * 加载数据的监听
         * @param result
         */
        void saveUser(User result);
    }

}
