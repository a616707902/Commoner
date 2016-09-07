package com.chenpan.commoner.base;

import android.app.Application;
import android.support.v4.util.LongSparseArray;

import com.chenpan.commoner.network.volleyOK.ImageLoaderHelper;
import com.chenpan.commoner.network.volleyOK.VolleyHelper;
import com.chenpan.commoner.utils.Preferences;
import com.chenpan.commoner.utils.ToastFactory;
import com.example.chenpan.library.skinmanager.loader.SkinManager;
import com.nostra13.universalimageloader.core.ImageLoader;


public class MyApplication extends Application {
   // public  static RequestQueue queues;
   public static final String IMAGE_LOADER_CACHE_PATH = "/commoner/Images/";
    private LongSparseArray<String> mDownloadList = new LongSparseArray<>();
    private static MyApplication sInstance;
    public static MyApplication getInstance() {
        return sInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        ToastFactory.init(this);
        Preferences.init(this);
        initSkinLoader();
        VolleyHelper.getInstance().init(this);
        ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(this).getImageLoaderConfiguration(IMAGE_LOADER_CACHE_PATH));
    }
    public LongSparseArray<String> getDownloadList() {
        return mDownloadList;
    }
    /**
     * Must call init first
     */
    private void initSkinLoader() {
        SkinManager.getInstance().init(this);
        SkinManager.getInstance().load();
    }
}