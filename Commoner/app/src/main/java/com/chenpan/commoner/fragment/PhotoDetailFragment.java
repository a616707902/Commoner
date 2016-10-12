package com.chenpan.commoner.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.chenpan.commoner.R;
import com.chenpan.commoner.base.BaseFragment;
import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.common.Constants;
import com.chenpan.commoner.mvp.presenter.NewsFragmentPresenter;
import com.chenpan.commoner.utils.CommonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

/**
 * 作者：chenpan
 * 时间：2016/10/12 15:07
 * 邮箱：616707902@qq.com
 * 描述：
 */

public class PhotoDetailFragment extends BaseFragment {

    @Bind(R.id.photo_view)
    PhotoView mPhotoView;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    private String mImgSrc;


    @Override
    public BasePresenter createPresenter() {
        return new NewsFragmentPresenter();
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mImgSrc = getArguments().getString(Constants.PHOTO_DETAIL_IMGSRC);
        }
        if (!CommonUtils.isEmpty(mImgSrc)) {
            ImageLoader.getInstance().displayImage(mImgSrc, mPhotoView);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_news_photo_detail;
    }


}
