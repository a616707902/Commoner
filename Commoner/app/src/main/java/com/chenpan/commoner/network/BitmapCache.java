package com.chenpan.commoner.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/5/19.
 */
public class BitmapCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> cache;
    public int max=10*1024*1024;//10M
    public  BitmapCache(){
        cache=new LruCache<String,Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getWidth()*value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }
}
