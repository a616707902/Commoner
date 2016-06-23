package com.chenpan.commoner.mvp.presenter;

import android.content.Context;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.PictureBean;
import com.chenpan.commoner.mvp.modle.PictureFramentModel;
import com.chenpan.commoner.mvp.modle.imp.IPictureFragmentOtherModel;
import com.chenpan.commoner.mvp.view.IPictureView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class PicturePresenterOther extends BasePresenter<IPictureView> {
    PictureFramentModel pictureFramentModel=new IPictureFragmentOtherModel();



}
