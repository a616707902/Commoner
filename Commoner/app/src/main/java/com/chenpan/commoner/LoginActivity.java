package com.chenpan.commoner;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.chenpan.commoner.base.BaseActivity;
import com.chenpan.commoner.mvp.presenter.LoginPresenter;
import com.chenpan.commoner.mvp.view.LoginView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {

    @Bind(R.id.splash_image)
    ImageView splashImage;
    @Bind(R.id.account)
    EditText account;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.loginbtn)
    Button loginbtn;
    @Bind(R.id.forgetpwd)
    Button forgetpwd;
    @Bind(R.id.register)
    Button register;
    @Bind(R.id.qq)
    ImageView qq;
    @Bind(R.id.sina)
    ImageView sina;
    View.OnClickListener clickLisenter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginbtn:
                    mPresenter.login(LoginActivity.this, "login", getmap());
                    break;
                case R.id.forgetpwd:
                    break;
                case R.id.register:
                    break;
                case R.id.qq:
                    break;
                case R.id.sina:
                    break;


            }

        }
    };


    private Map<String, String> getmap() {
        Map<String, String> map = new HashMap<>();

        return map;
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        mPresenter.init();
        loginbtn.setOnClickListener(clickLisenter);
        forgetpwd.setOnClickListener(clickLisenter);
        register.setOnClickListener(clickLisenter);
        qq.setOnClickListener(clickLisenter);
        sina.setOnClickListener(clickLisenter);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    public int getToolBarId() {
        return 0;
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void isLogining() {

    }

    @Override
    public void loginFinshed() {

    }

    @Override
    public void animateBackgroundImage(Animation animation) {
        splashImage.setAnimation(animation);
    }

    @Override
    public void setImageSource(int res) {
        splashImage.setImageResource(res);
    }


}

