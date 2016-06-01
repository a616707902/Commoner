package com.chenpan.commoner.network.volleyOK;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/3/20.
 * Description:
 */
public class VolleyHelper {

    private RequestQueue requestQueue = null;

    private static volatile VolleyHelper instance = null;

    private VolleyHelper() {
    }

    public static VolleyHelper getInstance() {
        if (null == instance) {
            synchronized (VolleyHelper.class) {
                if (null == instance) {
                    instance = new VolleyHelper();
                }
            }
        }
        return instance;
    }

    /**
     * init volley helper
     *
     * @param context
     */
    public void init(Context context) {
        requestQueue = Volley.newRequestQueue(context, new OkHttpStack(new OkHttpClient()));
    }

    /**
     * get request queue
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (null != requestQueue) {
            return requestQueue;
        } else {
            throw new IllegalArgumentException("RequestQueue is not initialized.");
        }
    }

}
