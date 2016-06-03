package com.chenpan.commoner.mvp.view;

import android.view.animation.Animation;

/**
 * Created by Administrator on 2016/6/2.
 */
public interface LoginView {
    /**
     * 正在登陆
     */
   void isLogining();
    /**登陆成功*/
    void loginFinshed();

    /**
     * 设置背景动画
     * @param animation
     */
    void animateBackgroundImage(Animation animation);
    void setImageSource(int res);

}
