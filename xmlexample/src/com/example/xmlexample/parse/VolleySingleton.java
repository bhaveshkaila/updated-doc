package com.example.xmlexample.parse;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Bhavesh.Kaila on 22-May-15.
 */
public class VolleySingleton {

    private static VolleySingleton mVolleySingleton=null;

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;

    public VolleySingleton(){
//        mRequestQueue= Volley.newRequestQueue(context);
       /* mImageLoader=new ImageLoader(mRequestQueue,new ImageLoader.ImageCache() {
            int catchSize=4*1024*1024;
            private LruCache<String,Bitmap> lruCache=new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
            @Override
            public Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

                lruCache.put(url,bitmap);
            }
        });*/
    }

    public static VolleySingleton getInstance(){

        if(mVolleySingleton==null){
            mVolleySingleton=new VolleySingleton();
        }
        return  mVolleySingleton;
    }

    public  RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return  mImageLoader;
    }
}
