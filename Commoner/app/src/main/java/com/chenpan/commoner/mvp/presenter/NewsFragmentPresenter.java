package com.chenpan.commoner.mvp.presenter;

import android.content.Context;

import com.chenpan.commoner.base.pbase.BasePresenter;
import com.chenpan.commoner.bean.NewsSummary;
import com.chenpan.commoner.common.ApiConstans;
import com.chenpan.commoner.mvp.modle.MyCallback;
import com.chenpan.commoner.mvp.modle.NewFragmentModel;
import com.chenpan.commoner.mvp.modle.imp.INewsFragmentModel;
import com.chenpan.commoner.mvp.view.INewsFragmentView;

import java.util.List;
import java.util.Map;

/**@类名: NewsFragmentPresenter
* @功能描述: 
* @作者:chepan
* @时间: 2016/10/11
* @版权申明:陈攀
* @最后修改者:
* @最后修改内容:
*/
public class NewsFragmentPresenter extends BasePresenter<INewsFragmentView> {

    private NewFragmentModel mModel=new INewsFragmentModel();
//http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    public void getNewsList(Context context,  String tag, final Map<String,String> params){

        if (!getWeakView().checkNet()) {
            getWeakView().onRefreshComplete();
            getWeakView().onLoadMoreComplete();
            getWeakView().showNoNet();
            return;
        }
        StringBuilder url=new StringBuilder();
        url.append(ApiConstans.NEWS_DETAIL).append(ApiConstans.getType(tag)).append("/").append(tag).append("/").append(params.get("pageNO")).append(ApiConstans.END_URL);
        mModel.parserNews(context,url.toString(), tag, new MyCallback<List<NewsSummary>>() {
            @Override
            public void onSccuss(List<NewsSummary> data) {
                if (getWeakView() == null) return;
                getWeakView().onRefreshComplete();
                getWeakView().onLoadMoreComplete();
                if ("0".equals(params.get("page"))) {
                    if (data.size() == 0) {
                        getWeakView().showEmpty();
                    } else {
                        getWeakView().setAdapter(data);
                        getWeakView().showSuccess();
                    }
                } else {
                    getWeakView().loadMore(data);
                }
            }

            @Override
            public void onFaild() {
                if (getWeakView() == null) return;
                getWeakView().onRefreshComplete();
                getWeakView().onLoadMoreComplete();
                if ("0".equals(params.get("page"))) {
                    getWeakView().showFaild();
                }
            }
        });

    }
}
