package com.chenpan.commoner.holder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.chenpan.commoner.ImagesDetailActivity;
import com.chenpan.commoner.R;
import com.chenpan.commoner.bean.PictureBeanBaiDu;
import com.chenpan.commoner.utils.CommonUtils;
import com.chenpan.commoner.widget.PLAImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/6/23.
 */
public class PictureBaiduHolder extends BaseHolder<PictureBeanBaiDu> {
    @Bind(R.id.list_item_images_list_image)
    PLAImageView imageView;

    public PictureBaiduHolder(View view) {
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
                view.getLocationOnScreen(location);
                location[1] += statusBarHeight;

                int width = view.getWidth();
                int height = view.getHeight();
                Bundle extras = new Bundle();
                extras.putString(ImagesDetailActivity.INTENT_IMAGE_URL_TAG, mData.getImageUrl());
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_X_TAG, location[0]);
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_Y_TAG, location[1]);
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_W_TAG, width);
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_H_TAG, height);
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
    public void setData(PictureBeanBaiDu itemData,int position) {
        super.setData(itemData,position);
        int width = itemData.getThumbnailWidth();
        int height = itemData.getThumbnailHeight();

        String imageUrl = itemData.getImageUrl();

        if (!CommonUtils.isEmpty(imageUrl)) {
            ImageLoader.getInstance().displayImage(imageUrl, imageView);
        }

        imageView.setImageWidth(width);
        imageView.setImageHeight(height);
    }

}
