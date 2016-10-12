package com.chenpan.commoner.mvp.modle;


import com.chenpan.commoner.bean.JOnlineMusicList;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface OnlineMusicModel {
    void parserMusic(String url,String tag,Map<String,String>  param, MyCallback<JOnlineMusicList> mCallback);


}
