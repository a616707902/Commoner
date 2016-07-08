package com.chenpan.commoner.mvp.view;

import com.chenpan.commoner.bean.User;
import com.chenpan.commoner.service.PlayService;

/**
 * Created by Administrator on 2016/6/1.
 */
public interface  MainView {

    void showLogin();
    public void setUserInfo(User user);

    /**
     * 下一曲
     * @param service
     */
     void next(PlayService service);

    /**
     * 暂停
     * @param service
     */
    void  pause(PlayService service);

    /**
     * 开始
     * @param service
     */
    void  start(PlayService service);

    /**
     * 改变
     * @param service
     */
      void change(PlayService service);
}
