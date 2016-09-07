package com.chenpan.commoner;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.chenpan.commoner.base.BaseActivity;
import com.chenpan.commoner.mvp.presenter.SkinPresenter;
import com.chenpan.commoner.mvp.view.SkinView;
import com.chenpan.commoner.utils.ToastFactory;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SkinActivity extends BaseActivity<SkinView, SkinPresenter> implements SkinView {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ll_green)
    LinearLayout llGreen;
    @Bind(R.id.ll_brown)
    LinearLayout llBrown;
    @Bind(R.id.ll_black)
    LinearLayout llBlack;
    @Bind(R.id.ll_blue)
    LinearLayout llBlue;
    @Bind(R.id.ll_red)
    LinearLayout llRed;
    @Bind(R.id.ll_yellow)
    LinearLayout llYellow;

    @Override
    public SkinPresenter createPresenter() {
        return new SkinPresenter();
    }

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        llRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setRedSkin("RedFantacy.skin");
            }
        });
        llBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setBlueSkin();
            }
        });
    }

    @Override
    public void setActionBar() {
        getSupportActionBar().setTitle(R.string.skinchange);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_skin;
    }


    @Override
    public void showSucceed() {
        ToastFactory.show("切换成功");
    }

    @Override
    public void showFild() {
        ToastFactory.show("切换失败");
    }

    @Override
    public void showNOres() {
        ToastFactory.show("未发现皮肤资源");
    }


}
