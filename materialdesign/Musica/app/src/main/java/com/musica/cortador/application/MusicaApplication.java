package com.musica.cortador.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Bhavesh.Kaila on 22-May-15.
 */
public class MusicaApplication extends Application {

    public static MusicaApplication mMusicaApplication;
    public static  final  String API_KEY="ny97sdcpqetasj8a4v2na8va";

    @Override
    public void onCreate() {
        super.onCreate();
        mMusicaApplication=this;
    }

    public static MusicaApplication getInstance(){
        return mMusicaApplication;
    }

    public static Context getAppContext(){

        return mMusicaApplication.getApplicationContext();
    }
}
