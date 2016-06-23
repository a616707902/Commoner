package com.chenpan.commoner.mvp.modle.imp;

import android.content.Context;

import com.chenpan.commoner.bean.PictureBean;
import com.chenpan.commoner.bean.PictureBeanBaiDu;
import com.chenpan.commoner.bean.PictureBeanOther;
import com.chenpan.commoner.mvp.modle.PictureFramentModel;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class IPictureFragmentOtherModel implements PictureFramentModel {


    @Override
    public void parserPictureBaidu(Context context, String url, String tag, Callback<List<PictureBeanBaiDu>> mCallback) {

    }

    @Override
    public void parserPictureOther(Context context, String url, String tag, Callback<List<PictureBeanOther>> mCallback) {

    }
}
