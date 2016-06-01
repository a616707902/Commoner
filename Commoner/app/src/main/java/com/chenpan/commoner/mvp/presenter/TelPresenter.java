package com.chenpan.commoner.mvp.presenter;



import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.mvp.modle.TelModle;
import com.chenpan.commoner.mvp.modle.imp.TelModelImp;
import com.chenpan.commoner.mvp.view.TelView;

import java.util.Map;

/**
 * 绑定 view和modle的presenter
 * Created by Administrator on 2016/5/24.
 */
public class TelPresenter extends BasePresenter<TelView> {
    //View
    //view与presenter可以相互实现
   // TelView telView;
    //modle
    TelModle telModle = new TelModelImp();

   /* public TelPresenter(TelView telView) {
        this.telView = telView;
    }
*/
    /**
     * 绑定view与modle
     */
    public void fetch( Map<String, String> map) {

        getWeakView().showLoading();
        //modle 获取数据
        if (telModle != null) {
            telModle.queryTel(new TelModle.TelLisener() {
                @Override
                public void queryComppelete(String result) {
                    //得到数据，让View显示
                    getWeakView().showData(result);
                }
            },   map);
        }

    }
}
