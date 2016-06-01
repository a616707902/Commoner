package com.chenpan.commoner.base;

import android.app.Application;

import com.chenpan.commoner.network.volleyOK.VolleyHelper;


public class MyApplication extends Application {
   // public  static RequestQueue queues;
    @Override
    public void onCreate() {
        super.onCreate();
       // queues= Volley.newRequestQueue(getApplicationContext());
        VolleyHelper.getInstance().init(this);

    }
//    public static RequestQueue getQueues(){
//        return  queues;
//    }
}