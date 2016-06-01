package com.chenpan.commoner.mvp.presenter;

import android.content.Context;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.User;
import com.chenpan.commoner.mvp.modle.MainMode;
import com.chenpan.commoner.mvp.modle.imp.IMainMode;
import com.chenpan.commoner.mvp.view.MainView;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/1.
 */
public class MainPresenter extends BasePresenter<MainView> {
    MainMode mainMode = new IMainMode();
    User user = null;

    public void login(Context context, Map<String, String> map) {
        getWeakView().showLogin();
        mainMode.login(context, map, new MainMode.LoginLisener() {
            @Override
            public void saveUser(User result) {
                getWeakView().setUserInfo(result);
            }
        });

    }
}
