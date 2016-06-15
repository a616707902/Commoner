package com.chenpan.commoner.widget.load;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenpan.commoner.R;
import com.chenpan.commoner.network.NetWorkUtil;
import com.chenpan.commoner.widget.loadview.CatLoadingView;
import com.chenpan.commoner.widget.loadview.GraduallyTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sysadminl on 2016/1/21.
 */
public class LoadingView extends FrameLayout {

    /**
     * 判断加载状态
     */
    private LoadingState mState;
    private Context mContext;
    @Bind(R.id.ll_over)
    LinearLayout ll_over;

    @Bind(R.id.ll_loading)
    LinearLayout ll_loading;


    @Bind(R.id.tv_loaded)
    TextView tv_loaded;

/*    @Bind(R.id.tv_loading)
    TextView tv_loading;*/

    @Bind(R.id.btn_loaded)
    Button btn_loaded;
    @Bind(R.id.iv_loading)
    CatLoadingView iv_loading;
    /*@Bind( R.id.graduallyTextView)
    GraduallyTextView mGraduallyTextView;*/
    @Bind(R.id.iv_loaded)
    ImageView iv_loaded;
    public boolean btn_empty_ennable = true;
    public boolean btn_error_ennable = true;
    public boolean btn_nonet_ennable = true;

    /**
     * 加载中提示文字
     */
    private String mLoadingText;
    /**
     * 加载的图片
     */
    // private int mLoadingIco;
    /**
     * 加载数据为空提示文字
     */
    private String mLoaded_empty_text;
    /**
     * 数据加载为空时显示的图片
     */
    private int mEmptyIco;
    /**
     * 没网提示
     */
    private String mLoaded_not_net_text;
    /**
     * 没网显示的图片
     */
    private int mNoNetIco;
    /**
     * 从新加载的监听
     */
    public OnRetryListener mOnRetryListener;
    /**
     * 后台或者本地出现错误提示
     */
    private String mLoaded_error_text;
    /**
     * 出现错误时显示的图片
     */
    private int mErrorIco;

