package com.chenpan.commoner.mvp.modle;

import android.content.Context;

import com.chenpan.commoner.bean.PictureBeanBaiDu;
import com.chenpan.commoner.bean.PictureBeanOther;

import java.util.List;


/**
 * Created by Administrator on 2016/6/22.
 */
public interface PictureFramentModel {

    void parserPictureBaidu(Context context,String url,String tag, MyCallback<List<PictureBeanBaiDu>> mCallback);
    void parserPictureOther(Context context,String url,String tag, MyCallback<List<PictureBeanOther>> mCallback);

}
