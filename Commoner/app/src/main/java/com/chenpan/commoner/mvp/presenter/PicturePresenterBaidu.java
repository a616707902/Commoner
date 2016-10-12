package com.chenpan.commoner.mvp.presenter;

import android.content.Context;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.PictureBeanBaiDu;
import com.chenpan.commoner.mvp.modle.MyCallback;
import com.chenpan.commoner.mvp.modle.PictureFramentModel;
import com.chenpan.commoner.mvp.modle.imp.IPictureFragmentBaiDuModel;
import com.chenpan.commoner.mvp.view.IPictureView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/22.
 */
public class PicturePresenterBaidu  extends BasePresenter<IPictureView> {
    //http://image.baidu.com/data/imgs?col=美女&tag=全部&pn=0&rn=20&from=1
private  String  BDurl="http://image.baidu.com/data/imgs";
    private  int MAX=20;
    PictureFramentModel pictureFramentModel=new IPictureFragmentBaiDuModel();
    public   void getPicture(Context context,String  tag, final Map<String ,String >  param){

        if(!getWeakView().checkNet()){
            getWeakView().onRefreshComplete();
            getWeakView().onLoadMoreComplete();
            getWeakView().showNoNet();
            return;
        }
        int page=Integer.valueOf(param.get("page"));
        StringBuilder url=new StringBuilder();
        url.append(BDurl).append("?col=").append(tag).append("&tag=全部").append("&pn=").append(MAX*page);
        url.append("&rn=").append(MAX).append("&from=1");

        pictureFramentModel.parserPictureBaidu(context, url.toString(), tag, new MyCallback<List<PictureBeanBaiDu>>() {
            @Override
            public void onSccuss(List<PictureBeanBaiDu> data) {
                if (getWeakView() == null) return;
                getWeakView().onRefreshComplete();
                getWeakView().onLoadMoreComplete();
                if ("0".equals(param.get("page"))) {
                    if (data.size() == 0) {
                        getWeakView().showEmpty();
                    } else {
                        getWeakView().setAdapterBaidu(data);
                        getWeakView().showSuccess();
                    }
                } else {
                    getWeakView().loadMoreBaidu(data);
                }
            }

            @Override
            public void onFaild() {
                if (getWeakView() == null) return;
                getWeakView().onRefreshComplete();
                getWeakView().onLoadMoreComplete();
                if ("0".equals(param.get("page"))) {
                    getWeakView().showFaild();
                }
            }
        });
    }
}
