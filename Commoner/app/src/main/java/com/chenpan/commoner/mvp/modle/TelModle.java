package com.chenpan.commoner.mvp.modle;

import java.util.Map;

/**
 * 查询电话号码归属地的modle
 * Created by Administrator on 2016/5/24.
 */
public interface  TelModle {
    /**
     * 加载数据
     * @param telLisener
     */
    void queryTel(TelLisener telLisener, Map<String, String> map);

    interface TelLisener{

        /**
         * 加载数据的监听
         * @param result
         */
        void queryComppelete(String result);
    }
}
