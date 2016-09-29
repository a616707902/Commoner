package com.chenpan.commoner.holder;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenpan.commoner.ArticleActivity;
import com.chenpan.commoner.MainActivity;
import com.chenpan.commoner.R;
import com.chenpan.commoner.bean.ArticleBean;

import butterknife.Bind;

public class ArticleHolder extends BaseHolder<ArticleBean> {
    public ArticleHolder(View view) {
        super(view);
    }

    @Bind(R.id.rl_auth)
    RelativeLayout rl_auth;

    @Bind(R.id.tv_auth)
    TextView tv_auth;

    @Bind(R.id.title)
  TextView title;
    @Bind(R.id.des)
    TextView des;

    @Override
    public void init() {
        super.init();
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, ArticleActivity.class);
                intent.putExtra("article", mData);
                mContext.startActivity(intent);
                ((MainActivity)mContext). overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
    }

    @Override
    public void setData(ArticleBean mData,int position) {
        super.setData(mData,position);
        title.setText(mData.title);
        des.setText(Html.fromHtml(mData.text));
        if (TextUtils.isEmpty(mData.auth)) {
            rl_auth.setVisibility(View.GONE);
        } else {
            rl_auth.setVisibility(View.VISIBLE);
            tv_auth.setText(mData.auth);
        }
    }
}