    public LoadingView(Context context) {
        super(context);
        mContext = context;
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
       /* if (View.GONE == visibility && mState == LoadingState.STATE_LOADING && animation != null && animation.isRunning()) {
            animation.stop();
        }*/
        if (View.GONE == visibility && mState == LoadingState.STATE_LOADING) {
            iv_loading.setVisibility(visibility);
        }

    }
  /*  public  void stop(){
        iv_loading.stopLoading();
    }
*/
    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }


    @OnClick(R.id.btn_loaded)
    public void onClick() {
        if (mOnRetryListener != null) {
            setState(LoadingState.STATE_LOADING);
            mOnRetryListener.onRetry();
        }
    }


    /**
     * 设置加载图片
     *
     * @param resId
     * @return
     */
   /* public LoadingView withLoadingIco(int resId) {
        mLoadingIco = resId;
        return this;
    }*/
    public LoadingView withEmptyIco(int resId) {
        mEmptyIco = resId;
        return this;
    }


    public LoadingView withNoNetIco(int resId) {
        mNoNetIco = resId;
        return this;
    }


    public LoadingView withOnRetryListener(OnRetryListener mOnRetryListener) {
        this.mOnRetryListener = mOnRetryListener;
        return this;
    }


    /**
     * 设置加载状态
     *
     * @param state
     */
    public void setState(LoadingState state) {
        if (mState == state) {
            return;
        } else if (state == LoadingState.STATE_LOADING) {
            ll_over.setVisibility(GONE);
          //  mGraduallyTextView.startLoading();
            ll_loading.setVisibility(VISIBLE);
        } else if (state != LoadingState.STATE_LOADING) {
          //  mGraduallyTextView.stopLoading();
            ll_loading.setVisibility(GONE);
            ll_over.setVisibility(VISIBLE);
           /* if (animation != null&&mState==LoadingState.STATE_LOADING)
                animation.stop();*/
            //iv_loading.stopLoading();
        }
        changeState(state);
    }


    /**
     * 没网按钮点击
     *
     * @param ennable
     * @return
     */
    public LoadingView withBtnNoNetEnnable(boolean ennable) {
        btn_nonet_ennable = ennable;
        return this;
    }

    /**
     * 错误按钮点击
     *
     * @param ennable
     * @return
     */
    public LoadingView withBtnErrorEnnable(boolean ennable) {
        btn_error_ennable = ennable;
        return this;
    }

    /**
     * 返回空按钮点击
     *
     * @param ennable
     * @return
     */
    public LoadingView withBtnEmptyEnnable(boolean ennable) {
        btn_empty_ennable = ennable;
        return this;
    }

    // private AnimationDrawable animation;
    /**
     * 承载加载的动画
     */
    //  private Titanic titanic;

    /**
     * 改变加载状态
     *
     * @param state
     */
    private void changeState(LoadingState state) {
        switch (state) {
            case STATE_LOADING:
                mState = LoadingState.STATE_LOADING;

                iv_loading.setVisibility(VISIBLE);

                break;
            case STATE_EMPTY:
                mState = LoadingState.STATE_EMPTY;
                iv_loaded.setImageResource(mEmptyIco);
                tv_loaded.setText(mLoaded_empty_text);
                if (btn_empty_ennable) {
                    btn_loaded.setVisibility(VISIBLE);
                    btn_loaded.setText(btn_empty_text);
                } else {
                    btn_loaded.setVisibility(GONE);
                }
                break;
            case STATE_ERROR:
                mState = LoadingState.STATE_ERROR;
                iv_loaded.setImageResource(mErrorIco);
                tv_loaded.setText(mLoaded_error_text);
                if (btn_error_ennable) {
                    btn_loaded.setVisibility(VISIBLE);
                    btn_loaded.setText(btn_error_text);
                } else {
                    btn_loaded.setVisibility(GONE);
                }
                break;
            case STATE_NO_NET:
                mState = LoadingState.STATE_NO_NET;
                iv_loaded.setImageResource(mNoNetIco);
                tv_loaded.setText(mLoaded_not_net_text);
                if (btn_nonet_ennable) {
                    btn_loaded.setVisibility(VISIBLE);
                    btn_loaded.setText(btn_nonet_text);
                } else {
                    btn_loaded.setVisibility(GONE);
                }
                break;
        }

    }


    public LoadingView withErrorIco(int resId) {
        mErrorIco = resId;
        return this;
    }

    public LoadingView withLoadedEmptyText(int resId) {
        mLoaded_empty_text = getResources().getString(resId);
        return this;
    }

    public LoadingView withLoadedEmptyText(String mLoadedemptyText) {
        this.mLoaded_empty_text = mLoadedemptyText;
        return this;
    }

    public LoadingView withLoadedNoNetText(int resId) {
        mLoaded_not_net_text = getResources().getString(resId);
        return this;
    }

    public String btn_empty_text = "重试";
    public String btn_error_text = "重试";
    public String btn_nonet_text = "重试";

    public LoadingView withbtnEmptyText(String text) {
        this.btn_empty_text = text;
        return this;
    }

    public LoadingView withbtnErrorText(String text) {
        this.btn_error_text = text;
        return this;
    }

    public LoadingView withbtnNoNetText(String text) {
        this.btn_nonet_text = text;
        return this;
    }


    public LoadingView withLoadedNoNetText(String mLoadedNoNetText) {
        this.mLoaded_not_net_text = mLoadedNoNetText;
        return this;
    }

    public LoadingView withLoadedErrorText(int resId) {
        mLoaded_error_text = getResources().getString(resId);
        return this;
    }

    public LoadingView withLoadedErrorText(String mLoadedErrorText) {
        this.mLoaded_error_text = mLoadedErrorText;
        return this;
    }

    public LoadingView withLoadingText(int resId) {
        mLoadingText = getResources().getString(resId);
        return this;
    }

    public LoadingView withLoadingText(String mLoadingText) {
        this.mLoadingText = mLoadingText;
        return this;
    }

    /**
     * 创建
     */
    public void build() {
        View view = View.inflate(mContext, R.layout.loading, this);
        ButterKnife.bind(this, view);
        if (NetWorkUtil.isNetWorkConnected(mContext)) {
            setState(LoadingState.STATE_LOADING);
        } else {
            setState(LoadingState.STATE_NO_NET);
        }
    }

}
