package com.chenpan.commoner.mvp.modle;

/**
 * Created by Administrator on 2016/7/28.
 */
public interface SkinModel {
    void setRedSkin(String fileString, Callback callback);

    void setBlackSkin(String fileString, Callback callback);

    void setGreenSkin(String fileString, Callback callback);

    void setBlueSkin(Callback callback);


    interface Callback {
        public void onSccuss();

        public void onFaild();

        public void noSkinRes();
    }

}
