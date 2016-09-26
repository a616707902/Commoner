package com.chenpan.commoner.mvp.presenter;


import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.Constants;
import com.chenpan.commoner.bean.JOnlineMusicList;
import com.chenpan.commoner.mvp.modle.OnlineMusicModel;
import com.chenpan.commoner.mvp.modle.imp.IOnlineMusicModel;
import com.chenpan.commoner.mvp.view.OnlineMusicView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/12.
 */
public class OnlineMusicPresenter extends BasePresenter<OnlineMusicView> {


    OnlineMusicModel onlineMusicModel = new IOnlineMusicModel();

    public void onLoad(String url, final int offset, String type) {

        if (!getWeakView().checkNet()) {
            getWeakView().showNoNet();
            return;
        }
        Map<String, String> param = new HashMap<>();
        param.put(Constants.PARAM_METHOD, Constants.METHOD_GET_MUSIC_LIST);
        param.put(Constants.PARAM_TYPE, type);
        param.put(Constants.PARAM_SIZE, String.valueOf(Constants.MUSIC_LIST_SIZE));
        param.put(Constants.PARAM_OFFSET, String.valueOf(offset));


        onlineMusicModel.parserMusic(url, url, param, new OnlineMusicModel.Callback<JOnlineMusicList>() {
            @Override
            public void onSccuss(JOnlineMusicList data) {
                if (data == null || data.getSong_list() == null || data.getSong_list().size() == 0) {
                   getWeakView().showFaild();
                    return;
                }
                if (offset == 0 && data == null) {
                    getWeakView().showFaild();
                } else

                {
                    getWeakView().showSuccess();
                    getWeakView().setAdapter(data.getSong_list());
                    if (offset == 0)
                    getWeakView().initHeader(data);

                }


            }

            @Override
            public void onFaild() {

                if (offset==0)
                getWeakView().showFaild();
            }
        });


    }
}
