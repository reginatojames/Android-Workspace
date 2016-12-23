package com.example.reginatojames.dialoglession;

import android.app.Application;

/**
 * Created by Reginato James on 06/05/2016.
 */
public class MainApp extends Application {

    private static MainApp mInstance;

    public MainApp get(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }
}
