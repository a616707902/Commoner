package com.chenpan.commoner.mvp.modle.imp;


import com.chenpan.commoner.mvp.modle.SkinModel;
import com.chenpan.skinlibrary.listener.ILoaderListener;
import com.chenpan.skinlibrary.load.SkinManager;

import java.io.File;

/**
 * Created by Administrator on 2016/7/28.
 */
public class ISkinModel  implements SkinModel{
    @Override
    public void setRedSkin(String fileString, final Callback callback) {
        File skin = new File(fileString);

        if(skin == null || !skin.exists()){
            callback.noSkinRes();
            return;
        }
        SkinManager.getInstance().load(skin.getAbsolutePath(),
                new ILoaderListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess() {
                      callback.onSccuss();
                    }

                    @Override
                    public void onFailed() {
                       callback.onFaild();
                    }
                });
    }

    @Override
    public void setBlackSkin(String fileString, Callback callback) {
        File skin = new File(fileString);

        if(skin == null || !skin.exists()){
            callback.noSkinRes();
            return;
        }
    }

    @Override
    public void setGreenSkin(String fileString, Callback callback) {
        File skin = new File(fileString);

        if(skin == null || !skin.exists()){
            callback.noSkinRes();
            return;
        }
    }

    @Override
    public void setBlueSkin(Callback callback) {
        SkinManager.getInstance().restoreDefaultTheme();
        callback.onSccuss();
    }
}
