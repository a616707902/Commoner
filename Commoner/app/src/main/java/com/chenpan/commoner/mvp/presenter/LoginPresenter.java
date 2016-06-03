package com.chenpan.commoner.mvp.presenter;

import android.content.Context;
import android.view.animation.Animation;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.User;
import com.chenpan.commoner.bean.UserManager;
import com.chenpan.commoner.mvp.modle.LoginModel;
import com.chenpan.commoner.mvp.modle.imp.ILoginModel;
import com.chenpan.commoner.mvp.view.LoginView;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/2.
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    LoginModel loginModel = new ILoginModel();
    private Context context;

    public LoginPresenter(Context context) {
        this.context = context;
    }


    public void login(Context context, String tag, Map<String, String> map
    ) {
        getWeakView().isLogining();
        loginModel.login(context, tag, map, new LoginModel.LoginLisener() {
            @Override
            public void saveUser(User result) {
                UserManager.getInstance().saveUser(result);
                getWeakView().loginFinshed();
            }
        });

    }

    @Override
    public void init() {
        getWeakView().setImageSource(loginModel.getBackgroundImageResID());
        Animation animation = loginModel.getBackgroundImageAnimation(context);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        getWeakView().animateBackgroundImage(animation);

    }

    ;

}
