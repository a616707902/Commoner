package com.chenpan.commoner.mvp.presenter;

import android.os.Environment;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.mvp.modle.SkinModel;
import com.chenpan.commoner.mvp.modle.imp.ISkinModel;
import com.chenpan.commoner.mvp.view.SkinView;

import java.io.File;

/**
 * Created by Administrator on 2016/7/28.
 */
public class SkinPresenter extends BasePresenter<SkinView> {
    SkinModel SkinModel = new ISkinModel();
    private String SKIN_DIR = Environment
            .getExternalStorageDirectory() + File.separator;

    public void setRedSkin(String filename) {
        SkinModel.setRedSkin(SKIN_DIR + filename, new SkinModel.CallbackSkin() {
            @Override
            public void onSccuss() {
                getWeakView().showSucceed();
            }

            @Override
            public void onFaild() {
                getWeakView().showFild();
            }

            @Override
            public void noSkinRes() {
                getWeakView().showNOres();
            }
        });

    }

    public void setBlueSkin() {
        SkinModel.setBlueSkin(new SkinModel.CallbackSkin() {
            @Override
            public void onSccuss() {
                getWeakView().showSucceed();

            }

            @Override
            public void onFaild() {
                getWeakView().showFild();
            }

            @Override
            public void noSkinRes() {
                getWeakView().showNOres();
            }
        });

    }
}
