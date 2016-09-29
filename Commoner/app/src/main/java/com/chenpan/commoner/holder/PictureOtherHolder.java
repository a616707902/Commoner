package com.chenpan.commoner.holder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chenpan.commoner.ImagesDetailActivity;
import com.chenpan.commoner.R;
import com.chenpan.commoner.bean.PictureBeanOther;
import com.chenpan.commoner.utils.CommonUtils;
import com.chenpan.commoner.widget.PLAImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/6/28.
 */
public class PictureOtherHolder extends BaseHolder<PictureBeanOther> {

    @Bind(R.id.sd_juzimi)
    PLAImageView sdJuzimi;
    @Bind(R.id.tv_content)
    TextView tvContent;
    public PictureOtherHolder(View view) {
        super(view);
    }


    @Override
    public void init() {
        super.init();
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Rect frame = new Rect();
                //这里可能会出错
                ((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;

                int[] location = new int[2];
                sdJuzimi.getLocationOnScreen(location);
                location[1] += statusBarHeight;

                int width = sdJuzimi.getWidth();
                int height = sdJuzimi.getHeight();
                Bundle extras = new Bundle();
                extras.putString(ImagesDetailActivity.INTENT_IMAGE_URL_TAG, mData.getUrl());
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_X_TAG, location[0]);
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_Y_TAG, location[1]);
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_W_TAG, width);
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_H_TAG, height);
                extras.putString(ImagesDetailActivity.INTENT_CONTENT, mData.content);
                extras.putString(ImagesDetailActivity.INTENT_CONTENT_SENER,mData.sender);
             /*   readyGo(ImagesDetailActivity.class, extras);
                getActivity().overridePendingTransition(0, 0);*/
                Intent intent=new Intent(mContext, ImagesDetailActivity.class);
                intent.putExtras(extras);
                mContext.startActivity(intent);
                ( ((Activity) mContext)).overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void setData(PictureBeanOther itemData,int position) {
        super.setData(itemData,position);


        String imageUrl = itemData.getUrl();

        if (!CommonUtils.isEmpty(imageUrl)) {
            ImageLoader.getInstance().displayImage(imageUrl, sdJuzimi);
        }

        if (TextUtils.isEmpty(mData.content)) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setText(mData.content);
        }


    }

}
