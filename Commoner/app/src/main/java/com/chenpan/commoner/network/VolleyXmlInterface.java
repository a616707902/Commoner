package com.chenpan.commoner.network;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by Administrator on 2016/5/31.
 */
public abstract class VolleyXmlInterface {
    private Context context;
    public static Response.Listener<XmlPullParser> mXMLListener;
    public static Response.ErrorListener mErrorListener;
    public VolleyXmlInterface() {
    }
    public VolleyXmlInterface(Context context, Response.Listener<XmlPullParser> lisener, Response.ErrorListener errorListener) {
        this.context = context;
        mXMLListener = lisener;
        mErrorListener = errorListener;
    }
    public Response.Listener<XmlPullParser>  loadingListenerXml(){
        mXMLListener=new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                onMySuccessXml(response);
            }
        };
        return mXMLListener;
    }
    public Response.ErrorListener errorListener(){
        mErrorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onMyError(error);
            }
        };
        return mErrorListener;
    }
    public  abstract void onMySuccessXml(XmlPullParser result);
    public  abstract void onMyError(VolleyError result);
}
