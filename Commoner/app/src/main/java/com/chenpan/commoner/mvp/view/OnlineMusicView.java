package com.chenpan.commoner.mvp.view;

import com.chenpan.commoner.bean.JOnlineMusic;
import com.chenpan.commoner.bean.JOnlineMusicList;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface OnlineMusicView {
    void showSuccess();

    void showEmpty();

    boolean checkNet();

    void showFaild();

    void showNoNet();
    void initHeader(JOnlineMusicList jOnlineMusicList);
    void setAdapter(List<JOnlineMusic> musicList);
}
