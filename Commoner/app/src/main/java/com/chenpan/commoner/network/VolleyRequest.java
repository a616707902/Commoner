package com.chenpan.commoner.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.chenpan.commoner.network.volleyOK.VolleyHelper;
import com.chenpan.commoner.utils.XMLRequest;

import java.util.Map;

/**
 * Created by Administrator on 2016/5/19.
 */
public class VolleyRequest {
    public  static StringRequestUTF stringRequest;
    public  static XMLRequest XRequest;

    public static Context mContext;
    public  static void  RequestGetString(String url,String tag,VolleyInterface volleyInterface){
        VolleyHelper.getInstance().getRequestQueue().cancelAll(tag);
        stringRequest=new StringRequestUTF(Request.Method.GET,url,volleyInterface.loadingListener(),volleyInterface.errorListener());
        stringRequest.setTag(tag);
        VolleyHelper.getInstance().getRequestQueue().add(stringRequest);
        VolleyHelper.getInstance().getRequestQueue().start();

    }
    public static void RequestPostString(String url,String tag, final Map<String,String> params,VolleyInterface volleyInterface){
        VolleyHelper.getInstance().getRequestQueue().cancelAll(tag);
        stringRequest=new StringRequestUTF(Request.Method.POST,url,volleyInterface.loadingListener(),volleyInterface.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setTag(tag);
        VolleyHelper.getInstance().getRequestQueue().add(stringRequest);
        VolleyHelper.getInstance().getRequestQueue().start();
    }

    /**
     * xml请求
     * @param url
     * @param tag
     * @param params
     * @param volleyInterface
     */
    public static void RequestGetStringXml(String url,String tag, final Map<String,String> params,VolleyXmlInterface volleyInterface){
        VolleyHelper.getInstance().getRequestQueue().cancelAll(tag);
        XRequest=new XMLRequest(Request.Method.POST,url,volleyInterface.loadingListenerXml(),volleyInterface.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        XRequest.setTag(tag);
        VolleyHelper.getInstance().getRequestQueue().add(XRequest);
        VolleyHelper.getInstance().getRequestQueue().start();
    }
}
