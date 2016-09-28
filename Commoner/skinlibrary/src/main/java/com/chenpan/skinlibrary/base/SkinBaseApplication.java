package com.chenpan.skinlibrary.base;

import android.app.Application;

import com.chenpan.skinlibrary.load.SkinManager;


/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:10:54
 */
public class SkinBaseApplication extends Application {

    public void onCreate() {
        super.onCreate();

        initSkinLoader();
    }

    /**
     * Must call init first
     */
    private void initSkinLoader() {
        SkinManager.getInstance().init(this);
        SkinManager.getInstance().load();
    }
}