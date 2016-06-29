package com.chenpan.commoner.mvp.presenter;

import android.content.Context;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.PictureBean;
import com.chenpan.commoner.bean.PictureBeanOther;
import com.chenpan.commoner.mvp.modle.PictureFramentModel;
import com.chenpan.commoner.mvp.modle.imp.IPictureFragmentOtherModel;
import com.chenpan.commoner.mvp.view.IPictureView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/22.
 */
public class PicturePresenterOther extends BasePresenter<IPictureView> {
    PictureFramentModel pictureFramentModel = new IPictureFragmentOtherModel();
    String url = "";

    public void getPicture(Context context, String tag, final Map<String, String> params) {
        if (!getWeakView().checkNet()) {
            getWeakView().onRefreshComplete();
            getWeakView().onLoadMoreComplete();
            getWeakView().showNoNet();
            return;
        }
        url = tag + "?page=" + params.get("page");

        pictureFramentModel.parserPictureOther(context, url, tag, new PictureFramentModel.Callback<List<PictureBeanOther>>() {
            @Override
            public void onSccuss(List<PictureBeanOther> data) {
                if (getWeakView() == null) return;
                getWeakView().onRefreshComplete();
                getWeakView().onLoadMoreComplete();
                if ("0".equals(params.get("page"))) {
                    if (data.size() == 0) {
                        getWeakView().showEmpty();
                    } else {
                        getWeakView().setAdapterother(data);
                        getWeakView().showSuccess();
                    }
                } else {
                    getWeakView().loadMoreOther(data);
                }

            }

            @Override
            public void onFaild() {
                {
                    if (getWeakView() == null)
                        return;
                    getWeakView().onRefreshComplete();
                    getWeakView().onLoadMoreComplete();
                    if ("0".equals(params.get("page"))) {
                        getWeakView().showFaild();
                    }
                }
            }
        });
    }

}
