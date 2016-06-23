/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chenpan.commoner;

import android.os.Bundle;
import android.view.View;

import com.chenpan.commoner.base.BaseActivity;
import com.chenpan.commoner.mvp.presenter.ImageDetailPresenter;
import com.chenpan.commoner.mvp.view.ImageDetailView;
import com.chenpan.commoner.widget.SmoothImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;


public class ImagesDetailActivity extends BaseActivity<ImageDetailView, ImageDetailPresenter> {

    public static final String INTENT_IMAGE_URL_TAG = "INTENT_IMAGE_URL_TAG";
    public static final String INTENT_IMAGE_X_TAG = "INTENT_IMAGE_X_TAG";
    public static final String INTENT_IMAGE_Y_TAG = "INTENT_IMAGE_Y_TAG";
    public static final String INTENT_IMAGE_W_TAG = "INTENT_IMAGE_W_TAG";
    public static final String INTENT_IMAGE_H_TAG = "INTENT_IMAGE_H_TAG";

    private String mImageUrl;
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;

    @Bind(R.id.images_detail_smooth_image)
    SmoothImageView mSmoothImageView;


    @Override
    public void onBackPressed() {
        mSmoothImageView.transformOut();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }


    @Override
    public ImageDetailPresenter createPresenter() {
        return new ImageDetailPresenter();
    }

    @Override
    public int getToolBarId() {
        return R.id.toolbar;
    }

    @Override
    public void bindViewAndAction(Bundle savedInstanceState) {
        mSmoothImageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        mSmoothImageView.transformIn();

        ImageLoader.getInstance().displayImage(mImageUrl, mSmoothImageView);

        mSmoothImageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });

        mSmoothImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v2) {
                mSmoothImageView.transformOut();
            }
        });
    }

    @Override
    public void getIntentValue() {
        Bundle extras = getIntent().getExtras();
        mImageUrl = extras.getString(INTENT_IMAGE_URL_TAG);
        mLocationX = extras.getInt(INTENT_IMAGE_X_TAG);
        mLocationY = extras.getInt(INTENT_IMAGE_Y_TAG);
        mWidth = extras.getInt(INTENT_IMAGE_W_TAG);
        mHeight = extras.getInt(INTENT_IMAGE_H_TAG);
    }

    @Override
    public void setActionBar() {
        getToolbar().setVisibility(View.GONE);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_images_detail;
    }
}
