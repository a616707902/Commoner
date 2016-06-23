package com.chenpan.commoner.base;

import android.app.Application;

import com.chenpan.commoner.network.volleyOK.ImageLoaderHelper;
import com.chenpan.commoner.network.volleyOK.VolleyHelper;
import com.nostra13.universalimageloader.core.ImageLoader;


public class MyApplication extends Application {
   // public  static RequestQueue queues;
   public static final String IMAGE_LOADER_CACHE_PATH = "/commoner/Images/";
    @Override
    public void onCreate() {
        super.onCreate();
       // queues= Volley.newRequestQueue(getApplicationContext());
        VolleyHelper.getInstance().init(this);
        ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(this).getImageLoaderConfiguration(IMAGE_LOADER_CACHE_PATH));
    }
//    public static RequestQueue getQueues(){
//        return  queues;
//    }
}