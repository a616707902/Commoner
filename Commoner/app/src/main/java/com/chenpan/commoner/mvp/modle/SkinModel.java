package com.chenpan.commoner.mvp.modle;

/**
 * Created by Administrator on 2016/7/28.
 */
public interface SkinModel {
    void setRedSkin(String fileString, CallbackSkin callback);

    void setBlackSkin(String fileString, CallbackSkin callback);

    void setGreenSkin(String fileString, CallbackSkin callback);

    void setBlueSkin(CallbackSkin callback);


    interface CallbackSkin {
        public void onSccuss();

        public void onFaild();

        public void noSkinRes();
    }

}
